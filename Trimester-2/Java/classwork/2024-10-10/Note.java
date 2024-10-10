class Note {
    String title;
    String content;
    
    void Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    void print() {
        System.out.println("---------------------");
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("---------------------");
    }

}