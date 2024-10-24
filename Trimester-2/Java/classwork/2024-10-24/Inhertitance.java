class Inheritance {
    public static void main(String[] args) {

        Dog dog = new Dog();
        dog.name = "Tommy";
        dog.age = 5;
        dog.color = "Black";
        dog.breed = "Labrador";

        dog.eat();
        dog.sleep();
        dog.bark();

        Cat cat = new Cat();
        cat.name = "Kitty";
        cat.age = 3;
        cat.color = "White";
        cat.breed = "Persian";

        cat.eat();
        cat.sleep();
        cat.meow();

    }
}

class Animal {
    String name;
    int age;
    String color;

    public void eat() {
        System.out.println("Eating...");
    }

    public void sleep() {
        System.out.println("Sleeping...");
    }
}

class Dog extends Animal {
    String breed;

    public void bark() {
        System.out.println("Barking...");
    }
}

class Cat extends Animal {
    String breed;

    public void meow() {
        System.out.println("Meowing...");
    }
}

