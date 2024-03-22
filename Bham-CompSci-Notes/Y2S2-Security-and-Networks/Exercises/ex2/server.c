#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <unistd.h>
#include <pthread.h>
#include <stdbool.h>

#define BUFFERLENGTH 256
#define THREAD_IN_USE 0
#define THREAD_FINISHED 1
#define THREAD_AVAILABLE 2
#define THREADS_ALLOCATED 10

/* displays error messages from system calls */
void error(char *msg)
{
  perror(msg);
  exit(1);
};

struct threadArgs_t
{
  int newsockfd;
  int threadIndex;
};

struct firewallRules_t *allRules = NULL, *tmp = NULL;
int isExecuted = 0;
int returnValue = 0;                             /* not used; need something to keep compiler happy */
pthread_mutex_t mut = PTHREAD_MUTEX_INITIALIZER; /* the lock used for processing */

/* this is only necessary for proper termination of threads - you should not need to access this part in your code */
struct threadInfo_t
{
  pthread_t pthreadInfo;
  pthread_attr_t attributes;
  int status;
};
struct threadInfo_t *serverThreads = NULL;
int noOfThreads = 0;
pthread_rwlock_t threadLock = PTHREAD_RWLOCK_INITIALIZER;
pthread_rwlock_t ruleLock = PTHREAD_RWLOCK_INITIALIZER;
pthread_cond_t threadCond = PTHREAD_COND_INITIALIZER;
pthread_mutex_t threadEndLock = PTHREAD_MUTEX_INITIALIZER;

struct queryList
{
  char query[25];
  struct queryList *nextQuery;
};

struct firewallRule_t
{
  int ipaddr1[4];
  int ipaddr2[4];
  int port1;
  int port2;
  struct queryList *list;
};

struct firewallRules_t
{
  struct firewallRule_t *rule;
  struct firewallRules_t *next;
};

int compareIPAddresses(int *ipaddr1, int *ipaddr2)
{
  int i;
  for (i = 0; i < 4; i++)
  {
    if (ipaddr1[i] > ipaddr2[i])
    {
      return 1;
    }
    else if (ipaddr1[i] < ipaddr2[i])
    {
      return -1;
    }
  }
  return 0;
}

char *parseIPaddress(int *ipaddr, char *text, bool checkFile)
{
  char *oldPos, *newPos;
  long int addr;
  int i;

  oldPos = text;
  for (i = 0; i < 4; i++)
  {
    if (oldPos == NULL || *oldPos < '0' || *oldPos > '9')
    {
      return NULL;
    }

    addr = strtol(oldPos, &newPos, 10);
    if (newPos == oldPos)
    {
      return NULL;
    }
    if ((addr < 0) || addr > 255)
    {
      ipaddr[0] = -1;
      return NULL;
    }
    if (i < 3)
    {
      if ((newPos == NULL) || (*newPos != '.'))
      {
        ipaddr[0] = -1;
        return NULL;
      }
      else
        newPos++;
    }
    else if ((newPos == NULL) || ((*newPos != ' ') && (*newPos != '-') && checkFile) || (!checkFile && (*newPos != '\0')))
    {
      ipaddr[0] = -1;
      return NULL;
    }
    ipaddr[i] = addr;
    oldPos = newPos;
  }
  return newPos;
}

char *parsePort(int *port, char *text)
{
  char *newPos;

  if ((text == NULL) || (*text < '0') || (*text > '9'))
  {
    return NULL;
  }
  *port = strtol(text, &newPos, 10);
  if (newPos == text)
  {
    *port = -1;
    return NULL;
  }
  if ((*port < 0) || (*port > 65535))
  {
    *port = -1;
    return NULL;
  }
  return newPos;
}

int checkIfValidArg(int *IP, char *port)
{
  int i;
  if (port == NULL)
  {
    return -1;
  }
  for (i = 0; i < 4; i++)
  {
    if ((IP[i] < 0) || (IP[i] > 255))
    {
      return -1;
    }
  }
  if ((atoi(port) >= 0) && (atoi(port) <= 65535))
  {
    return 0;
  }
  return -1;
}

struct firewallRule_t *readRule(char *line)
{
  struct firewallRule_t *newRule;
  char *pos;

