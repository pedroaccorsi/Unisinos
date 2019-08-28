#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <locale.h>

#define gd_new_line printf("\n");
#define gd_clear_screen system("@cls||clear");

void gotoxy(int x, int y){
     printf("\033[%d;%df", x, y) ;
     int i=2;
     while (i--) {}
}

void show_menu(){
    gd_clear_screen;
    gotoxy(0,0);
    printf("Trabalho GA  - Sistemas Operacionais");
    printf("\n\tGabriel Pereira da Conceição");
    printf("\n\tPedro Henrique Accorsi");
    printf("\n\tRafaela Kreush");

    gd_new_line;gd_new_line;gd_new_line;
    
    printf("<<<< Applications Menu >>>");
    printf("\n\t1) Web Browser");
    printf("\n\t2) Text Editor");
    printf("\n\t3) Terminal ");
    printf("\n\t4) Finalizar processo");
    printf("\n\t5) Quit");
}

int get_option(){
    int lv_return;
    printf("\nOpção: ");
    scanf("%d", &lv_return);
    return lv_return;
}