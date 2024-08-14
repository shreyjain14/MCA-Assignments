#include <stdio.h>
#include <string.h>

struct employee
{
    char name[50];
    int id;
    float salary;
};

int main()
{
    struct employee emp1;
    struct employee emp2;

    strcpy(emp1.name, "Shrey");
    emp1.id = 1;
    emp1.salary = 100000;

    strcpy(emp2.name, "Anjaney");
    emp2.id = 2;
    emp2.salary = 75000;

    printf("Employee 1\n");
    printf("Name: %s\n", emp1.name);
    printf("ID: %d\n", emp1.id);
    printf("Salary: %.2f\n", emp1.salary);

    printf("\nEmployee 2\n");
    printf("Name: %s\n", emp2.name);
    printf("ID: %d\n", emp2.id);
    printf("Salary: %.2f\n", emp2.salary);

    return 0;
}
