#include<stdio.h>

int check_prime(int a) {
    int i;
    for (i = 2; i < a/2; i++) {
        if (a % i == 0) {
            return 0;
        }
    }
    return 1;
}

int main() {
    
    int a = 101;
    
    if (check_prime(a) == 0) {
        printf("%d is not prime\n", a);
    } else {
        printf("%d is prime\n", a);
    }

    return 0;
}