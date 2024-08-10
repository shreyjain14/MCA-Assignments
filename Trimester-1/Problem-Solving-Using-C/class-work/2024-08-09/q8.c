#include <stdio.h>

void countVowelsAndConsonants(char* str, int* vowels, int* consonants) {
    *vowels = 0;
    *consonants = 0;
    
    while (*str) {
        if (*str == 'a' || *str == 'e' || *str == 'i' || *str == 'o' || *str == 'u' ||
            *str == 'A' || *str == 'E' || *str == 'I' || *str == 'O' || *str == 'U') {
            (*vowels)++;
        } else if ((*str >= 'a' && *str <= 'z') || (*str >= 'A' && *str <= 'Z')) {
            (*consonants)++;
        }
        str++;
    }
}

int main() {
    char str[100];
    int vowels, consonants;
    
    printf("Enter a string: ");
    fgets(str, sizeof(str), stdin);
    
    countVowelsAndConsonants(str, &vowels, &consonants);
    
    printf("Vowels: %d\n", vowels);
    printf("Consonants: %d\n", consonants);
    
    return 0;
}