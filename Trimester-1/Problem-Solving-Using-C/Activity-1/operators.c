#include <stdio.h>

int main() {
    int a = 10;
    int b = 5;
    int result;

    // Arithmetic operators
    result = a + b;
    printf("Addition: %d\n", result);

    result = a - b;
    printf("Subtraction: %d\n", result);

    result = a * b;
    printf("Multiplication: %d\n", result);

    result = a / b;
    printf("Division: %d\n", result);

    result = a % b;
    printf("Modulus: %d\n", result);

    // Relational operators
    printf("Greater than: %d\n", a > b);
    printf("Less than: %d\n", a < b);
    printf("Greater than or equal to: %d\n", a >= b);
    printf("Less than or equal to: %d\n", a <= b);
    printf("Equal to: %d\n", a == b);
    printf("Not equal to: %d\n", a != b);

    // Logical operators
    int x = 1;
    int y = 0;
    printf("Logical AND: %d\n", x && y);
    printf("Logical OR: %d\n", x || y);
    printf("Logical NOT: %d\n", !x);

    // Assignment operators
    result += 5;
    printf("Add and assign: %d\n", result);

    result -= 3;
    printf("Subtract and assign: %d\n", result);

    result *= 2;
    printf("Multiply and assign: %d\n", result);

    result /= 4;
    printf("Divide and assign: %d\n", result);

    result %= 2;
    printf("Modulus and assign: %d\n", result);

    // Increment and decrement operators
    int i = 5;
    printf("Post-increment: %d\n", i++);
    printf("Pre-increment: %d\n", ++i);
    printf("Post-decrement: %d\n", i--);
    printf("Pre-decrement: %d\n", --i);

    // Bitwise operators
    int num1 = 10; // Binary: 0000 1010
    int num2 = 5;  // Binary: 0000 0101

    int bitwiseAnd = num1 & num2; // Binary AND: 0000 0000
    printf("Bitwise AND: %d\n", bitwiseAnd);

    int bitwiseOr = num1 | num2; // Binary OR: 0000 1111
    printf("Bitwise OR: %d\n", bitwiseOr);

    int bitwiseXor = num1 ^ num2; // Binary XOR: 0000 1111
    printf("Bitwise XOR: %d\n", bitwiseXor);

    int bitwiseNot = ~num1; // Binary NOT: 1111 0101
    printf("Bitwise NOT: %d\n", bitwiseNot);

    int leftShift = num1 << 2; // Binary left shift: 0010 1000
    printf("Left shift: %d\n", leftShift);

    int rightShift = num1 >> 2; // Binary right shift: 0000 0010
    printf("Right shift: %d\n", rightShift);

    return 0;
}