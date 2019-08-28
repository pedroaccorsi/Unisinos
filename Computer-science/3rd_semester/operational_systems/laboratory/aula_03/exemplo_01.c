#include <stdio.h>

int main(){

    int a, b, c;

    printf("\tEntre o primeiro valor: ");
    scanf("%d", &a);

    printf("\tEntre o segundo valor: ");
    scanf("%d", &b);

    c = a + b;

    printf("\t%d + %d = %d\n", a, b, c);

    return 0;

}