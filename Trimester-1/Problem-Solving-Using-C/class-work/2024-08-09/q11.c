#include <stdio.h>

void swapCase(char *str) {
    while (*str) {
        if (*str >= 'a' && *str <= 'z') {
            *str = *str - 32; // convert to upper case
        } else if (*str >= 'A' && *str <= 'Z') {
            *str = *str + 32; // convert to lower case
        }
        str++;
    }
}

int main() {
    char str[100];

    printf("Enter a string: ");
    fgets(str, sizeof(str), stdin);

    swapCase(str);

    printf("String after swapping case: %s", str);

    return 0;
}