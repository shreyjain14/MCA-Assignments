#include <stdio.h>
#include <stdlib.h>
#include <string.h>  // Include for strcpy()

struct node {
    char *name;
    int performanceScore;
    int employeeID;
    struct node *next;
};

struct node *llHead = NULL;

struct node *llTail = NULL;

void printNode(struct node *node) {
    printf("\n==================================");
    printf("\nName: %s", node->name);
    printf("\nEmployeeID: %d", node->employeeID);
    printf("\nPerformance Score: %d", node->performanceScore);
    printf("\n==================================");
}

void display(struct node *head, struct node *tail) {
    struct node *temp = head;

    if (head == NULL) {
        printf("\n List is Empty!");
        return;
    }

    do {
        printNode(temp);
        temp = temp->next;
    } while (temp != head);  
}

void insert(struct node **head, struct node **tail, struct node *newNode) {
    if (*head == NULL) {
        *head = newNode;
        *tail = newNode;
        newNode->next = newNode;  
    } else {
        newNode->next = (*head);
        (*tail)->next = newNode;
        *tail = newNode;  
    }
}

void insertAtPosition(int position, struct node *newNode) {
    if (llHead == NULL && position == 0) {
        llHead = llTail = newNode;
        newNode->next = newNode;
    } else {
        struct node *temp = llHead;
        int i = 0;

        if (position == 0) {  
            newNode->next = llHead;
            llHead = newNode;
            llTail->next = newNode;  
        } else {
            while (temp != llTail && i < position - 1) {
                temp = temp->next;
                i++;
            }

            if (i == position - 1) {
                newNode->next = temp->next;
                temp->next = newNode;

                if (temp == llTail) {  
                    llTail = newNode;
                }
            } else {
                printf("\nInvalid position!");
            }
        }
    }
}

void deleteAtPosition(int position) {
    if (llHead == NULL) return;  

    struct node *temp = llHead;

    if (position == 0) {
        if (llHead == llTail) {
            free(llHead);
            llHead = llTail = NULL;
        } else {
            llHead = llHead->next;
            llTail->next = llHead;  
            free(temp);
        }
    } else {
        int i = 0;
        struct node *prev = NULL;
        while (temp != llTail && i < position) {
            prev = temp;
            temp = temp->next;
            i++;
        }

        if (temp == llTail && i == position) {
            prev->next = llHead;
            llTail = prev;
            free(temp);
        } else if (i == position) {
            prev->next = temp->next;
            free(temp);
        } else {
            printf("\nInvalid position!");
        }
    }
}

void split() {

    struct node *lpHead = NULL;
    struct node *hpHead = NULL;

    struct node *lpTail = NULL;
    struct node *hpTail = NULL;

    struct node *temp = llHead;
    while (temp != llTail) {
        struct node *next = temp->next;
        if (temp->performanceScore < 40) {
            insert(&lpHead, &lpTail, temp);
        } else {
            insert(&hpHead, &hpTail, temp);
        }
        temp = next;
    }
        if (temp->performanceScore < 40) {
            insert(&lpHead, &lpTail, temp);
        } else {
            insert(&hpHead, &hpTail, temp);
        }

    printf("\n\nLP List\n");
    display(lpHead, lpTail);

    printf("\n\nHP List\n");
    display(hpHead, hpTail);

}

void printTopPerfomer() {
    struct node *temp = llHead;
    while (temp != llTail) {
        if (temp->performanceScore > 40) {
            printf("\nName: %s", temp->name);
            printf("\nEmployeeID: %d", temp->employeeID);
            printf("\nPerformance Score: %d", temp->performanceScore);
            break;
        }
        temp = temp->next;
    }
}

