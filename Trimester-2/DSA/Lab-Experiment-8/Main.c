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

struct product* search(int id) {
    struct product *temp = head;
    while (temp != NULL) {
        if (temp->id == id) {
            return temp;
        } else if (id < temp->id) {
            temp = temp->left;
        } else {
            temp = temp->right;
        }
    }
    return NULL;
}

int main() {
    int choice;
    while (1) {
        printf("1. Add Node\n2. Prefix\n3. Infix\n4. Postfix\n5. Search\n6. Exit\nEnter your choice: ");
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
                printf("Enter Product ID to search: ");
                int id;
                scanf("%d", &id);
                struct product *result = search(id);
                if (result != NULL) {
                    printf("Product Found: %d %s %f\n", result->id, result->name, result->price);
                } else {
                    printf("Product Not Found\n");
                }
                break;
            case 6:
                exit(0);
            default:
                printf("Invalid Choice\n");
        }
    }
    return 0;
}