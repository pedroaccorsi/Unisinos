#include <stdio.h>

int main(){

    int a, b;
    a = b = 5;
    
    printf("%d\n", ++a + 5);  //vai printar 11
    printf("%d\n", a);        //vai printar 6
    printf("%d\n", b++ + 5);  //vai printar 10
    printf("%d\n", b);        //vai printar 6
    
    return 0;

}