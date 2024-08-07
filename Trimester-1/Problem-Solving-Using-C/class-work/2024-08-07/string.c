#include <stdio.h>
#include <string.h>

int main() {
    char str1[100];
    printf("Enter string 1: ");
    scanf("%s", str1);
    
    printf("Size of str1: %lu\n", sizeof(str1));
    printf("Length of str1: %lu\n", strlen(str1));
    
    return 0;
}
