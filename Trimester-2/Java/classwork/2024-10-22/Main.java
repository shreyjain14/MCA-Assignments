class Main {
    public static void main(String[] args) {

        Student shrey = new Student("Shrey", 10);

        System.out.println(shrey.name);
        System.out.println(shrey.grade);

        shrey.displayDetails();
        shrey.displayGrade();

        PStudent shreyP = new PStudent("Shrey", 10);

        // System.out.println(shreyP.name); ERROR
        // System.out.println(shreyP.grade); ERROR

        // shreyP.displayDetails(); ERROR
        // shreyP.displayGrade(); ERROR
        shreyP.display();

    }
}