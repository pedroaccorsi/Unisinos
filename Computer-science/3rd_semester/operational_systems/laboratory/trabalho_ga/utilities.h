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
    printf("\n\t1) Web Browser               %s", get_browser_status()     );
    printf("\n\t2) Text Editor               %s", get_text_editor_status() );
    printf("\n\t3) Terminal                  %s", get_terminal_Status()    );
    printf("\n\t4) Finalizar processo        %s", get_termination_status() );
    printf("\n\t5) Quit");
}

int get_option(){
    int rv_return;
    printf("\nOpção: ");
    scanf("%d", &rv_return);
    return rv_return;
}