  // parse IP addresses
  newRule = malloc(sizeof(struct firewallRule_t));
  pos = parseIPaddress(newRule->ipaddr1, line, true);
  if ((pos == NULL) || (newRule->ipaddr1[0] == -1))
  {
    free(newRule);
    return NULL;
  }
  if (*pos == '-')
  {
    // read second IP address
    pos = parseIPaddress(newRule->ipaddr2, pos + 1, true);
    if ((pos == NULL) || (newRule->ipaddr2[0] == -1))
    {
      free(newRule);
      return NULL;
    }

    if (compareIPAddresses(newRule->ipaddr1, newRule->ipaddr2) != -1)
    {
      free(newRule);
      return NULL;
    }
  }
  else
  {
    newRule->ipaddr2[0] = -1;
    newRule->ipaddr2[1] = -1;
    newRule->ipaddr2[2] = -1;
    newRule->ipaddr2[3] = -1;
  }
  if (*pos != ' ')
  {
    free(newRule);
    return NULL;
  }
  else
    pos++;

  // parse ports
  pos = parsePort(&(newRule->port1), pos);
  if ((pos == NULL) || (newRule->port1 == -1))
  {
    free(newRule);
    return NULL;
  }
  if ((*pos == '\n') || (*pos == '\0'))
  {
    newRule->port2 = -1;
    return newRule;
  }
  if (*pos != '-')
  {
    free(newRule);
    return NULL;
  }

  pos++;
  pos = parsePort(&(newRule->port2), pos);
  if ((pos == NULL) || (newRule->port2 == -1))
  {
    free(newRule);
    return NULL;
  }
  if (newRule->port2 <= newRule->port1)
  {
    free(newRule);
    return NULL;
  }
  if ((*pos == '\n') || (*pos == '\0'))
  {
    return newRule;
  }
  free(newRule);
  return NULL;
}

struct firewallRules_t *addRule(struct firewallRules_t *rules, struct firewallRule_t *rule)
{
  struct firewallRules_t *newRule;

  newRule = malloc(sizeof(struct firewallRules_t));
  newRule->rule = rule;
  newRule->next = rules;
  return newRule;
}

struct queryList *addQuery(char *ipaddr, char *port, struct firewallRule_t *rule)
{
  struct queryList *newQueryList;
  char value[25];
  strcpy(value, ipaddr);
  strcat(value, " ");
  strcat(value, port);

  newQueryList = malloc(sizeof(struct queryList));
  strcpy(newQueryList->query, value);
  newQueryList->nextQuery = rule->list;
  return newQueryList;
}

bool checkIPAddress(int *ipaddr1, int *ipaddr2, int *ipaddr)
{
  int res;

  res = compareIPAddresses(ipaddr, ipaddr1);
  if (compareIPAddresses(ipaddr, ipaddr1) == 0)
  {
    return true;
  }
  else if (ipaddr2[0] == -1)
  {
    return false;
  }
  else if (res == -1)
  {
    return false;
  }
  else if (compareIPAddresses(ipaddr, ipaddr2) <= 0)
  {
    return true;
  }
  else
  {
    return false;
  }
}
char *printIPaddress(int *ipaddr)
{
  static char ipString[16];
  sprintf(ipString, "%d.%d.%d.%d", ipaddr[0], ipaddr[1], ipaddr[2], ipaddr[3]);
  return ipString;
}

void sendQueryList(struct firewallRule_t *rule, int sock)
{
  char msg[BUFFERLENGTH];
  struct queryList *localQueryList = rule->list;
  int n;
  char portCopy[12];
  while (localQueryList)
  {
    strcpy(msg, "Query: ");
    strcat(msg, localQueryList->query);
    n = write(sock, msg, BUFFERLENGTH);
    if (n < 0)
      error("Error writing to socket.");
    n = read(sock, msg, BUFFERLENGTH - 1);
    if (n < 0)
      error("ERROR reading from socket");
    bzero(msg, BUFFERLENGTH);
    bzero(portCopy, 12);
    localQueryList = localQueryList->nextQuery;
  }
}

