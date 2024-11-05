#include <stdio.h>
#include <stdlib.h>

struct node {
    int productID;
    struct node *next;
};

struct node *front = NULL;
struct node *rear = NULL;

void insert(int productID) {
    struct node *newNode = (struct node *)malloc(sizeof(struct node));
    newNode->productID = productID;
    newNode->next = NULL;
    if (!front && !rear) {
        front = rear = newNode;
    } else {
        rear->next = newNode;
        rear = newNode;
    }
}

void delete() {
    if (!front && !rear) {
        printf("Queue is empty\n");
    } else if (front == rear) {
        front = rear = NULL;
    } else {
        struct node *temp = front;
        front = front->next;
        free(temp);
    }
}

void display() {
    struct node *temp = front;
    while (temp != NULL) {
        printf("%d ", temp->productID);
        temp = temp->next;
    }
    printf("\n");
}

int main() {

    while (1) {

        printf("--------------------\n");
        printf("ECommerce Orders Queue\n");
        printf("1. Insert OrderID\n");
        printf("2. Delete OrderID\n");
        printf("3. Display OrderIDs\n");
        printf("4. Exit\n");
        printf("--------------------\n");

        int choice;
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1: {
                int productID;
                printf("Enter productID: ");
                scanf("%d", &productID);
                insert(productID);
                break;
            }
            case 2: {
                delete();
                break;
            }
            case 3: {
                display();
                break;
            }
            case 4: {
                exit(0);
            }
            default: {
                printf("Invalid choice\n");
            }
        }

    }

    return 0;

}
