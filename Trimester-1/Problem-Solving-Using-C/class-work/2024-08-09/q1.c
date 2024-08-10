#include<stdio.h>

#define MAX_SIZE 100

int main() {
    int matrix[MAX_SIZE][MAX_SIZE];
    int rows, cols;

    printf("Enter the number of rows: ");
    scanf("%d", &rows);

    printf("Enter the number of columns: ");
    scanf("%d", &cols);

    printf("Enter the elements of the matrix:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            scanf("%d", &matrix[i][j]);
        }
    }

    printf("Diagonal elements of the matrix:\n");
    for (int i = 0; i < rows; i++) {
        printf("%d ", matrix[i][i]);
    }
    printf("\n");

    return 0;
}