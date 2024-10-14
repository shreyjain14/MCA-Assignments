#include <stdio.h>

#define ROWS 3
#define COLS 3

void findMaxTwoElements(int matrix[ROWS][COLS], int *max1, int *max2) {
    *max1 = *max2 = matrix[0][0];

    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            if (matrix[i][j] > *max1) {
                *max2 = *max1;
                *max1 = matrix[i][j];
            } else if (matrix[i][j] > *max2 && matrix[i][j] != *max1) {
                *max2 = matrix[i][j];
            }
        }
    }
}

int main() {
    int matrix[ROWS][COLS] = {
        {3, 8, 1},
        {9, 2, 7},
        {4, 6, 5}
    };

    int max1, max2;
    findMaxTwoElements(matrix, &max1, &max2);

    printf("The largest element is: %d\n", max1);
    printf("The second largest element is: %d\n", max2);

    return 0;
}
