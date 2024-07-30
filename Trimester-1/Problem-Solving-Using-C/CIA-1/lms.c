#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure to represent a car
typedef struct {
    int id;
    char make[50];
    char model[50];
    int year;
    float price;
} Car;

// Function to create a new car
Car createCar(int id, char make[], char model[], int year, float price) {
    Car car;
    car.id = id;
    strcpy(car.make, make);
    strcpy(car.model, model);
    car.year = year;
    car.price = price;
    return car;
}

// Function to display car details
void displayCar(Car car) {
    printf("ID: %d\n", car.id);
    printf("Make: %s\n", car.make);
    printf("Model: %s\n", car.model);
    printf("Year: %d\n", car.year);
    printf("Price: %.2f\n", car.price);
    printf("\n");
}

int main() {
    Car cars[100];  // Array to store cars
    int numCars = 0;  // Number of cars in the array

    int choice;
    do {
        printf("Car Dealership Management System\n");
        printf("1. Add Car\n");
        printf("2. Remove Car\n");
        printf("3. View Cars\n");
        printf("4. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1: {
                int id;
                char make[50];
                char model[50];
                int year;
                float price;

                printf("Enter car details:\n");
                printf("Make: ");
                scanf("%s", make);
                printf("Model: ");
                scanf("%s", model);
                printf("Year: ");
                scanf("%d", &year);
                printf("Price: ");
                scanf("%f", &price);

                // Generate a unique ID for the car
                id = numCars + 1;

                // Create the car object
                Car car = createCar(id, make, model, year, price);

                // Add the car to the array
                cars[numCars] = car;
                numCars++;

                printf("Car created successfully!\n");
                break;
            }
            case 2: {
                int id;
                printf("Enter the ID of the car to remove: ");
                scanf("%d", &id);

                int found = 0;
                for (int i = 0; i < numCars; i++) {
                    if (cars[i].id == id) {
                        // Shift the remaining cars to fill the gap
                        for (int j = i; j < numCars - 1; j++) {
                            cars[j] = cars[j + 1];
                        }
                        numCars--;
                        found = 1;
                        printf("Car removed successfully!\n");
                        break;
                    }
                }

                if (!found) {
                    printf("Car not found!\n");
                }
                break;
            }
            case 3: {
                if (numCars == 0) {
                    printf("No cars available!\n");
                } else {
                    printf("Car Details:\n");
                    for (int i = 0; i < numCars; i++) {
                        displayCar(cars[i]);
                    }
                }
                break;
            }
            case 4: {
                printf("Exiting...\n");
                exit(0);
            }
            default:
                printf("Invalid choice! Please try again.\n");
        }
    } while (1);

    return 0;
}