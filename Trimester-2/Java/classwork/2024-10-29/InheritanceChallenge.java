class InheritanceChallenge {

    public static void main(String[] args) {

        SalariedEmployee salariedEmployee = new SalariedEmployee();
        salariedEmployee.name = "Shrey Jain";
        salariedEmployee.birthDate = "12122000";
        salariedEmployee.employeeId = 1;
        salariedEmployee.annualSalary = 100000.00;

        HourlyEmployee hourlyEmployee = new HourlyEmployee();
        hourlyEmployee.name = "Shrey Jain 2";
        hourlyEmployee.birthDate = "12122000";
        hourlyEmployee.employeeId = 2;
        hourlyEmployee.hourlyPayRate = 50.00;

        salariedEmployee.retire();

        System.out.println(salariedEmployee.isRetired);
        System.out.println(salariedEmployee.endDate);

        System.out.println(hourlyEmployee.getDoublePay());

    }

}

class Worker {

    String name;
    String birthDate;
    String endDate;

    int getAge() {
        return -1;
    }

    double collectPay() {
        return -1;
    }

    void terminate(String endDate) {
        this.endDate = endDate;
    }

}

class Employee extends Worker {

    long employeeId;
    String hireDate;

}

class SalariedEmployee extends Employee {

    double annualSalary;
    boolean isRetired;

    void retire() {
        isRetired = true;
        super.terminate("todays date");
    }

}

class HourlyEmployee extends Employee {

    double hourlyPayRate;

    double getDoublePay() {
        return hourlyPayRate;
    }

}