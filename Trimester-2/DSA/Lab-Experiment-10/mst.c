#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define V 5  // Number of products (nodes)

// Example adjacency matrix (0 means no edge)
int graph[V][V] = {
    {0, 2, 0, 6, 0},
    {2, 0, 3, 8, 5},
    {0, 3, 0, 0, 7},
    {6, 8, 0, 0, 9},
    {0, 5, 7, 9, 0}
};

// PRIM'S MST
int minKey(int key[], int mstSet[]) {
    int min = INT_MAX, min_index = -1;
    for(int v = 0; v < V; v++){
        if(mstSet[v] == 0 && key[v] < min){
            min = key[v]; 
            min_index = v;
        }
    }
    return min_index;
}

void primMST() {
    int parent[V], key[V], mstSet[V];
    for(int i = 0; i < V; i++){
        key[i] = INT_MAX; 
        mstSet[i] = 0;
    }
    key[0] = 0;  
    parent[0] = -1;

    for(int count = 0; count < V - 1; count++){
        int u = minKey(key, mstSet);
        mstSet[u] = 1;
        for(int v = 0; v < V; v++){
            if(graph[u][v] && mstSet[v] == 0 && graph[u][v] < key[v]){
                parent[v] = u; 
                key[v] = graph[u][v];
            }
        }
    }

    printf("Prim's MST:\n");
    for(int i = 1; i < V; i++){
        printf("%d - %d  (%d)\n", parent[i], i, graph[i][parent[i]]);
    }
}

// KRUSKAL'S MST
typedef struct {
    int src, dest, weight;
} Edge;

typedef struct {
    int parent;
    int rank;
} Subset;

int find(Subset subsets[], int i) {
    if(subsets[i].parent != i)
        subsets[i].parent = find(subsets, subsets[i].parent);
    return subsets[i].parent;
}

void Union(Subset subsets[], int x, int y) {
    int rx = find(subsets, x);
    int ry = find(subsets, y);
    if(subsets[rx].rank < subsets[ry].rank)
        subsets[rx].parent = ry;
    else if(subsets[rx].rank > subsets[ry].rank)
        subsets[ry].parent = rx;
    else {
        subsets[ry].parent = rx;
        subsets[rx].rank++;
    }
}

int compareEdges(const void *a, const void *b) {
    Edge *e1 = (Edge *)a, *e2 = (Edge *)b;
    return e1->weight - e2->weight;
}

void kruskalMST() {
    // Build edge list
    Edge edges[V*V];
    int eCount = 0;
    for(int i=0; i<V; i++){
        for(int j=i+1; j<V; j++){
            if(graph[i][j]) {
                edges[eCount].src = i;
                edges[eCount].dest = j;
                edges[eCount].weight = graph[i][j];
                eCount++;
            }
        }
    }

    // Sort edges
    qsort(edges, eCount, sizeof(Edge), compareEdges);

    // Create subsets
    Subset *subsets = (Subset*) malloc(V*sizeof(Subset));
    for(int v=0; v<V; v++){
        subsets[v].parent = v;
        subsets[v].rank = 0;
    }

    printf("Kruskal's MST:\n");
    int edgesUsed = 0, idx = 0;
    while(edgesUsed < V-1 && idx < eCount){
        Edge next = edges[idx++];
        int x = find(subsets, next.src);
        int y = find(subsets, next.dest);
        if(x != y){
            printf("%d - %d  (%d)\n", next.src, next.dest, next.weight);
            Union(subsets, x, y);
            edgesUsed++;
        }
    }
    free(subsets);
}

int main() {
    primMST();
    kruskalMST();
    return 0;
}