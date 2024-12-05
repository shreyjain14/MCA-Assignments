#include<stdio.h>

struct product {
    int id;
    char name[20];
    float price;
    struct product *left;
    struct product *right;
};

struct product *head = NULL;

void prefix(struct product *p) {
    if (p != NULL) {
        printf("%d %s %f\n", p->id, p->name, p->price);
        prefix(p->left);
        prefix(p->right);
    }
}

void infix(struct product *p) {
    if (p != NULL) {
        infix(p->left);
        printf("%d %s %f\n", p->id, p->name, p->price);
        infix(p->right);
    }
}

void postfix(struct product *p) {
    if (p != NULL) {
        postfix(p->left);
        postfix(p->right);
        printf("%d %s %f\n", p->id, p->name, p->price);
    }
}

void level_order(struct product *p) {
    struct product *queue[100];
    int front = 0, rear = 0;
    queue[rear++] = p;
    while (front < rear) {
        struct product *temp = queue[front++];
        printf("%d %s %f\n", temp->id, temp->name, temp->price);
        if (temp->left != NULL) {
            queue[rear++] = temp->left;
        }
        if (temp->right != NULL) {
            queue[rear++] = temp->right;
        }
    }
}

void add_node() {
    struct product *new_node = (struct product *)malloc(sizeof(struct product));
    printf("Enter Product ID: ");
    scanf("%d", &new_node->id);
    printf("Enter Product Name: ");
    scanf("%s", new_node->name);
    printf("Enter Product Price: ");
    scanf("%f", &new_node->price);
    new_node->left = NULL;
    new_node->right = NULL;
    if (head == NULL) {
        head = new_node;
    } else {
        struct product *temp = head;
        while (1) {
            if (new_node->id < temp->id) {
                if (temp->left == NULL) {
                    temp->left = new_node;
                    break;
                } else {
                    temp = temp->left;
                }
            } else {
                if (temp->right == NULL) {
                    temp->right = new_node;
                    break;
                } else {
                    temp = temp->right;
                }
            }
        }
    }
}

void delete_root() {
    if (head == NULL) {
        printf("The tree is empty.\n");
        return;
    }

    struct product *temp = head;
    struct product *parent = NULL;

    // Find the rightmost node in the left subtree or the leftmost node in the right subtree
    if (head->left != NULL) {
        temp = head->left;
        parent = head;
        while (temp->right != NULL) {
            parent = temp;
            temp = temp->right;
        }
        head->id = temp->id;
        strcpy(head->name, temp->name);
        head->price = temp->price;
        if (parent->right == temp) {
            parent->right = temp->left;
        } else {
            parent->left = temp->left;
        }
    } else if (head->right != NULL) {
        temp = head->right;
        parent = head;
        while (temp->left != NULL) {
            parent = temp;
            temp = temp->left;
        }
        head->id = temp->id;
        strcpy(head->name, temp->name);
        head->price = temp->price;
        if (parent->left == temp) {
            parent->left = temp->right;
        } else {
            parent->right = temp->right;
        }
    } else {
        free(head);
        head = NULL;
        printf("The tree is now empty.\n");
        return;
    }

    free(temp);
}

int main() {
    int choice;
    while (1) {
        printf("1. Add Node\n2. Prefix\n3. Infix\n4. Postfix\n5. level order\n6. Exit\nEnter your choice: ");
        scanf("%d", &choice);
        switch (choice) {
            case 1:
                add_node();
                break;
            case 2:
                prefix(head);
                break;
            case 3:
                infix(head);
                break;
            case 4:
                postfix(head);
                break;
            case 5:
                level_order(head);
                break;
            case 6:
                exit(0);
            default:
                printf("Invalid Choice\n");
        }
    }
    return 0;
}