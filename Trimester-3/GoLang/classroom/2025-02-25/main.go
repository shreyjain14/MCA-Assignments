package main

import "fmt"

type super interface {
	utility() int
	getName() string
}

type Superhero struct {
	name        string
	peopleSaved int
	powers      []string
	powerScale  int
}

func (s *Superhero) getName() string {
	return s.name
}

func (s *Superhero) utility() int {
	return s.powerScale * s.peopleSaved
}

type SuperVillain struct {
	name         string
	poepleHarmed int
	powers       []string
	powerScale   int
}

func (s *SuperVillain) utility() int {
	return s.powerScale * s.poepleHarmed
}

func (s *SuperVillain) getName() string {
	return s.name
}

func cumulativeUtility(s []super) int {
	total := 0
	for _, v := range s {
		total += v.utility()
	}
	return total
}

func main() {
	superman := Superhero{
		name:        "Super Man",
		peopleSaved: 1000,
		powers:      []string{"fly", "laser", "speed"},
		powerScale:  10,
	}

	lexLuthor := SuperVillain{
		name:         "Lex Luthor",
		poepleHarmed: 1000,
		powers:       []string{"intelligence", "money"},
		powerScale:   1,
	}

	spiderman := Superhero{
		name:        "SpiderMan",
		peopleSaved: 100,
		powers:      []string{"web", "spider sense", "agility"},
		powerScale:  5,
	}

	venom := SuperVillain{
		name:         "Venom",
		poepleHarmed: 1000,
		powers:       []string{"symbiote", "strength", "agility"},
		powerScale:   10,
	}

	hitSquad := []super{&superman, &lexLuthor, &spiderman, &venom}

	for _, v := range hitSquad {
		fmt.Println(v.getName(), v.utility())
	}

	fmt.Println("Total utility: ", cumulativeUtility(hitSquad))

}
