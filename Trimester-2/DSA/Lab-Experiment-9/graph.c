#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 100

typedef struct Product {
    int id;
    char name[50];
    float price;
} Product;

typedef struct Graph {
    int vertices;
    int adjMatrix[MAX][MAX];
    Product products[MAX];
} Graph;

void initializeGraph(Graph *graph, int vertices) {
    graph->vertices = vertices;
    for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
            graph->adjMatrix[i][j] = 0;
        }
    }
}

void addEdge(Graph *graph, int src, int dest) {
    graph->adjMatrix[src][dest] = 1;
    graph->adjMatrix[dest][src] = 1;
}

void addProduct(Graph *graph, int vertex, int id, const char *name, float price) {
    graph->products[vertex].id = id;
    strcpy(graph->products[vertex].name, name);
    graph->products[vertex].price = price;
}

void displayPath(int path[], int pathLength) {
    printf("Path: ");
    for (int i = 0; i < pathLength; i++) {
        printf("%d ", path[i]);
    }
    printf("\n");
}

void BFT(Graph *graph, int start, int target) {
    int visited[MAX] = {0};
    int queue[MAX], front = 0, rear = 0;
    int path[MAX], pathLength = 0;

    visited[start] = 1;
    queue[rear++] = start;

    while (front < rear) {
        int current = queue[front++];
        path[pathLength++] = current;

        if (current == target) {
            printf("Product Found: %s\n", graph->products[current].name);
            displayPath(path, pathLength);
            return;
        }

        for (int i = 0; i < graph->vertices; i++) {
            if (graph->adjMatrix[current][i] && !visited[i]) {
                visited[i] = 1;
                queue[rear++] = i;
            }
        }
    }

    printf("Product not found in the graph.\n");
}

void DFTUtil(Graph *graph, int current, int target, int visited[], int path[], int *pathLength, int *found) {
    visited[current] = 1;
    path[(*pathLength)++] = current;

    if (current == target) {
        printf("Product Found: %s\n", graph->products[current].name);
        displayPath(path, *pathLength);
        *found = 1;
        return;
    }

    for (int i = 0; i < graph->vertices; i++) {
        if (graph->adjMatrix[current][i] && !visited[i]) {
            DFTUtil(graph, i, target, visited, path, pathLength, found);
            if (*found) return;
        }
    }

    (*pathLength)--;
}

void DFT(Graph *graph, int start, int target) {
    int visited[MAX] = {0};
    int path[MAX], pathLength = 0;
    int found = 0;

    DFTUtil(graph, start, target, visited, path, &pathLength, &found);

    if (!found) {
        printf("Product not found in the graph.\n");
    }
}

int main() {
    int vertices = 5;

    Graph graph;
    initializeGraph(&graph, vertices);

    addProduct(&graph, 0, 101, "Laptop", 75000.0);
    addProduct(&graph, 1, 102, "Phone", 50000.0);
    addProduct(&graph, 2, 103, "Tablet", 30000.0);
    addProduct(&graph, 3, 104, "Headphones", 2000.0);
    addProduct(&graph, 4, 105, "Smartwatch", 10000.0);

    addEdge(&graph, 0, 1);
    addEdge(&graph, 0, 2);
    addEdge(&graph, 1, 3);
    addEdge(&graph, 2, 4);
    addEdge(&graph, 3, 4);

    int start, target;
    printf("Enter the starting product index for traversal: ");
    scanf("%d", &start);
    printf("Enter the product index to search for: ");
    scanf("%d", &target);

    printf("\nBreadth-First Traversal:\n");
    BFT(&graph, start, target);

    printf("\nDepth-First Traversal:\n");
    DFT(&graph, start, target);

    return 0;
}
