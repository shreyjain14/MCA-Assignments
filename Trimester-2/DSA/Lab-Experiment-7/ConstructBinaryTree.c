#include <stdio.h>
#include <stdlib.h>
#include <time.h>

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
    FILE *file = fopen("order.txt", "r");
    if (file == NULL) {
        printf("Error opening file\n");
        return 1;
    }

    int inorder[1023], preorder[1023], postorder[1023], levelOrder[1023];
    int n = 0;

    while (fscanf(file, "%d", &inorder[n]) != EOF) {
        n++;
        if (fgetc(file) == '\n') break;
    }

    for (int i = 0; i < n; i++) {
        fscanf(file, "%d", &preorder[i]);
    }

    for (int i = 0; i < n; i++) {
        fscanf(file, "%d", &postorder[i]);
    }

    for (int i = 0; i < n; i++) {
        fscanf(file, "%d", &levelOrder[i]);
    }

    fclose(file);
    #include <time.h>

    clock_t start, end;
    double cpu_time_used;

    // Measure time for buildTreeFromInPre
    start = clock();
    int preIndex = 0;
    struct Node* root1 = buildTreeFromInPre(inorder, preorder, 0, n - 1, &preIndex);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC * 1000;
    printf("Time taken to build tree from Inorder and Preorder: %f ms\n", cpu_time_used);

    // Measure time for buildTreeFromInPost
    start = clock();
    int postIndex = n - 1;
    struct Node* root2 = buildTreeFromInPost(inorder, postorder, 0, n - 1, &postIndex);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC * 1000;
    printf("Time taken to build tree from Inorder and Postorder: %f ms\n", cpu_time_used);

    // Measure time for buildTreeFromPreLevel
    start = clock();
    struct Node* root3 = buildTreeFromPreLevel(preorder, levelOrder, n);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC * 1000;
    printf("Time taken to build tree from Preorder and Level Order: %f ms\n", cpu_time_used);

    return 0;
}