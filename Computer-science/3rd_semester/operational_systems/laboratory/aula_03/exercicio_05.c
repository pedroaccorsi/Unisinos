#include <stdio.h>
#include <string.h>

void imprime_bytes_hex( int iv_integer ){

    char lv_string[500];
    int i;
    
    sprintf(lv_string, "%d", iv_integer);
    
    
    for(i = strlen(lv_string) -1; i >=0 ; i--){
        int dec_value = lv_string[i] - '0';
        printf("int: %c, hexa: 0x%X\n", lv_string[i], dec_value);
    }
}

int main(){

    int integer=0;

    printf("Digite um inteirio: ");
    scanf("%d", &integer);

    imprime_bytes_hex(integer);

    return 0;

}