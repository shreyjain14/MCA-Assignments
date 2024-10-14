#include<stdio.h>

int main() {

    while (1) {
        printf("Enter a number: ");
        int num;
        if (scanf("%d", &num) != 1) {
            while (getchar() != '\n');
            printf("Invalid input. Please enter an integer.\n");
            continue;
        }

        if (num > 0) {
            printf("Number Accepted: %d\n", num);
            break;
        } else {
            printf("Please enter a positive int!\n");
        }
    }

    return 0;
}