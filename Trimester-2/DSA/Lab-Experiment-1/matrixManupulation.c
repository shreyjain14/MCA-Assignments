#include<stdio.h>
#include<stdlib.h>

#define MAX 100

void inputMatrix(int matrix[MAX][MAX], int row, int col) {
    printf("Enter the elements of the matrix:\n");
    for(int i=0; i<row; i++) {
        for(int j=0; j<col; j++) {
            scanf("%d", &matrix[i][j]);
        }
    }
}

void displayMatrix(int matrix[MAX][MAX], int row, int col) {
    printf("The matrix is:\n");
    for(int i=0; i<row; i++) {
        for(int j=0; j<col; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

void rowSum(int matrix[MAX][MAX], int row, int col) {
    int sum = 0;
    for(int i=0; i<row; i++) {
        sum = 0;
        for(int j=0; j<col; j++) {
            sum += matrix[i][j];
        }
        printf("The sum of the elements of row %d is %d\n", i+1, sum);
    }
}

void colSum(int matrix[MAX][MAX], int row, int col) {
    int sum = 0;
    for(int i=0; i<col; i++) {
        sum = 0;
        for(int j=0; j<row; j++) {
            sum += matrix[j][i];
        }
        printf("The sum of the elements of column %d is %d\n", i+1, sum);
    }
}

void addMatrix(int matrix1[MAX][MAX], int matrix2[MAX][MAX], int result[MAX][MAX], int row, int col) {
    for(int i=0; i<row; i++) {
        for(int j=0; j<col; j++) {
            result[i][j] = matrix1[i][j] + matrix2[i][j];
        }
    }
}

void subtractMatrix(int matrix1[MAX][MAX], int matrix2[MAX][MAX], int result[MAX][MAX], int row, int col) {
    for(int i=0; i<row; i++) {
        for(int j=0; j<col; j++) {
            result[i][j] = matrix1[i][j] - matrix2[i][j];
        }
    }
}

void multiplyMatrix(int matrix1[MAX][MAX], int matrix2[MAX][MAX], int result[MAX][MAX], int row1, int col1, int col2) {
    for(int i=0; i<row1; i++) {
        for(int j=0; j<col2; j++) {
            result[i][j] = 0;
            for(int k=0; k<col1; k++) {
                result[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }
}

int main() {
    int matrix1[MAX][MAX], matrix2[MAX][MAX], result[MAX][MAX];
    int row1, col1, row2, col2;
    printf("Enter the number of rows and columns of the first matrix:\n");
    scanf("%d %d", &row1, &col1);
    printf("Enter the number of rows and columns of the second matrix:\n");
    scanf("%d %d", &row2, &col2);

    int not_eql = 0;

    if(row1 != row2 || col1 != col2) {
        printf("The matrices cannot be added or subtracted\n");
        not_eql = 1;
    }
    
    while (1) {

        printf("------------------------\n");
        printf("1. Input the matrix A\n");
        printf("2. Input the matrix B\n");
        printf("3. Display the matrix A\n");
        printf("4. Display the matrix B\n");
        printf("5. Row sum of the matrix A\n");
        printf("6. Column sum of the matrix A\n");
        if (!not_eql) {
            printf("7. Add the matrices A and B\n");
            printf("8. Subtract the matrices A and B\n");
        }
        if (col1 == row2) {
            printf("9. Multiply the matrices A and B\n");
        }
        printf("10. Exit\n");
        printf("------------------------\n");

        int choice;

        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch(choice) {
            case 1:
                inputMatrix(matrix1, row1, col1);
                break;
            case 2:
                inputMatrix(matrix2, row2, col2);
                break;
            case 3:
                displayMatrix(matrix1, row1, col1);
                break;
            case 4:
                displayMatrix(matrix2, row2, col2);
                break;
            case 5:
                rowSum(matrix1, row1, col1);
                break;
            case 6:
                colSum(matrix1, row1, col1);
                break;
            case 7:
                addMatrix(matrix1, matrix2, result, row1, col1);
                displayMatrix(result, row1, col1);
                break;
            case 8:
                subtractMatrix(matrix1, matrix2, result, row1, col1);
                displayMatrix(result, row1, col1);
                break;
            case 9:
                multiplyMatrix(matrix1, matrix2, result, row1, col1, col2);
                displayMatrix(result, row1, col2);
                break;
            case 10:
                exit(0);
            default:
                printf("Invalid choice\n");
        }


    }

    return 0;
}