void sendRule(struct firewallRule_t *rule, int sock)
{
  struct firewallRule_t *tempRule = rule;
  char msg[BUFFERLENGTH];
  char port[11];
  int n;

  strcpy(msg, "Rule: ");
  strcat(msg, printIPaddress(tempRule->ipaddr1));
  if (tempRule->ipaddr2[0] != -1)
  {
    strcat(msg, "-");
    strcat(msg, printIPaddress(tempRule->ipaddr2));
  }
  if (tempRule->port2 != -1)
  {
    sprintf(port, " %d-%d", tempRule->port1, tempRule->port2);
  }
  else
  {
    sprintf(port, " %d", tempRule->port1);
  }
  strcat(msg, port);

  n = write(sock, msg, BUFFERLENGTH);
  if (n < 0)
    error("Error writing to socket.");
  bzero(msg, BUFFERLENGTH);
  bzero(port, 11);
}

int ruleExactMatch(struct firewallRule_t *rule1, struct firewallRule_t *rule2)
{
  int i;
  for (i = 0; i < 4; i++)
  {
    if (rule1->ipaddr1[i] != rule2->ipaddr1[i])
      return -1;
    if (rule1->ipaddr2[i] != rule2->ipaddr2[i])
      return -1;
  }
  if (!((rule1->port1 == rule2->port1) && (rule1->port2 == rule2->port2)))
    return -1;
  return 0;
}

int checkPort(int port1, int port2, int port)
{
  if (port == port1)
  {
    return 0;
  }
  else if (port < port1)
  {
    return -1;
  }
  else if (port2 == -1 || port > port2)
  {
    return 1;
  }
  else
  {
    return 0;
  }
}

