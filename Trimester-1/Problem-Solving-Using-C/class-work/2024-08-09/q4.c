#include <stdio.h>

void swapRows(int matrix[][3], int row1, int row2, int cols) {
    for (int i = 0; i < cols; i++) {
        int temp = matrix[row1][i];
        matrix[row1][i] = matrix[row2][i];
        matrix[row2][i] = temp;
    }
}

int main() {
    int matrix[3][3] = {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};

    int row1 = 0; // First row to swap
    int row2 = 2; // Second row to swap

    printf("Before swapping:\n");
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }

    swapRows(matrix, row1, row2, 3);

    printf("\nAfter swapping:\n");
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }

    return 0;
}