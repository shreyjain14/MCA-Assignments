#include <stdio.h>
#include <time.h>

void time_execution(void (*func)()) {
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    func();
    end = clock();

    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC * 1000; 
    printf("Function execution time: %f ms\n", cpu_time_used);
}

void sample_function() {
    for (int i = 0; i < 1000000; i++);
}

int main() {
    time_execution(sample_function);
    return 0;
}