/* For each connection, this function is called in a separate thread. You need to modify this function. */
void *processRequest(void *args)
{
  struct threadArgs_t *threadArgs;
  char buffer[BUFFERLENGTH];
  int n;
  threadArgs = (struct threadArgs_t *)args;
  bzero(buffer, BUFFERLENGTH);
  n = read(threadArgs->newsockfd, buffer, BUFFERLENGTH - 1);
  if (n < 0)
    error("ERROR reading from socket");

  // ADD RULE COMMAND
  if (buffer[0] == 'A')
  {
    struct firewallRule_t *newRule;
    char *temp = buffer;
    temp += 2;
    char *replace = strchr(temp, ':');
    if (replace != NULL)
      *replace = ' ';
    newRule = readRule(temp);
    if (newRule != NULL)
    {
      if (pthread_rwlock_wrlock(&ruleLock) != 0)
        error("ERROR obtaining write lock");
      allRules = addRule(allRules, newRule);
      if (pthread_rwlock_unlock(&ruleLock) != 0)
        error("ERROR releasing write lock");
      n = write(threadArgs->newsockfd, "Rule added", BUFFERLENGTH);
      if (n < 0)
        error("ERROR writing to socket");
    }
    else
    {
      n = write(threadArgs->newsockfd, "Invalid rule", BUFFERLENGTH);
      if (n < 0)
        error("ERROR writing to socket");
    }
  }

  // FIREWALL CHECK COMMAND

  else if (buffer[0] == 'C')
  {
    bool ruleFound = false;
    char *temp = buffer;
    int res, searchIP[4], searchPort;
    char *IPStrFull, *IPStrSec, *portStr, IPStrConst[16];
    struct firewallRules_t *tmp = allRules;
    if (strchr(temp, '-') == NULL)
    {
      temp += 2;
      IPStrFull = strtok(temp, ":");
      portStr = strtok(NULL, ":");
      searchPort = atoi(portStr);
      strcpy(IPStrConst, IPStrFull);
      IPStrSec = strtok(IPStrFull, ".");
      searchIP[0] = atoi(IPStrSec);
      int i;
      for (i = 1; i < 4; i++)
      {
        IPStrSec = strtok(NULL, ".");
        searchIP[i] = atoi(IPStrSec);
      }
      int chk = checkIfValidArg(searchIP, portStr);
      if (chk == 0)
      {
        if (pthread_rwlock_rdlock(&ruleLock) != 0)
          error("ERROR obtaining write lock");
        while (tmp && !ruleFound)
        {
          res = checkPort(tmp->rule->port1, tmp->rule->port2, searchPort);
          if (res == 0)
          {
            ruleFound = checkIPAddress(tmp->rule->ipaddr1, tmp->rule->ipaddr2, searchIP);
          }
          if (!ruleFound)
          {
            tmp = tmp->next;
          }
        }
        if (pthread_rwlock_unlock(&ruleLock) != 0)
          error("ERROR releasing write lock");

        if (ruleFound)
        {
          if (pthread_rwlock_rdlock(&ruleLock) != 0)
            error("ERROR obtaining write lock");
          tmp->rule->list = addQuery(IPStrConst, portStr, tmp->rule);
          if (pthread_rwlock_unlock(&ruleLock) != 0)
            error("ERROR releasing write lock");
          n = write(threadArgs->newsockfd, "Connection accepted", BUFFERLENGTH);
          if (n < 0)
            error("ERROR writing to socket");
        }
        else
        {
          n = write(threadArgs->newsockfd, "Connection rejected", BUFFERLENGTH);
          if (n < 0)
            error("ERROR writing to socket");
        }
      }
      else
      {
        n = write(threadArgs->newsockfd, "Illegal IP address or port specified", BUFFERLENGTH);
        if (n < 0)
          error("ERROR writing to socket");
      }
    }
    else
    {
      n = write(threadArgs->newsockfd, "Illegal IP address or port specified", BUFFERLENGTH);
      if (n < 0)
        error("ERROR writing to socket");
    }
  }

  // DELETE A RULE
  else if (buffer[0] == 'D')
  {
    bool ruleFound = false;
    struct firewallRules_t *prev;
    struct firewallRule_t *tempRule;
    struct queryList *tempQueries;
    char *ruleText = buffer + 2;
    char *position = strchr(ruleText, ':');
    if (position != NULL)
    {
      *position = ' ';
    }
    tempRule = readRule(ruleText);
    if (tempRule != NULL)
    {
      if (pthread_rwlock_wrlock(&ruleLock) != 0)
        error("ERROR obtaining write lock");
      tmp = allRules;
      while (tmp && !ruleFound)
      {
        tempRule = readRule(ruleText);
        ruleFound = (0 == ruleExactMatch(tmp->rule, tempRule));
        if (!ruleFound)
        {
          prev = tmp;
          tmp = tmp->next;
        }
      }

      if (ruleFound)
      {
        while (tmp->rule->list != NULL)
        {
          tempQueries = tmp->rule->list->nextQuery;
          free(tmp->rule->list);
          tmp->rule->list = tempQueries;
        }
        if (prev != NULL)
        {
          strcpy(buffer, "Rule deleted");
          prev->next = tmp->next;
          free(tmp->rule);
          free(tmp);
        }
        else if (prev == NULL)
        {
          strcpy(buffer, "Rule deleted");
          allRules = allRules->next;
          free(tmp->rule);
          free(tmp);
        }
      }
      else if (!ruleFound)
      {
        strcpy(buffer, "Rule not found");
      }
      if (pthread_rwlock_unlock(&ruleLock) != 0)
        error("ERROR releasing write lock");
    }
    else
    {
      strcpy(buffer, "Rule invalid");
    }
    n = write(threadArgs->newsockfd, buffer, BUFFERLENGTH);
    if (n < 0)
      error("ERROR writing to socket");
  }

  // LIST ALL RULES
  else if (buffer[0] == 'L')
  {
    if (pthread_rwlock_rdlock(&ruleLock) != 0)
        error("ERROR obtaining read lock");
    tmp = allRules;
    struct firewallRules_t *tempRulesIterator = allRules;
    while (tempRulesIterator)
    {
      sendRule(tmp->rule, threadArgs->newsockfd);
      n = read(threadArgs->newsockfd, buffer, BUFFERLENGTH - 1);
      if (n < 0)
        error("ERROR reading from socket");
      sendQueryList(tmp->rule, threadArgs->newsockfd);
      tmp = tmp->next;
      tempRulesIterator = tempRulesIterator->next;
    }
    if (pthread_rwlock_unlock(&ruleLock) != 0)
        error("ERROR releasing read lock");
    n = write(threadArgs->newsockfd, "$", BUFFERLENGTH);
    if (n < 0)
      error("ERROR writing to socket");
  }
  else if (buffer[0] == '$')
  {
  }
  else
  {
    printf("Illegal request\n");
  }
  /* these two lines are required for proper thread termination */
  serverThreads[threadArgs->threadIndex].status = THREAD_FINISHED;
  pthread_cond_signal(&threadCond);

  close(threadArgs->newsockfd); /* important to avoid memory leak */
  free(threadArgs);
  pthread_exit(&returnValue);
}

/* finds unused thread info slot; allocates more slots if necessary
   only called by main thread */
