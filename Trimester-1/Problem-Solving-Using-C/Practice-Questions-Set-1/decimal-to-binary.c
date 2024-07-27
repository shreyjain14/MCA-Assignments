#include<stdio.h>

int decimal_to_binary(int a) {
    int binary = 0;
    int base = 1;
    while (a > 0) {
        binary += (a % 2) * base;
        a /= 2;
        base *= 10;
    }
    return binary;
}

int main() {
    
    int a = 10;
    
    printf("Binary of %d is %d\n", a, decimal_to_binary(a));

    return 0;
}