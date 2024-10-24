#include <stdio.h>
#include <stdlib.h>

struct node {
    int data;
    struct node *next;
};
struct node *head = NULL;

void insertAtBegining(int data, char name) {
    struct node *newNode = (struct node *) malloc(sizeof(struct node));
    newNode->data = data;
    newNode->next = head;
    head = newNode;
}

void insertAtEnd(int data) {
    struct node *newNode = (struct node *) malloc(sizeof(struct node));
    newNode->data = data;
    newNode->next = NULL;
    if (head == NULL) {
        head = newNode;
    } else {
        struct node *temp = head;
        while (temp->next != NULL) {
            temp = temp->next;
        }
        temp->next = newNode;
    }
}

void insertAtPosition(int data, int position) {
    struct node *newNode = (struct node *) malloc(sizeof(struct node));
    newNode->data = data;
    if (position == 1) {
        newNode->next = head;
        head = newNode;
    } else {
        struct node *temp = head;
        for (int i = 1; i < position - 1; i++) {
            temp = temp->next;
        }
        newNode->next = temp->next;
        temp->next = newNode;
    }
}

void deleteAtPosition(int position) {
    if (position == 1) {
        struct node *temp = head;
        head = head->next;
        free(temp);
    } else {
        struct node *temp = head;
        for (int i = 1; i < position - 1; i++) {
            temp = temp->next;
        }
        struct node *temp2 = temp->next;
        temp->next = temp2->next;
        free(temp2);
    }
}

void display() {
    struct node *temp = head;
    while (temp != NULL) {
        printf("%d ", temp->data);
        temp = temp->next;
    }
    printf("\n");
}

void locateElement(int data) {
    struct node *temp = head;
    int position = 1;
    while (temp != NULL) {
        if (temp->data == data) {
            printf("Product ID found at position %d\n", position);
            return;
        }
        temp = temp->next;
        position++;
    }
    printf("Product ID not found\n");
}


int main() {

    while (1) {
        int choice;
        printf("========================\n");
        printf("WELCOME TO PRODUCTS ID PAGE\n");
        printf("1. Insert at Begining\n");
        printf("2. Insert at End\n");
        printf("3. Insert at Position\n");
        printf("4. Delete at Position\n");
        printf("5. Display\n");
        printf("6. Locate Element\n");
        printf("7. Exit\n");
        printf("========================\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice)
        {
        case 1:
            printf("Enter product: ");
            int product;
            scanf("%d", &product);
            insertAtBegining(product);
            break;
        
        case 2:
            printf("Enter product: ");
            int product2;
            scanf("%d", &product2);
            insertAtEnd(product2);
            break;

        case 3:
            printf("Enter product: ");
            int product3;
            scanf("%d", &product3);
            printf("Enter position: ");
            int position;
            scanf("%d", &position);
            insertAtPosition(product3, position);
            break;

        case 4:
            printf("Enter position: ");
            int position2;
            scanf("%d", &position2);
            deleteAtPosition(position2);
            break;

        case 5:
            display();
            break;

        case 6:
            printf("Enter product: ");
            int product4;
            scanf("%d", &product4);
            locateElement(product4);
            break;

        case 7:
            exit(0);
            break;

        default:
            printf("Invalid choice\n");
            break;
        }

    }

}
