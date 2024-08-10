#include <stdio.h>

#define SIZE 3

void printLowerDiagonal(int matrix[SIZE][SIZE]) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (j <= i) {
                printf("%d ", matrix[i][j]);
            }
        }
    }
}

int main() {
    int matrix[SIZE][SIZE] = {{1, 2, 3},
                              {4, 5, 6},
                              {7, 8, 9}};

    printf("Lower Diagonal: ");
    printLowerDiagonal(matrix);

    return 0;
}