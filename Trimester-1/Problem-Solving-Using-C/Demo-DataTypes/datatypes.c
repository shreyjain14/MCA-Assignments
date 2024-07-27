#include <stdio.h>

int main() {
    // Integer types
    int integer = 10;
    short shortInteger = 20;
    long longInteger = 30;
    unsigned int unsignedInteger = 40;
    unsigned short unsignedShortInteger = 50;
    unsigned long unsignedLongInteger = 60;

    // Floating-point types
    float floatingPoint = 3.14;
    double doublePrecision = 3.14159;
    long double longDoublePrecision = 3.1415926535;

    // Character types
    char character = 'A';

    // Boolean type
    _Bool boolean = 1;

    // Display the values
    printf("Integer: %d\n", integer);
    printf("Short Integer: %hd\n", shortInteger);
    printf("Long Integer: %ld\n", longInteger);
    printf("Unsigned Integer: %u\n", unsignedInteger);
    printf("Unsigned Short Integer: %hu\n", unsignedShortInteger);
    printf("Unsigned Long Integer: %lu\n", unsignedLongInteger);
    printf("Floating Point: %f\n", floatingPoint);
    printf("Double Precision: %lf\n", doublePrecision);
    printf("Long Double Precision: %Lf\n", longDoublePrecision);
    printf("Character: %c\n", character);
    printf("Boolean: %d\n", boolean);

    return 0;
}