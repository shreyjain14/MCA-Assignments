package main

import "fmt"

// func fog(f func(int) int, g func(int) int) func(int) int {
// 	return func(x int) int {
// 		return f(g(x))
// 	}
// }

func main() {

	f := func(x int) int {
		return x * x
	}

	// g := func(x int) int {
	// 	return x + 1
	// }

	// fmt.Println(fog(f, g)(3))
	// fmt.Println(fog(g, f)(3))

	apply := func(f func(int) int, x int) int {
		return f(x)
	}

	fmt.Println(apply(f, 10))

}
