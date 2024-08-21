#include <stdio.h>
#include <string.h>

struct car {
    char make[20];
    char model[20];
    int year;
    float price;
};

void displayCar(struct car c) {
    printf("Make: %s\n", c.make);
    printf("Model: %s\n", c.model);
    printf("Year: %d\n", c.year);
    printf("Price: %.2f\n", c.price);
}

int main() {
    struct car c1;
    strcpy(c1.make, "Toyota");
    strcpy(c1.model, "Corolla");
    c1.year = 2019;
    c1.price = 25000.00;

    displayCar(c1);

    return 0;
}