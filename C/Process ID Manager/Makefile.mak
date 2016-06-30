NAME=pidmanager
LIBS=
SOURCES=\
	pidmanager_main.c pidmanager_pid.c


CC=gcc
CFLAGS=-g -Wall

CXX=g++
CXXFLAGS=$(CFLAGS)

LD=gcc
LDFLAGS=-pthread


OBJECTS=${SOURCES:.c=.o}


.PHONY: clean
.SUFFIXES: .o .c .cc .cpp


all: program

clean:
	$(RM) $(OBJECTS)
	$(RM) $(NAME)

.c.o:
	$(CC) -c $(CFLAGS) -o $@ $<

.cc.o:
	$(CXX) -c $(CXXFLAGS) -o $@ $<

.cpp.o:
	$(CXX) -c $(CXXFLAGS) -o $@ $<


program: $(NAME)
$(NAME): $(OBJECTS)
	$(LD) $(LIBS) -o $@ $^ $(LDFLAGS)
