#include <stdio.h>

#define A 30

int main() {
    int s = (A - 6) / 2;

    for (int i = 0; i < A / 3; i++) {
        if (i == 0 || i == A / 3 - 1) {
            for (int j = 0; j < A; j++) {
                printf("#");
            }
            printf("\n");
        } else {
            printf("#%*s#**#%*s#\n", s, "", s, "");
        }
    }

    return 0;
}