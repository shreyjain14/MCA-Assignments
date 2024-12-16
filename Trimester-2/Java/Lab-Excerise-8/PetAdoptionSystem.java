import java.util.*;

class PetAdoptionSystem {

    static class Pet {
        String name;
        int age;
        String type; 
        boolean adopted; 
        int id; 

      
        Pet(int id, String name, int age, String type) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.type = type;
            this.adopted = false; 
        }

     
        void adopt() {
            this.adopted = true;
        }

        @Override
        public String toString() {
            return "Pet [ID=" + id + ", Name=" + name + ", Age=" + age + ", Type=" + type + ", Adopted=" + adopted + "]";
        }
    }

  
    private static Map<Integer, Pet> petRegistry = new HashMap<>();
    private static Set<String> petNames = new HashSet<>(); 
    private static int idCounter = 1; 

    private static String getValidStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Name should only contain alphabets. Please try again.");
            } else {
                return input;
            }
        }
    }

    private static int getValidIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
             
                if (input <= 0) {
                    System.out.println("Age must be a positive integer greater than zero. Please enter a valid age.");
                } else if (input > 99) {
                    System.out.println("Age cannot be greater than 99. Please enter a valid age.");
                } else {
                    return input;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); 
            }
        }
    }


    private static void addPet() {
        String name = getValidStringInput("Enter pet name: ");
        while (petNames.contains(name)) {
            System.out.println("Pet with this name already exists. Choose another name.");
            name = getValidStringInput("Enter pet name: ");
        }
        int age = getValidIntegerInput("Enter pet age: ");
        String type = getValidStringInput("Enter pet type: ");

    
        Pet newPet = new Pet(idCounter++, name, age, type);
        
   
        if (petNames.contains(newPet.name)) {
            System.out.println("Pet with the name " + newPet.name + " already exists in the system.");
            return;
        }

        petRegistry.put(newPet.id, newPet); 
        petNames.add(newPet.name);

        System.out.println("Pet added successfully: " + newPet);
    }

 
    private static void adoptPet() {
        if (petRegistry.isEmpty()) {
            System.out.println("No pets available for adoption.");
            return;
        }

        int petId = getValidIntegerInput("Enter pet ID to adopt: ");
        if (petRegistry.containsKey(petId)) {
            Pet pet = petRegistry.get(petId);
            if (pet.adopted) {
                System.out.println("This pet has already been adopted.");
            } else {
                pet.adopt();
                System.out.println("Pet adopted successfully: " + pet);
            }
        } else {
            System.out.println("Pet ID not found.");
        }
    }

    private static void displayPets() {
        if (petRegistry.isEmpty()) {
            System.out.println("No pets available to display.");
        } else {
            System.out.println("All registered pets:");
            for (Pet pet : petRegistry.values()) {
                System.out.println(pet);
            }
        }
    }

    
    private static void viewAdoptionStatus() {
        if (petRegistry.isEmpty()) {
            System.out.println("No pets available for adoption.");
            return;
        }

        int petId = getValidIntegerInput("Enter pet ID to view adoption status: ");
        if (petRegistry.containsKey(petId)) {
            Pet pet = petRegistry.get(petId);
            System.out.println("Pet adoption status: " + (pet.adopted ? "Adopted" : "Available"));
        } else {
            System.out.println("Pet ID not found.");
        }
    }

   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Pet Adoption System ---");
            System.out.println("1. Add Pet");
            System.out.println("2. Adopt Pet");
            System.out.println("3. View All Pets");
            System.out.println("4. View Adoption Status");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getValidIntegerInput("");
            
            switch (choice) {
                case 1:
                    addPet();
                    break;
                case 2:
                    adoptPet();
                    break;
                case 3:
                    displayPets();
                    break;
                case 4:
                    viewAdoptionStatus();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
