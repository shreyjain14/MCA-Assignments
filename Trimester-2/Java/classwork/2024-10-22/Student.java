class Student {
    String name;
    int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
    }

    public void displayGrade() {
        System.out.println("Grade: " + grade);
    }

}