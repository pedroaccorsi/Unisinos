#include <stdio.h>

int main(){

    int i, soma, n, x;
    float media;

    printf("Quantos números para cálculo da média?");
    scanf("%d", &n);

    for(soma = 0, i = 0; i < n; i++){

        printf("Digite o %d número: ", i);
        scanf("%d", &x);

        soma += x;

    }

    media = (float) soma / n;

    printf("A média é: %f\n", media);

    return 0;

}