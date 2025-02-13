package main

import "fmt"

func main() {

	test := func(a int) int {
		return a + 1
	}

	fmt.Println(test(1))

}
