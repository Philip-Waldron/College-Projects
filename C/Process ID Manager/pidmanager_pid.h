#if ! defined(project4_pid_H)
#define project4_pid_H

#define MIN_PID 300
#define MAX_PID 5000

int allocate_map();
int allocate_pid();
void release_pid(int pid);
void *runner(void *pid);

int *map;

#endif
