#include <stdio.h>
#include <stdlib.h>
#include "searching.h"
#include "sorting.h"

int main()
{

    int productID[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    while (1)
    {

        printf("\n=================================================\n");
        printf("1. Binary Search ProductIDs\n");
        printf("2. Linear Search ProductIDs\n");
        printf("3. Sentinel Search ProductIDs\n");
        printf("4. Bubble Sort ProsuctIDs\n");
        printf("5. Insertion Sort ProsuctIDs\n");
        printf("6. Exit\n");
        printf("=================================================\n");

        int choice;
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice)
        {
        case 1:
            printf("Enter the productID to be searched: ");
            int key;
            scanf("%d", &key);
            int index = binarySearch(productID, 0, 9, key);
            if (index == -1)
            {
                printf("productID not found\n");
            }
            else
            {
                printf("productID found at index %d\n", index);
            }
            break;
        case 2:
            printf("Enter the productID to be searched: ");
            int key2;
            scanf("%d", &key2);
            int index2 = linerSearch(productID, 10, key2);
            if (index2 == -1)
            {
                printf("productID not found\n");
            }
            else
            {
                printf("productID found at index %d\n", index2);
            }
            break;
        case 3:
            printf("Enter the productID to be searched: ");
            int key3;
            scanf("%d", &key3);
            int index3 = sentinelSearch(productID, 10, key3);
            if (index3 == -1)
            {
                printf("productID not found and not enough space to insert\n");
            }
            else
            {
                printf("productID found at index %d\n", index3);
            }
            break;
        case 4:
            printf("Enter 10 productIDs to be sorted: ");
            int arr[10];
            for (int i = 0; i < 10; i++)
            {
                scanf("%d", &arr[i]);
            }
            bubbleSort(arr, 10);
            for (int i = 0; i < 10; i++)
            {
                printf("%d ", arr[i]);
            }
            break;
        case 5:
            printf("Enter 10 productIDs to be sorted: ");
            int arr2[10];
            for (int i = 0; i < 10; i++)
            {
                scanf("%d", &arr2[i]);
            }
            insertionSort(arr2, 10);
            for (int i = 0; i < 10; i++)
            {
                printf("%d ", arr2[i]);
            }
            break;
        case 6:
            exit(0);
            break;
        default:
            printf("Invalid choice\n");
            break;
        }
    }
}