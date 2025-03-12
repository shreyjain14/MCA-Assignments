package main

import (
	"fmt"
	"encoding/json"
)

type Person struct {
	Name 	string	`json:"name"`
	Age	int	`json:"age"`
	IsAdult	bool	`json:"isAdult"`
}

func main() {

	p1 := Person{"shrey",21,true}

	p, err := json.Marshal(p1)

	if err != nil {

		fmt.Println(err)

	}

	fmt.Println(string(p))

	var p2 Person
	json.Unmarshal(p, &p2)
	fmt.Println(p2)


}
