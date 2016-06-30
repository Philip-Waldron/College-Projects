Author: Waldron, Philip

*.c files: pidmanager_main.c, pidmanager_pid.c
*.h files: pidmanager_pid.h

Compile sequence:
   gcc -c pidmanager_main.c
   gcc -c pidmanager_pid.c
   gcc pidmanager_main.o pidmanager_pid.o -o pidmanager -pthread

Execute command:
   ./pidmanager NumberOfProcesses

Platforms tested:
   Fedora Linux version 20 (Heisenbug)

*Note if makefile does not work, use aforementioned compilation sequence

pidmanager_pid.c contains the functions called allocate_map, allocate_pid and release_pid and uses project4_pid.h.
  - allocate_map creates and initialises the data structure for representing PIDs.
  - allocate_pid returns a PID if there’s a PID not in use.
  - release_pid releases a PID.

pidmanager_pid.h contains #DEFINE MIN_PID value, #DEFINE MAX_PID value and the function prototypes defined in project4_pid.c.


pidmanager_main.c contains the main function and the function runner to simulate a process.