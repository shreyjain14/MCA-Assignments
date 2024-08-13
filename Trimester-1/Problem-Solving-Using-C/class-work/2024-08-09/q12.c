#include <stdio.h>

#define MAX_SIZE 100

void calculateFrequency(char str[]) {
    int freq[26] = {0}; // Array to store the frequency of each character

    // Iterate through each character in the string
    for (int i = 0; str[i] != '\0'; i++) {
        // Increment the frequency of the character
        if (str[i] >= 'a' && str[i] <= 'z') {
            freq[str[i] - 'a']++;
        } else if (str[i] >= 'A' && str[i] <= 'Z') {
            freq[str[i] - 'A']++;
        }
    }

    // Print the frequency of each character
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) {
            printf("Frequency of %c = %d\n", 'a' + i, freq[i]);
        }
    }
}

int main() {
    char str[MAX_SIZE];

    printf("Enter a string: ");
    fgets(str, sizeof(str), stdin);

    calculateFrequency(str);

    return 0;
}