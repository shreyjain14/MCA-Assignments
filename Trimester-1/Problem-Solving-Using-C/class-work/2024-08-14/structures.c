#include <stdio.h>
#include <string.h>

struct member_str {
    int member_id;
    char member_name[50];
};

struct parent_str {
    int parent_id;
    char parent_name[50];
    struct member_str member;
};

int main()
{
    struct parent_str parent;

    parent.parent_id = 1;
    strcpy(parent.parent_name, "Shrey");

    parent.member.member_id = 1;
    strcpy(parent.member.member_name, "Anjaney");

    printf("Parent\n");
    printf("ID: %d\n", parent.parent_id);
    printf("Name: %s\n", parent.parent_name);

    printf("\nMember\n");
    printf("ID: %d\n", parent.member.member_id);
    printf("Name: %s\n", parent.member.member_name);

    return 0;
}
