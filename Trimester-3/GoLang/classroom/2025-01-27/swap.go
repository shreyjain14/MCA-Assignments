package main

import "fmt"

func main() {
	// a, b := 10, 20

	// fmt.Println(a, b)
	// fmt.Println(swap(a, b))
	// fmt.Println(a, b)
	// swap2(&a, &b)

	arr := [5]int{1, 2, 3, 4, 5}
	slc := []int{1, 2, 3, 4, 5}

	fmt.Println(arr, slc)

	slc = append(slc, 6, 7, 8, 9, 10)

	fmt.Println(slc)

	slc = append(slc, arr[1:4]...)

	fmt.Println(slc)

}

func swap(a, b int) (int, int) {
	return b, a
}

func swap2(a, b *int) {
	*a = *a + *b
	*b = *a - *b
	*a = *a - *b
}
