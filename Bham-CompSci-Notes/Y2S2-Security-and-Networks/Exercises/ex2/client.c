#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define BUFFERLENGTH 256

/* displays error messages from system calls */
void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, n;
    struct addrinfo hints;
    struct addrinfo *result, *rp;
    int res;

    char buffer[BUFFERLENGTH];

    if (argc < 4)
    {
        fprintf(stderr, "Usage: %s <serverHost> <serverPort> <action> [args]\n", argv[0]);
        fprintf(stderr, "Actions: A - Add rule, C - Check IP and port, D - Delete rule, L - Show firewall rules\n");
        exit(1);
    }

    char action = argv[3][0];

    if (action != 'A' && action != 'C' && action != 'D' && action != 'L')
    {
        fprintf(stderr, "Invalid Client Call\n");
        exit(1);
    }

    /* Obtain address(es) matching host/port */
    /* code taken from the manual page for getaddrinfo */

    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_family = AF_UNSPEC;     /* Allow IPv4 or IPv6 */
    hints.ai_socktype = SOCK_STREAM; /* Datagram socket */
    hints.ai_flags = 0;
    hints.ai_protocol = 0; /* Any protocol */

    res = getaddrinfo(argv[1], argv[2], &hints, &result);
    if (res != 0)
    {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(res));
        exit(EXIT_FAILURE);
    }

    /* getaddrinfo() returns a list of address structures.
       Try each address until we successfully connect(2).
       If socket(2) (or connect(2)) fails, we (close the socket
       and) try the next address. */

    for (rp = result; rp != NULL; rp = rp->ai_next)
    {
        sockfd = socket(rp->ai_family, rp->ai_socktype, rp->ai_protocol);
        if (sockfd == -1)
            continue;

        if (connect(sockfd, rp->ai_addr, rp->ai_addrlen) != -1)
            break; /* Success */

        close(sockfd);
    }

    if (rp == NULL)
    {
        fprintf(stderr, "Could not connect\n");
        exit(EXIT_FAILURE);
    }

    freeaddrinfo(result);

    /* prepare message based on the action */
    if (action == 'A')
    {   
        
        snprintf(buffer, BUFFERLENGTH, "A:%s:%s", argv[4], argv[5]);
    }
    else if (action == 'C')
    {   
        //printf("%d\n", argc);
        if(argc != 6){
            printf("%s", "Illegal IP address or port specified");
            //close(sockfd);
            snprintf(buffer, BUFFERLENGTH, "$");
            //return 0;
        } else {
        snprintf(buffer, BUFFERLENGTH, "C:%s:%s", argv[4], argv[5]);
        }
    }
    else if (action == 'D')
    {
        if(argc != 6){
            printf("%s", "Rule invalid");
            //close(sockfd);
            snprintf(buffer, BUFFERLENGTH, "$");
            //return 0;
        } else {
        snprintf(buffer, BUFFERLENGTH, "D:%s:%s", argv[4], argv[5]);
        }
    }
    else if (action == 'L')
    {
        snprintf(buffer, BUFFERLENGTH, "L");
    }
    else
    {
        snprintf(buffer, BUFFERLENGTH, "error");
    }

    /* send message */
    n = write(sockfd, buffer, strlen(buffer));
    if (n < 0)
        error("ERROR writing to socket");
    bzero(buffer, BUFFERLENGTH);

    /* wait for reply */
    if (action == 'L' /*|| action == 'D'*/)
    {
        while (strcmp(buffer, "$") !=0)
        {
            if(strcmp(buffer, "")){
                printf("%s\n", buffer);
            }
            n = read(sockfd, buffer, BUFFERLENGTH - 1);
            if (n < 0)
                error("ERROR reading from socket");
            n = write(sockfd, "RET", strlen(buffer));
            if (n < 0)
                error("ERROR writing from socket");
        } 
    }
    else
    {
        n = read(sockfd, buffer, BUFFERLENGTH - 1);
        if (n < 0)
            error("ERROR reading from socket");
        printf("%s\n", buffer);
    }
    close(sockfd);
    return 0;
}