int main() {
    while (1) {
        printf("\n==================================");
        printf("\n1. Insert at End");
        printf("\n2. Insert at Position");
        printf("\n3. Insert at Beginning");
        printf("\n4. Delete at Position");
        printf("\n5. Display");
        printf("\n6. Split");
        printf("\n7. Print Top Performer");
        printf("\n==================================");
        int choice;
        printf("\nEnter Choice: ");
        scanf("%d", &choice);

        char name[100];  
        int performanceScore;
        int employeeID;
        int position;

        struct node *newNode = (struct node *) malloc(sizeof(struct node));

        if (choice == 1) {
            // insert a new element at end
            printf("Enter Name: ");
            scanf("%s", name);
            newNode->name = strdup(name);  

            printf("Enter Employee ID: ");
            scanf("%d", &employeeID);

            while (employeeID <= 0) {
                printf("Employee ID must be a postive interger!\nEnter Employee ID:");
                scanf("%d", &employeeID);
            }
            newNode->employeeID = employeeID;

            printf("Enter Employee Performance Score: ");
            scanf("%d", &performanceScore);

            while (performanceScore < 0 || performanceScore > 100) {
                printf("Employee Performance Score must be between 0 to 100!\nEnter Employee Performance Score:");
                scanf("%d", &performanceScore);
            }
            newNode->performanceScore = performanceScore;

            insert(&llHead, &llTail, newNode);
        } else if (choice == 2) {
            // insert a new element at position
            printf("Enter Name: ");
            scanf("%s", name);
            newNode->name = strdup(name);  

            printf("Enter Employee ID: ");
            scanf("%d", &employeeID);

            while (employeeID <= 0) {
                printf("Employee ID must be a postive interger!\nEnter Employee ID:");
                scanf("%d", &employeeID);
            }
            newNode->employeeID = employeeID;

            printf("Enter Employee Performance Score: ");
            scanf("%d", &performanceScore);

            while (performanceScore < 0 || performanceScore > 100) {
                printf("Employee Performance Score must be between 0 to 100!\nEnter Employee Performance Score:");
                scanf("%d", &performanceScore);
            }
            newNode->performanceScore = performanceScore;

            printf("Enter Position: ");
            scanf("%d", &position);
            insertAtPosition(position, newNode);
        } else if (choice == 3) {
            // insert a new element at the beginning
            printf("Enter Name: ");
            scanf("%s", name);
            newNode->name = strdup(name);  

            printf("Enter Employee ID: ");
            scanf("%d", &employeeID);

            while (employeeID <= 0) {
                printf("Employee ID must be a postive interger!\nEnter Employee ID:");
                scanf("%d", &employeeID);
            }
            newNode->employeeID = employeeID;

            printf("Enter Employee Performance Score: ");
            scanf("%d", &performanceScore);

            while (performanceScore < 0 || performanceScore > 100) {
                printf("Employee Performance Score must be between 0 to 100!\nEnter Employee Performance Score:");
                scanf("%d", &performanceScore);
            }
            newNode->performanceScore = performanceScore;

            insertAtPosition(0, newNode);
        } else if (choice == 4) {
            // delete at position
            printf("Enter Position: ");
            scanf("%d", &position);
            deleteAtPosition(position);
        } else if (choice == 5) {
            // display
            display(llHead, llTail);
        } else if (choice == 6) {
            // split
            split();
        } else  if (choice == 7) {
            // print top performer
            printTopPerfomer();
        } else {
            printf("\nInvalid Option!");
        }
    }

    return 0;
}



/*

OUTPUT:


==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 1
Enter Name: a
Enter Employee ID: 1
Enter Employee Performance Score: 10

==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 1
Enter Name: b
Enter Employee ID: 2
Enter Employee Performance Score: 30

==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 1
Enter Name: d
Enter Employee ID: 3
Enter Employee Performance Score: 50

==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 1
Enter Name: f
Enter Employee ID: 4
Enter Employee Performance Score: 90

==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 5

==================================
Name: a
EmployeeID: 1
Performance Score: 10
==================================
==================================
Name: b
EmployeeID: 2
Performance Score: 30
==================================
==================================
Name: d
EmployeeID: 3
Performance Score: 50
==================================
==================================
Name: f
EmployeeID: 4
Performance Score: 90
==================================
==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice: 6


LP List

==================================
Name: a
EmployeeID: 1
Performance Score: 10
==================================
==================================
Name: b
EmployeeID: 2
Performance Score: 30
==================================

HP List

==================================
Name: d
EmployeeID: 3
Performance Score: 50
==================================
==================================
Name: f
EmployeeID: 4
Performance Score: 90
==================================
==================================
1. Insert at End
2. Insert at Position
3. Insert at Beginning
4. Delete at Position
5. Display
6. Split
==================================
Enter Choice:


 */