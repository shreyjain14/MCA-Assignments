#include<stdio.h>

int check_odd_even(int a) {
    if (a % 2 == 0) {
        return 0;
    } else {
        return 1;
    }
}

int main() {
    
    int a = 10;
    
    if (check_odd_even(a) == 0) {
        printf("%d is even\n", a);
    } else {
        printf("%d is odd\n", a);
    }

    return 0;
}