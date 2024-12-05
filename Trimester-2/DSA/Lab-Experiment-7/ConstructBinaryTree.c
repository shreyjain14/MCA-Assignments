#include <stdio.h>
#include <stdlib.h>

struct Node {
    int data;
    struct Node* left;
    struct Node* right;
};

struct Node* newNode(int data) {
    struct Node* node = (struct Node*)malloc(sizeof(struct Node));
    node->data = data;
    node->left = node->right = NULL;
    return node;
}

int search(int arr[], int start, int end, int value) {
    for (int i = start; i <= end; i++) {
        if (arr[i] == value)
            return i;
    }
    return -1;
}

struct Node* buildTreeFromInPre(int inorder[], int preorder[], int inStart, int inEnd, int* preIndex) {
    if (inStart > inEnd)
        return NULL;

    struct Node* node = newNode(preorder[*preIndex]);
    (*preIndex)++;

    if (inStart == inEnd)
        return node;

    int inIndex = search(inorder, inStart, inEnd, node->data);

    node->left = buildTreeFromInPre(inorder, preorder, inStart, inIndex - 1, preIndex);
    node->right = buildTreeFromInPre(inorder, preorder, inIndex + 1, inEnd, preIndex);

    return node;
}

struct Node* buildTreeFromInPost(int inorder[], int postorder[], int inStart, int inEnd, int* postIndex) {
    if (inStart > inEnd)
        return NULL;

    struct Node* node = newNode(postorder[*postIndex]);
    (*postIndex)--;

    if (inStart == inEnd)
        return node;

    int inIndex = search(inorder, inStart, inEnd, node->data);

    node->right = buildTreeFromInPost(inorder, postorder, inIndex + 1, inEnd, postIndex);
    node->left = buildTreeFromInPost(inorder, postorder, inStart, inIndex - 1, postIndex);

    return node;
}

struct Node* buildTreeFromPreLevel(int preorder[], int levelOrder[], int n) {
    if (n == 0)
        return NULL;

    struct Node* root = newNode(preorder[0]);

    if (n == 1)
        return root;

    int leftLevel[n - 1], rightLevel[n - 1];
    int leftIndex = 0, rightIndex = 0;

    int rootIndex = search(levelOrder, 0, n - 1, preorder[0]);

    for (int i = 1; i < n; i++) {
        if (search(levelOrder, 0, rootIndex, preorder[i]) != -1)
            leftLevel[leftIndex++] = preorder[i];
        else
            rightLevel[rightIndex++] = preorder[i];
    }

    root->left = buildTreeFromPreLevel(leftLevel, levelOrder, leftIndex);
    root->right = buildTreeFromPreLevel(rightLevel, levelOrder, rightIndex);

    return root;
}

void printInorder(struct Node* node) {
    if (node == NULL)
        return;

    printInorder(node->left);
    printf("%d ", node->data);
    printInorder(node->right);
}

int main() {
    int inorder[] = {4, 2, 5, 1, 6, 3};
    int preorder[] = {1, 2, 4, 5, 3, 6};
    int postorder[] = {4, 5, 2, 6, 3, 1};
    int levelOrder[] = {1, 2, 3, 4, 5, 6};
    int n = sizeof(inorder) / sizeof(inorder[0]);

    int preIndex = 0;
    struct Node* root1 = buildTreeFromInPre(inorder, preorder, 0, n - 1, &preIndex);
    printf("Inorder traversal of the constructed tree (from Inorder and Preorder):\n");
    printInorder(root1);
    printf("\n");

    int postIndex = n - 1;
    struct Node* root2 = buildTreeFromInPost(inorder, postorder, 0, n - 1, &postIndex);
    printf("Inorder traversal of the constructed tree (from Inorder and Postorder):\n");
    printInorder(root2);
    printf("\n");

    struct Node* root3 = buildTreeFromPreLevel(preorder, levelOrder, n);
    printf("Inorder traversal of the constructed tree (from Preorder and Level Order):\n");
    printInorder(root3);
    printf("\n");

    return 0;
}