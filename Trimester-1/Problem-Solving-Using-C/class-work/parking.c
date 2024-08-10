#include<stdio.h>

int calculateCharges(int hours){
    int charges = 2;
    if(hours > 3){
        charges += (hours - 3) * 0.5;
    }
    if(charges > 10){
        charges = 10;
    }
    return charges;
}

int main(){
    int car1, car2, car3;
    printf("Enter the number of hours for car 1: ");
    scanf("%d", &car1);
    printf("Enter the number of hours for car 2: ");
    scanf("%d", &car2);
    printf("Enter the number of hours for car 3: ");
    scanf("%d", &car3);

    printf("Car\tHours\tCharge\n");
    printf("1\t%d\t%d\n", car1, calculateCharges(car1));
    printf("2\t%d\t%d\n", car2, calculateCharges(car2));
    printf("3\t%d\t%d\n", car3, calculateCharges(car3));
    printf("Total\t%d\t%d\n", car1 + car2 + car3, calculateCharges(car1) + calculateCharges(car2) + calculateCharges(car3));
    return 0;
}