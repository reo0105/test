//61910902 須永一輝
#include <stdio.h>
#include <stdlib.h>

#define NNODE 6
#define INF 100

struct node {
    int min_cost[NNODE];
    char pre_node[NNODE];
    int selected[NNODE];
};

int cost[NNODE][NNODE] = {
        {  0,   2, 5,   1, INF, INF},
        {  2,   0, 3,   2, INF, INF},
        {  5,   3, 0,   3,   1,   5},
        {  1,   2, 3,   0,   1, INF},
        {INF, INF, 1,   1,   0,   2},
        {INF, INF, 5, INF,   2,   0}
};


void initialize(int i, struct node *n)
{
    int j;
    for (j = 0; j < NNODE; j++) {
        n->min_cost[j] = cost[i][j];
        n->pre_node[j] = i + 'A';
        if (j == i) {
            n->selected[j] = 1;
        } else {
            n->selected[j] = 0;
        }
    }
}


int find_min(struct node *n)
{
    int k, tmp_min = INF, tmp_index;

    for (k = 0; k < NNODE; k++) {
        if (n->selected[k] == 0 && n->min_cost[k] != 0 && n->min_cost[k] < tmp_min) {
            tmp_min = n -> min_cost[k];
            tmp_index = k;
        }
    }
    n->selected[tmp_index] = 1;
    return tmp_index;
}


int main()
{
    int i, j, count = 0, min_index = 0, tmp_cost;
    struct node *n;
    char node[NNODE] = {'A', 'B', 'C', 'D', 'E', 'F'};

    if ((n = (struct node *)malloc(sizeof(struct node))) == NULL) {
        fprintf(stderr, "Cannot allocate memory.\n");
        exit(1);
    } else {
        for (i = 0; i < NNODE; i++) {
        initialize(i, n);
        for (count = 0; count < NNODE; count++) {
            min_index = find_min(n);
            for (j = 0; j < NNODE; j++) {
                if (cost[min_index][j] != INF) {
                    tmp_cost = n -> min_cost[min_index] + cost[min_index][j];
                    if (n->min_cost[j] > tmp_cost) {
                        n->min_cost[j] = tmp_cost;
                        n->pre_node[j] = node[min_index];
                    }
                }
            }
        }
        printf("root node %c:\n    ", node[i]);
        for (count = 0; count < NNODE; count++) {
            printf(" [%c, %c, %d]", node[count], n->pre_node[count], n->min_cost[count]);
        }
        printf("\n");
        } 
    }
    return 0;
}