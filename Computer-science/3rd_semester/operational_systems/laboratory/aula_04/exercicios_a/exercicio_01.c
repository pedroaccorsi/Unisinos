#include<stdio.h>
#include<stdlib.h>

#define gd_new_line printf("\n");


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

	for(int i=0; i < lv_number_of_repeats; i++){
		printf("#%d: %s\n", i+1, argv[1]);
	}

    gd_new_line;

	exit(0);
	
}
