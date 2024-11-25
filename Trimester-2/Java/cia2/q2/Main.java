class Main {
    public static void main(String[] args) {

        System.out.println(hasSharedDigit(12, 23));
        System.out.println(hasSharedDigit(9, 99));
        System.out.println(hasSharedDigit(15, 55));
        
    }

    public static boolean hasSharedDigit (int value1, int value2) {
        if (value1 > 99 || value1 < 10 || value2 > 99 || value2 < 10) {
            return false;
        } else {
            String strValue1 = Integer.toString(value1);
            String strValue2 = Integer.toString(value2);

            for (char ch1 : strValue1.toCharArray()) {
                for (char ch2 : strValue2.toCharArray()) {
                    if (ch1 == ch2) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}