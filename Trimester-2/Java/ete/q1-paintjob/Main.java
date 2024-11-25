import static java.lang.Math.ceil;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double width;
        double height;
        double area;
        int extraBuckets;

        while (true) {

            System.out.print("Enter 1 for 4 para fn and 0 for 3 para fn: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.print("Enter width: ");
                width = scanner.nextDouble();
                System.out.print("Enter height: ");
                height = scanner.nextDouble();
                System.out.print("Enter area per Bucket: ");
                area = scanner.nextDouble();
                System.out.println(getBucketCount(width, height, area));
            } else {
                System.out.print("Enter width: ");
                width = scanner.nextDouble();
                System.out.print("Enter height: ");
                height = scanner.nextDouble();
                System.out.print("Enter area per Bucket: ");
                area = scanner.nextDouble();
                System.out.print("Enter Buckets at home: ");
                extraBuckets = scanner.nextInt();
                
                System.out.println(getBucketCount(width, height, area, extraBuckets));
            }

        }


    }

    private static int getBucketCount(double width, double height, double areaPerBucket) {
        return getBucketCount(width, height, areaPerBucket, 0);
    }

    private static int getBucketCount(double width, double height, double areaPerBucket, int extraBuckets) {

        if (checkValid(width, height, areaPerBucket, extraBuckets) == 0) {
            return -1;
        } else {
            return (int) ceil(((width * height) / areaPerBucket) - extraBuckets);
        }

    }

    private static int checkValid(double width, double height, double areaPerBucket, int extraBuckets) {
        if (
                width > 0 &&
                        height > 0 &&
                        areaPerBucket > 0 &&
                        extraBuckets >= 0
        ) return 1;
        else return 0;
    }
}