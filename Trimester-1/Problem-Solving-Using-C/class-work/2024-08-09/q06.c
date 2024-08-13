#include <stdio.h>
#include <string.h>

void printReverseString(const char* str) {
    int length = strlen(str);
    
    for (int i = length - 1; i >= 0; i--) {
        printf("%c\n", str[i]);
    }
    
    printf("\n");
}

int main() {
    const char* str = "Hello, World!";
    
    printReverseString(str);
    
    return 0;
}