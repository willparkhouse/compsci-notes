CFLAGS = -Wall -Werror -g -fdiagnostics-color=always

all: server client

server: server.o
	$(CC) $(CFLAGS) -o server server.o -lpthread

server.o: server.c
	$(CC) $(CFLAGS) -c server.c


client: client.o
	$(CC) $(CFLAGS)  -o client client.o

client.o: client.c
	$(CC) $(CFLAGS) -c client.c

clean:
	rm -f *.o server client
