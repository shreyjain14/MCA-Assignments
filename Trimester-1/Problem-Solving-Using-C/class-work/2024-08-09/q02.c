#include <stdio.h>

#define ROWS 3
#define COLS 3

void multiplyMatrices(int mat1[][COLS], int mat2[][COLS], int result[][COLS]) {
    int i, j, k;
    
    for (i = 0; i < ROWS; i++) {
        for (j = 0; j < COLS; j++) {
            result[i][j] = 0;
            for (k = 0; k < COLS; k++) {
                result[i][j] += mat1[i][k] * mat2[k][j];
            }
        }
    }
}

void displayMatrix(int matrix[][COLS]) {
    int i, j;
    
    for (i = 0; i < ROWS; i++) {
        for (j = 0; j < COLS; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int main() {
    int mat1[ROWS][COLS] = {{1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9}};
                            
    int mat2[ROWS][COLS] = {{9, 8, 7},
                            {6, 5, 4},
                            {3, 2, 1}};
                            
    int result[ROWS][COLS];
    
    multiplyMatrices(mat1, mat2, result);
    
    printf("Resultant matrix:\n");
    displayMatrix(result);
    
    return 0;
}