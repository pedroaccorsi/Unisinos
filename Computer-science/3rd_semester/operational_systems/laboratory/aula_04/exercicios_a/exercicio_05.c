#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

#define gd_new_line printf("\n");

void repete(int iv_number_of_repeats, char* iv_args[]){
    for(int i=0; i < iv_number_of_repeats; i++){
		printf("#%d: %s, pid: %d, ppid: %d\n", i+1, iv_args[1], getpid(), getppid());
        sleep(1);
    }
}

int main(int argc, char *argv[]){

    int lv_number_of_repeats;
    pid_t pid;
   	int status;

    if (argc > 2) {
        printf("mais de 1 argumento, errrouu!");
        gd_new_line;
        exit(42);
    }
    
    printf("Digite a quantidade de repetições: ");
    scanf("%d", &lv_number_of_repeats);
    
    gd_new_line;

	if ((pid=fork()) < 0) {
		perror("erro no fork!"); 	 
	}
	else if (pid == 0) {
        gd_new_line;
    	repete(lv_number_of_repeats, argv);
        gd_new_line;
        exit(0);
	}
	
    wait(&status);

	printf("pid: %d, ppid: %d\n",getpid(), getppid());
    printf("also, o processo filho terminou com status = %d", WEXITSTATUS(status));
	gd_new_line;
    exit(0);
}
