class PStudent {

    private String name;
    private int grade;

    public PStudent(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    private void displayDetails() {
        System.out.println("Name: " + name);
    }

    private void displayGrade() {
        System.out.println("Grade: " + grade);
    }

    public void display() {
        displayDetails();
        displayGrade();
    }

}