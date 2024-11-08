#include<stdio.h>
#include<stdlib.h>

struct node{
    int data;
    int priority;
    struct node *next;
};

struct node* newNode(int d, int p){
    struct node* temp = (struct node*)malloc(sizeof(struct node));
    temp->data = d;
    temp->priority = p;
    temp->next = NULL;
    return temp;
}

int peek(struct node** head){
    return (*head)->data;
}

void pop(struct node** head){
    struct node* temp = *head;
    (*head) = (*head)->next;
    free(temp);
}

void push(struct node** head, int d, int p){
    struct node* start = (*head);
    struct node* temp = newNode(d, p);
    if((*head)->priority > p){
        temp->next = *head;
        (*head) = temp;
    }
}

int isEmpty(struct node** head){
    return (*head) == NULL;
}

int main(){
    struct node* pq = newNode(4, 1);
    push(&pq, 5, 2);
    push(&pq, 6, 3);
    push(&pq, 7, 0);
    while(!isEmpty(&pq)){
        printf("%d ", peek(&pq));
        pop(&pq);
    }
    return 0;
}
