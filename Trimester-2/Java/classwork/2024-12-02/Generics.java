class Generics {
    public static void main(String[] args) {

        ABC<Integer> obj = new ABC<>(15);
        System.out.println(obj.getObj());

        ABC<String> obj2 = new ABC<>("Hello");
        System.out.println(obj2.getObj());
        
    }
}

class ABC<T> {

    T obj;
    
    ABC(T obj) {
        this.obj = obj;
    }
    
    T getObj() {
        return obj;
    }
}