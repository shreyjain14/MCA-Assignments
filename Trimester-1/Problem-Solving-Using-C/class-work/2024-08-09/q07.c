#include <stdio.h>
#include <ctype.h>

void countCharacters(const char *str, int *alphabets, int *digits, int *specialChars) {
    *alphabets = 0;
    *digits = 0;
    *specialChars = 0;

    while (*str) {
        if (isalpha(*str)) {
            (*alphabets)++;
        } else if (isdigit(*str)) {
            (*digits)++;
        } else {
            (*specialChars)++;
        }
        str++;
    }
}

int main() {
    char str[100];
    int alphabets, digits, specialChars;

    printf("Enter a string: ");
    fgets(str, sizeof(str), stdin);

    countCharacters(str, &alphabets, &digits, &specialChars);

    printf("Alphabets: %d\n", alphabets);
    printf("Digits: %d\n", digits);
    printf("Special Characters: %d\n", specialChars);

    return 0;
}