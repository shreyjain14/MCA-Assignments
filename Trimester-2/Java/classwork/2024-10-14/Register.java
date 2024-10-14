class Register {

    int id;
    String username;
    String email;
    String password;

    Register(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    void method(String username, String email, String password) {
        // get id from database
        this.username = username;
        this.email = email;
        this.password = password;
    }
    void method(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    void method(String username, String password) {
        // get id from database
        this.username = username;
        this.password = password;
    }

    void printUser() {
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
    }

    public static void main(String args[]) {

        Register user1 = new Register(1, "user1", "user@email.com", "password1");
        user1.printUser();

        user1.method("user2", "email2", "password2");
        user1.printUser();

        user1.method("user3", "password3");
        user1.printUser();

    }

}