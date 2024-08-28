#include<stdio.h>

enum week {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
};

int main() {
    enum week day;
    day = Wednesday;
    printf("Day = %d\n", day);
    return 0;
}