#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct Node
{
    int data;
    struct Node *next;
};

struct Node *insertAtBeginning(struct Node *head, int value)
{
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    newNode->data = value;
    newNode->next = head;
    return newNode;
}

void insertionSortArray(int arr[], int n)
{
    int i, key, j;
    for (i = 1; i < n; i++)
    {
        key = arr[i];
        j = i - 1;
        while (j >= 0 && arr[j] > key)
        {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

struct Node *insertionSortList(struct Node *head)
{
    if (head == NULL || head->next == NULL)
        return head;

    struct Node *sorted = NULL;
    struct Node *current = head;

    while (current != NULL)
    {
        struct Node *next = current->next;

        if (sorted == NULL || sorted->data >= current->data)
        {
            current->next = sorted;
            sorted = current;
        }
        else
        {
            struct Node *temp = sorted;
            while (temp->next != NULL && temp->next->data < current->data)
            {
                temp = temp->next;
            }
            current->next = temp->next;
            temp->next = current;
        }
        current = next;
    }
    return sorted;
}

void printArray(int arr[], int n)
{
    for (int i = 0; i < n; i++)
        printf("%d ", arr[i]);
    printf("\n");
}

void printList(struct Node *head)
{
    while (head != NULL)
    {
        printf("%d ", head->data);
        head = head->next;
    }
    printf("\n");
}

int main()
{
    int arr[] = {64, 34, 25, 12, 22, 11, 90};
    int n = sizeof(arr) / sizeof(arr[0]);

    clock_t start, end;
    double cpu_time_used;

    printf("Original Array: ");
    printArray(arr, n);

    start = clock();
    insertionSortArray(arr, n);
    end = clock();

    printf("Sorted Array: ");
    printArray(arr, n);
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken for Array sort: %f seconds\n\n", cpu_time_used);

    struct Node *head = NULL;
    head = insertAtBeginning(head, 90);
    head = insertAtBeginning(head, 11);
    head = insertAtBeginning(head, 22);
    head = insertAtBeginning(head, 12);
    head = insertAtBeginning(head, 25);
    head = insertAtBeginning(head, 34);
    head = insertAtBeginning(head, 64);

    printf("Original List: ");
    printList(head);

    start = clock();
    head = insertionSortList(head);
    end = clock();

    printf("Sorted List: ");
    printList(head);
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken for Linked List sort: %f seconds\n", cpu_time_used);

    return 0;
}

/*
Time Complexity: O(n^2)

OUTPUT:

Original Array: 64 34 25 12 22 11 90
Sorted Array: 11 12 22 25 34 64 90
Time taken for Array sort: 0.000000 seconds

Original List: 64 34 25 12 22 11 90
Sorted List: 11 12 22 25 34 64 90
Time taken for Linked List sort: 0.000000 seconds
*/