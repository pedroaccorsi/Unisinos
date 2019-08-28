#include <stdio.h>
#include <string.h>

int main(){

    char string[128];
    int i=0;

    printf("Digite a string: ");
    scanf("%[^\n]s", string);

    for( i = 0; i < strlen(string); i++){
        printf("%c\n", string[i]);
    }

    return 0;

}