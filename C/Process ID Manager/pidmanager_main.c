#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <time.h>
#include <pthread.h>

#include "project4_pid.h"

int main(int argc, char* argv[])
{
	char *inputStr, *inputPtr;
	int iterations, i, pid;
	
	//Check if correct no. of args are used
	if (argc != 2)
	{
		fprintf(stderr, "Usage: %s NumberOfProcesses\n", argv[0]);
		exit(1);
	}

	//Convert input to integer
	inputStr = argv[1];
	iterations = strtol(inputStr, &inputPtr, 0);
	if ((0 == iterations && inputStr == inputPtr))
	{
		fprintf(stderr, "Usage: %s NumberOfProcesses\n", argv[0]);
		exit(1);
	}

	pthread_t tid[iterations]; //Array to hold the thread IDs
	int *t_index[iterations]; //Array to pass an index to each thread indicating the pid

	//Multi-threading section
	if (allocate_map())
	{
		srand(time(NULL));
		//Create threads
		for (i = 0; i < iterations; i++)
		{
			pid = allocate_pid();
			if (pid != -1)
			{
				t_index[i] = (int *)malloc(sizeof(int));
				*t_index[i] = pid;
				printf("\nProcess %d: pid = %d", i + 1, pid);
				pthread_create(&tid[i], NULL, runner, (void *) t_index[i]);
				//sleep(1); Code to more easily show that program works as intended
			}
			else
				printf("\nNo pids currently available!");
		}
		
		for (i = 0; i < iterations; i++)
		{
			pthread_join(tid[i], 0);
			free(t_index[i]);
		}
	}

	printf("\n");
	return 0;
}

//Simulates a process
void *runner(void *pid)
{
	int rpid = *((int *) pid);
	sleep((rand() % 5) + 1);
	release_pid(rpid);
}

