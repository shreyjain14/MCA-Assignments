package main

import (
	"errors"
	"fmt"
	"time"
)

type Event struct {
	Name      string
	Date      string
	StartTime string
	Duration  int
}

type EventManager struct {
	Events []Event
}

func validateDate(date string) bool {
	_, err := time.Parse("2006-01-02", date)
	return err == nil
}

func validateTime(timeStr string) bool {
	_, err := time.Parse("15:04", timeStr)
	return err == nil
}

func validateDuration(duration int) bool {
	return duration > 0
}

func (em *EventManager) addEvent(name, date, startTime string, duration int) error {
	if !validateDate(date) {
		return errors.New("invalid date format. Please use YYYY-MM-DD")
	}
	if !validateTime(startTime) {
		return errors.New("invalid time format. Please use HH:MM")
	}
	if !validateDuration(duration) {
		return errors.New("duration must be a positive integer")
	}

	for _, event := range em.Events {
		if event.Name == name {
			return errors.New("event with this name already exists")
		}
	}

	newEvent := Event{
		Name:      name,
		Date:      date,
		StartTime: startTime,
		Duration:  duration,
	}

	em.Events = append(em.Events, newEvent)
	em.sortEvents()
	return nil
}

func (em *EventManager) sortEvents() {
	for i := 0; i < len(em.Events)-1; i++ {
		for j := i + 1; j < len(em.Events); j++ {
			event1 := em.Events[i]
			event2 := em.Events[j]
			if event1.Date > event2.Date || (event1.Date == event2.Date && event1.StartTime > event2.StartTime) {
				em.Events[i], em.Events[j] = em.Events[j], em.Events[i]
			}
		}
	}
}

func (em *EventManager) modifyEvent(name, newDate, newStartTime string, newDuration int) error {
	for i, event := range em.Events {
		if event.Name == name {
			if !validateDate(newDate) || !validateTime(newStartTime) || !validateDuration(newDuration) {
				return errors.New("invalid input values")
			}
			em.Events[i].Date = newDate
			em.Events[i].StartTime = newStartTime
			em.Events[i].Duration = newDuration
			em.sortEvents()
			return nil
		}
	}
	return errors.New("event not found")
}

func (em *EventManager) deleteEvent(name string) error {
	for i, event := range em.Events {
		if event.Name == name {
			em.Events = append(em.Events[:i], em.Events[i+1:]...)
			return nil
		}
	}
	return errors.New("event not found")
}

func (em *EventManager) getEventsByDate(date string) []Event {
	var result []Event
	for _, event := range em.Events {
		if event.Date == date {
			result = append(result, event)
		}
	}
	return result
}

func (em *EventManager) listUpcomingEvents() []Event {
	return em.Events
}

func (em *EventManager) findLongestEvent() Event {
	var longestEvent Event
	for _, event := range em.Events {
		if event.Duration > longestEvent.Duration {
			longestEvent = event
		}
	}
	return longestEvent
}

func (em *EventManager) countEventsOnDate(date string) int {
	count := 0
	for _, event := range em.Events {
		if event.Date == date {
			count++
		}
	}
	return count
}

func (em *EventManager) averageDuration() float64 {
	if len(em.Events) == 0 {
		return 0
	}
	var totalDuration int
	for _, event := range em.Events {
		totalDuration += event.Duration
	}
	return float64(totalDuration) / float64(len(em.Events))
}

func displayMenu() {
	fmt.Println("\nEvent Management System Menu:")
	fmt.Println("1. Add Event")
	fmt.Println("2. Modify Event")
	fmt.Println("3. Delete Event")
	fmt.Println("4. List All Upcoming Events")
	fmt.Println("5. Get Events by Date")
	fmt.Println("6. Find Longest Event")
	fmt.Println("7. Count Events on a Specific Date")
	fmt.Println("8. Calculate Average Duration of Events")
	fmt.Println("9. Exit")
}

func main() {
	manager := EventManager{}
	var choice int

	for {
		displayMenu()
		fmt.Print("Enter your choice: ")
		_, err := fmt.Scan(&choice)
		if err != nil {
			fmt.Println("Invalid input. Please try again.")
			continue
		}

		switch choice {
		case 1:
			var name, date, startTime string
			var duration int
			fmt.Print("Enter Event Name: ")
			fmt.Scan(&name)
			fmt.Print("Enter Event Date (YYYY-MM-DD): ")
			fmt.Scan(&date)
			fmt.Print("Enter Start Time (HH:MM): ")
			fmt.Scan(&startTime)
			fmt.Print("Enter Duration (in hours): ")
			fmt.Scan(&duration)

			err := manager.addEvent(name, date, startTime, duration)
			if err != nil {
				fmt.Println("Error:", err)
			} else {
				fmt.Println("Event added successfully!")
			}

		case 2:
			var name, newDate, newStartTime string
			var newDuration int
			fmt.Print("Enter Event Name to Modify: ")
			fmt.Scan(&name)
			fmt.Print("Enter New Date (YYYY-MM-DD): ")
			fmt.Scan(&newDate)
			fmt.Print("Enter New Start Time (HH:MM): ")
			fmt.Scan(&newStartTime)
			fmt.Print("Enter New Duration (in hours): ")
			fmt.Scan(&newDuration)

			err := manager.modifyEvent(name, newDate, newStartTime, newDuration)
			if err != nil {
				fmt.Println("Error:", err)
			} else {
				fmt.Println("Event modified successfully!")
			}

		case 3:
			var name string
			fmt.Print("Enter Event Name to Delete: ")
			fmt.Scan(&name)

			err := manager.deleteEvent(name)
			if err != nil {
				fmt.Println("Error:", err)
			} else {
				fmt.Println("Event deleted successfully!")
			}

		case 4:
			fmt.Println("All Upcoming Events:")
			for _, event := range manager.listUpcomingEvents() {
				fmt.Printf("%s - %s %s, Duration: %d hours\n", event.Name, event.Date, event.StartTime, event.Duration)
			}

		case 5:
			var date string
			fmt.Print("Enter Date (YYYY-MM-DD): ")
			fmt.Scan(&date)
			eventsOnDate := manager.getEventsByDate(date)
			if len(eventsOnDate) == 0 {
				fmt.Println("No events found on this date.")
			} else {
				for _, event := range eventsOnDate {
					fmt.Printf("%s - %s %s, Duration: %d hours\n", event.Name, event.Date, event.StartTime, event.Duration)
				}
			}

		case 6:
			longestEvent := manager.findLongestEvent()
			if (longestEvent == Event{}) {
				fmt.Println("No events found.")
			} else {
				fmt.Printf("Longest Event: %s, Duration: %d hours\n", longestEvent.Name, longestEvent.Duration)
			}

		case 7:
			var date string
			fmt.Print("Enter Date (YYYY-MM-DD): ")
			fmt.Scan(&date)
			count := manager.countEventsOnDate(date)
			fmt.Printf("Number of events on %s: %d\n", date, count)

		case 8:
			avgDuration := manager.averageDuration()
			fmt.Printf("Average Duration of All Events: %.2f hours\n", avgDuration)

		case 9:
			fmt.Println("Exiting the program.")
			return

		default:
			fmt.Println("Invalid choice. Please try again.")
		}
	}
}
