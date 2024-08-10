#include <stdio.h>
#include <string.h>

void getSubstring(const char* str, int start, int length, char* result) {
    int i;
    for (i = 0; i < length && str[start + i] != '\0'; i++) {
        result[i] = str[start + i];
    }
    result[i] = '\0';
}

int main() {
    char str[100];
    int start, length;

    printf("Enter a string: ");
    scanf("%s", str);

    printf("Enter the starting index: ");
    scanf("%d", &start);

    printf("Enter the length: ");
    scanf("%d", &length);
    char result[100];

    getSubstring(str, start, length, result);

    printf("Substring: %s\n", result);

    return 0;
}