#include <stdio.h>
#include <string.h>

void imprime_bytes_bin( int iv_integer ){

    char lv_string[500];
    int i;
    int lv_shifted_value;
    int lv_shifted_times;
    
    sprintf(lv_string, "%d", iv_integer);
    
    
    for(i = strlen(lv_string) -1; i >=0 ; i--){
        int dec_value = lv_string[i] - '0';

        printf("int: %c, bin: 0b", lv_string[i]);

            for (lv_shifted_times= 15; lv_shifted_times >= 0; lv_shifted_times--)
            {
                lv_shifted_value = dec_value >> lv_shifted_times;
            
                if (lv_shifted_value & 1)
                printf("1");
                else
                printf("0");
            }

        printf("\n");

    }
}

int main(){

    int integer=0;

    printf("Digite um inteirio: ");
    scanf("%d", &integer);

    imprime_bytes_bin(integer);

    return 0;

}