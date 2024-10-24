#include <stdio.h>
#include <stdlib.h>

#define MAX 100

char stack[MAX];
int top = -1;

void push(int data) {
    if (top == MAX - 1) {
        printf("Stack is full\n");
        return;
    }
    stack[++top] = data;
}

char pop() {
    if (top == -1) {
        printf("Stack is empty\n");
        return -1;
    }
    return stack[top--];
}

void display() {
    for (int i = top; i >= 0; i--) {
        printf("%d ", stack[i]);
    }
    printf("\n");
}

void infixToPostFix(char* infix) {
    char* postfix = (char*)malloc(sizeof(char) * MAX);
    int i = 0, j = 0;
    while (infix[i] != '\0') {
        if (infix[i] == '(') {
            push(infix[i]);
        } else if (infix[i] == ')') {
            while (top != -1 && stack[top] != '(') {
                postfix[j++] = pop();
            }
            pop();
        } else if (infix[i] == '+' || infix[i] == '-') {
            while (top != -1 && stack[top] != '(') {
                postfix[j++] = pop();
            }
            push(infix[i]);
        } else if (infix[i] == '*' || infix[i] == '/') {
            while (top != -1 && (stack[top] == '*' || stack[top] == '/')) {
                postfix[j++] = pop();
            }
            push(infix[i]);
        } else {
            postfix[j++] = infix[i];
        }
        i++;
    }
    while (top != -1) {
        postfix[j++] = pop();
    }
    postfix[j] = '\0';
    printf("Postfix: %s\n", postfix);
}

int main() {
    char infix[MAX];
    printf("Enter infix expression: ");
    scanf("%s", infix);
    infixToPostFix(infix);
    return 0;
}