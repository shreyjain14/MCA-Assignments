#include<stdio.h>
#include<math.h>

float calculateCharges(float hours){
    hours = ceil(hours);
    float charges = 2;
    if(hours > 3){
        charges += (hours - 3) * 0.5;
    }
    if(charges > 10){
        charges = 10;
    }
    return charges;
}

int main(){
    float car1, car2, car3;
    printf("Enter the number of hours for car 1: ");
    scanf("%f", &car1);
    printf("Enter the number of hours for car 2: ");
    scanf("%f", &car2);
    printf("Enter the number of hours for car 3: ");
    scanf("%f", &car3);
    
    float c1 = calculateCharges(car1);
    float c2 = calculateCharges(car2);
    float c3 = calculateCharges(car3);

    printf("Car\tHours\tCharge\n");
    printf("1\t%.2f\t%.2f\n", car1, c1);
    printf("2\t%.2f\t%.2f\n", car2, c2);
    printf("3\t%.2f\t%.2f\n", car3, c3);
    printf("Total\t%.2f\t%.2f\n", car1 + car2 + car3, c1 + c2 + c3);
    return 0;
}