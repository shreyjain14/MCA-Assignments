#include <stdio.h>

int main() {
    while (1) {
        int a;
        printf("Select Size: ");
        scanf("%d", &a);
        int s = (a - (a / 6)) / 2;

        for (int i = 0; i < a / 3; i++) {
            if (i == 0 || i == a / 3 - 1) {
                for (int j = 0; j < a + 3; j++) {
                    printf("-");
                }
                printf("\n");
            } else {
                printf("|");
                for (int j = 0; j < s; j++) {
                    printf(" ");
                }
                printf("|");
                for (int j = 0; j < a / 6; j++) {
                    printf("*");
                }
                printf("|");
                for (int j = 0; j < s; j++) {
                    printf(" ");
                }
                printf("|\n");
            }
        }

        for (int i = 0; i < a / 3; i++) {
            if (i == 0 || i == a / 3 - 1) {
                for (int j = 0; j < a + 3; j++) {
                    printf("-");
                }
                printf("\n");
            } else {
                printf("|");
                for (int j = 0; j < s; j++) {
                    printf("*");
                }
                printf("|");
                for (int j = 0; j < a / 6; j++) {
                    printf("*");
                }
                printf("|");
                for (int j = 0; j < s; j++) {
                    printf("*");
                }
                printf("|\n");
            }
        }
    }

    return 0;
}