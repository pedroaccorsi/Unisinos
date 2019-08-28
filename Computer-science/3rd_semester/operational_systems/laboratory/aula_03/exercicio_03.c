#include <stdio.h>
#include <string.h>

void print_by_char(char* iv_string){

    int i = 0;
    
    for( i = 0; i < strlen(iv_string); i++){
        printf("%c\n", iv_string[i]);
    }

}
int main(){

    char string[128];

    printf("Digite a string: ");
    scanf("%[^\n]s", string);

    print_by_char(string);

    return 0;

}