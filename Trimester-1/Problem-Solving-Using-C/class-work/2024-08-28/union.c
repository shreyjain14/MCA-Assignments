#include <stdio.h>

union test {
    int x;
    char y;
};

int main() {
    union test t;
    t.x = 65;
    printf("t.x = %d\n", t.x);
    printf("t.y = %c\n", t.y);
    t.y = 'A';
    printf("t.x = %d\n", t.x);
    printf("t.y = %c\n", t.y);
    return 0;
}