class Main {
    public static void main(String[] args) {

        System.out.println(isCatPlaying(true, 10));
        System.out.println(isCatPlaying(false, 36));
        System.out.println(isCatPlaying(false, 35));
        
    }

    static boolean isCatPlaying (boolean summer, int temprature) {
        if (temprature < 25) {
            return false;
        } else {
            if (summer == true && temprature > 45) {
                return false;
            } else if (summer == false && temprature > 35) {
                return false;
            } else {
                return true;
            }
        }
    }
}