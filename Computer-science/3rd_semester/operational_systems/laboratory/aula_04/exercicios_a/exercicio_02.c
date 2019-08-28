#include<stdio.h>
#include<stdlib.h>

#define gd_new_line printf("\n");

void repete(int iv_number_of_repeats, char* iv_args[]){
    for(int i=0; i < iv_number_of_repeats; i++){
		printf("#%d: %s\n", i+1, iv_args[1]);
	}
}

int main(int argc, char *argv[]){

    int lv_number_of_repeats;

    if (argc > 2) {
        printf("mais de 1 argumento, errrouu!");
        gd_new_line;
        exit(42);
    }
    
    printf("Digite a quantidade de repetições: ");
    scanf("%d", &lv_number_of_repeats);
    
    gd_new_line;

    repete(lv_number_of_repeats, argv);

    gd_new_line;

	exit(0);
	
}
