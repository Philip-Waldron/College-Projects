#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "project4_pid.h"

/* Creates and initialises a data structure for representing pids;
   returns 1 if unsuccessful, 1 if successful */
int allocate_map()
{
	map = (int *) calloc((MAX_PID - MIN_PID + 1), sizeof(int));
	if (map == 0)
	{
		printf("\nFailed to initialise data structure.\n");
		return -1;
	}

	printf("\nData Structure initialised.\n");
	return 1;
}

/* Allocates and returns a pid; returns -1
if unable to allocate a pid (all pids are in use) */
int allocate_pid()
{
	int i = 0;
	int pid = map[i];

	while (pid != 0 && i != MAX_PID - MIN_PID)
	{
		i++;
		pid = map[i];
	}

	if (i == MAX_PID - MIN_PID)
		return -1;

	map[i] = i + MIN_PID;
	return map[i];
}

/* Releases a pid */
void release_pid(int pid)
{
	int i = pid - MIN_PID;
	map[i] = 0;
	printf("\nPid %d released", pid);
}
