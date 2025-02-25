package main

import "fmt"

type shape interface {
	area() float64
	perimeter() float64
}

type rectangle struct {
	width  float64
	height float64
}

func (r *rectangle) area() float64 {
	return r.width * r.height
}

func (r *rectangle) perimeter() float64 {
	return 2 * (r.width + r.height)
}

type circle struct {
	radius float64
}

func (c *circle) area() float64 {
	return 3.14 * c.radius * c.radius
}

func (c *circle) perimeter() float64 {
	return 2 * 3.14 * c.radius
}

type square struct {
	side float64
}

func (s *square) area() float64 {
	return s.side * s.side
}

func (s *square) perimeter() float64 {
	return 4 * s.side
}

func totalCalc(shapes []shape, method func(shape) float64) float64 {
	total := 0.0
	for _, s := range shapes {
		total += method(s)
	}
	return total
}

func main() {

	square := square{5}
	circle := circle{5}
	rectangle := rectangle{5, 10}

	shapes := []shape{&square, &circle, &rectangle}

	fmt.Println("Total Area: ", totalCalc(shapes, shape.area))
	fmt.Println("Total Perimeter: ", totalCalc(shapes, shape.perimeter))

}
