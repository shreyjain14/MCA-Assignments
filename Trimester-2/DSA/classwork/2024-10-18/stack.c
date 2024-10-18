#include <stdio.h>
#include <stdlib.h>

#define STACK_SIZE 10

int stack[STACK_SIZE] = {};
int top = -1;

int stackOverflow() {
    return top == STACK_SIZE - 1;
}

int stackUnderflow() {
    return top == -1;
}

void push(int value) {
    if (stackOverflow()) {
        printf("Stack overflow\n");
        return;
    }
    stack[++top] = value;
}

int pop() {
    if (stackUnderflow()) {
        printf("Stack underflow\n");
        return -1;
    }
    return stack[top--];
}

int display() {
    if (stackUnderflow()) {
        printf("Stack underflow\n");
        return -1;
    }
    for (int i = 0; i <= top; i++) {
        printf("%d ", stack[i]);
    }
    printf("\n");
}

int main() {

    while (1) {

        printf("1. Push\n");
        printf("2. Pop\n");
        printf("3. Display\n");
        printf("4. Exit\n");

        int choice;
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                printf("Enter the value to push: ");
                int value;
                scanf("%d", &value);
                push(value);
                break;
            case 2:
                printf("Popped value: %d\n", pop());
                break;
            case 3:
                display();
                break;
            case 4:
                exit(0);
            default:
                printf("Invalid choice\n");
        }

    }

}
