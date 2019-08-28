#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <locale.h>
#include "utilities.h"

int main(){   

    setlocale(LC_ALL, "Portuguese");
    system("cls");

    show_menu();
    int lv_option = get_option();
    show_menu();


}
