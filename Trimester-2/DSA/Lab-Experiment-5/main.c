#include <stdio.h>
#include <stdlib.h>
#include "searching.h"
#include "sorting.h"

int main()
{
    int productID[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    float prices[10] = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

    while (1)
    {
        printf("\n=================================================\n");
        printf("1. Binary Search ProductIDs\n");
        printf("2. Linear Search ProductIDs\n");
        printf("3. Sentinel Search ProductIDs\n");
        printf("4. Binary Search Prices\n");
        printf("5. Linear Search Prices\n");
        printf("6. Sentinel Search Prices\n");
        printf("7. Bubble Sort ProductIDs\n");
        printf("8. Insertion Sort ProductIDs\n");
        printf("9. Exit\n");
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
                printf("ProductID not found\n");
            }
            else
            {
                printf("ProductID found at index %d\n", index);
            }
            break;
        case 2:
            printf("Enter the productID to be searched: ");
            int key2;
            scanf("%d", &key2);
            int index2 = linearSearch(productID, 10, key2);
            if (index2 == -1)
            {
                printf("ProductID not found\n");
            }
            else
            {
                printf("ProductID found at index %d\n", index2);
            }
            break;
        case 3:
            printf("Enter the productID to be searched: ");
            int key3;
            scanf("%d", &key3);
            int index3 = sentinelSearch(productID, 10, key3);
            if (index3 == -1)
            {
                printf("ProductID not found and not enough space to insert\n");
            }
            else
            {
                printf("ProductID found at index %d\n", index3);
            }
            break;
        case 4:
            printf("Enter the price to be searched: ");
            float priceKey;
            scanf("%f", &priceKey);
            int priceIndex = binarySearchFloat(prices, 0, 9, priceKey);
            if (priceIndex == -1)
            {
                printf("Price not found\n");
            }
            else
            {
                printf("Price found at index %d\n", priceIndex);
            }
            break;
        case 5:
            printf("Enter the price to be searched: ");
            float priceKey2;
            scanf("%f", &priceKey2);
            int priceIndex2 = linearSearchFloat(prices, 10, priceKey2);
            if (priceIndex2 == -1)
            {
                printf("Price not found\n");
            }
            else
            {
                printf("Price found at index %d\n", priceIndex2);
            }
            break;
        case 6:
            printf("Enter the price to be searched: ");
            float priceKey3;
            scanf("%f", &priceKey3);
            int priceIndex3 = sentinelSearchFloat(prices, 10, priceKey3);
            if (priceIndex3 == -1)
            {
                printf("Price not found and not enough space to insert\n");
            }
            else
            {
                printf("Price found at index %d\n", priceIndex3);
            }
            break;
        case 7:
            printf("Enter 10 productIDs to be sorted: ");
            int arr2[10];
            for (int i = 0; i < 10; i++)
            {
                scanf("%d", &arr2[i]);
            }
            bubbleSort(arr2, 10);
            for (int i = 0; i < 10; i++)
            {
                printf("%d ", arr2[i]);
            }
            printf("\n");
            break;
        case 8:
            printf("Enter 10 productIDs to be sorted: ");
            int arr[10];
            for (int i = 0; i < 10; i++)
            {
                scanf("%d", &arr[i]);
            }
            insertionSort(arr, 10);
            for (int i = 0; i < 10; i++)
            {
                printf("%d ", arr[i]);
            }
            printf("\n");
            break;
        case 9:
            exit(0);
            break;
        default:
            printf("Invalid choice\n");
            break;
        }
    }
}