int findThreadIndex()
{
  int i, tmp;

  for (i = 0; i < noOfThreads; i++)
  {
    if (serverThreads[i].status == THREAD_AVAILABLE)
    {
      serverThreads[i].status = THREAD_IN_USE;
      return i;
    }
  }

  /* no available thread found; need to allocate more threads */
  pthread_rwlock_wrlock(&threadLock);
  serverThreads = realloc(serverThreads, ((noOfThreads + THREADS_ALLOCATED) * sizeof(struct threadInfo_t)));
  noOfThreads = noOfThreads + THREADS_ALLOCATED;
  pthread_rwlock_unlock(&threadLock);
  if (serverThreads == NULL)
  {
    fprintf(stderr, "Memory allocation failed\n");
    exit(1);
  }
  /* initialise thread status */
  for (tmp = i + 1; tmp < noOfThreads; tmp++)
  {
    serverThreads[tmp].status = THREAD_AVAILABLE;
  }
  serverThreads[i].status = THREAD_IN_USE;
  return i;
}

/* waits for threads to finish and releases resources used by the thread management functions. You don't need to modify this function */
void *waitForThreads(void *args)
{
  int i, res;
  while (1)
  {
    pthread_mutex_lock(&threadEndLock);
    pthread_cond_wait(&threadCond, &threadEndLock);
    pthread_mutex_unlock(&threadEndLock);

    pthread_rwlock_rdlock(&threadLock);
    for (i = 0; i < noOfThreads; i++)
    {
      if (serverThreads[i].status == THREAD_FINISHED)
      {
        res = pthread_join(serverThreads[i].pthreadInfo, NULL);
        if (res != 0)
        {
          fprintf(stderr, "thread joining failed, exiting\n");
          exit(1);
        }
        serverThreads[i].status = THREAD_AVAILABLE;
      }
    }
    pthread_rwlock_unlock(&threadLock);
  }
}

int main(int argc, char *argv[])
{
  socklen_t clilen;
  int sockfd, portno;
  struct sockaddr_in6 serv_addr, cli_addr;
  int result;
  pthread_t waitInfo;
  pthread_attr_t waitAttributes;

  if (argc < 2)
  {
    fprintf(stderr, "ERROR, no port provided\n");
    exit(1);
  }

  /* create socket */
  sockfd = socket(AF_INET6, SOCK_STREAM, 0);
  if (sockfd < 0)
    error("ERROR opening socket");
  bzero((char *)&serv_addr, sizeof(serv_addr));
  portno = atoi(argv[1]);
  serv_addr.sin6_family = AF_INET6;
  serv_addr.sin6_addr = in6addr_any;
  serv_addr.sin6_port = htons(portno);

  /* bind it */
  if (bind(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
  {
    error("ERROR on binding");
  }
  /* ready to accept connections */
  listen(sockfd, 5);
  clilen = sizeof(cli_addr);

  /* create separate thread for waiting  for other threads to finish */
  if (pthread_attr_init(&waitAttributes))
  {
    fprintf(stderr, "Creating initial thread attributes failed!\n");
    exit(1);
  }

  result = pthread_create(&waitInfo, &waitAttributes, waitForThreads, NULL);
  if (result != 0)
  {
    fprintf(stderr, "Initial Thread creation failed!\n");
    exit(1);
  }

  /* now wait in an endless loop for connections and process them */
  while (1)
  {
    struct threadArgs_t *threadArgs; /* must be allocated on the heap to prevent variable going out of scope */
    int threadIndex;
    threadArgs = malloc(sizeof(struct threadArgs_t));
    if (!threadArgs)
    {
      fprintf(stderr, "Memory allocation failed!\n");
      exit(1);
    }

    /* waiting for connections */
    threadArgs->newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
    if (threadArgs->newsockfd < 0)
      error("ERROR on accept");

    /* create thread for processing of connection */
    threadIndex = findThreadIndex();
    threadArgs->threadIndex = threadIndex;
    if (pthread_attr_init(&(serverThreads[threadIndex].attributes)))
    {
      fprintf(stderr, "Creating thread attributes failed!\n");
      exit(1);
    }

    result = pthread_create(&(serverThreads[threadIndex].pthreadInfo), &(serverThreads[threadIndex].attributes), processRequest, (void *)threadArgs);
    if (result != 0)
    {
      fprintf(stderr, "Thread creation failed!\n");
      exit(1);
    }
  }
}