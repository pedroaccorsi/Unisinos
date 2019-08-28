#include <stdio.h>
#include <string.h>

void imprime_bytes_dec(char* iv_string){

    int i = 0;
    
    for( i = 0; i < strlen(iv_string); i++){
        printf("char: %c, ascii: %d\n", iv_string[i],iv_string[i]);
    }

}
int main(){

    char string[128];

    printf("Digite a string: ");
    scanf("%[^\n]s", string);

    imprime_bytes_dec(string);

    return 0;

}