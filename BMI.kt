import java.util.Scanner;

public class BMI {
    double WeightKg;
    double HeightM;
    double HeightFeet;
    double HeightInches;
    double HeightTotal;
    double WeightLb;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nPress Y to Start\nPress E to Exit\n");
            String ch = sc.nextLine();
            if (ch.equalsIgnoreCase("Y")) {
                System.out.println("Please Choose \n(1) Imperial Units \n(2) Metric Units");
                int choice = sc.nextInt();

                if (choice == 1) {
                    BMIimperial imperial = new BMIimperial(132,6,0);
                    imperial.calculateBMI();
                }
                else if (choice == 2) {
                    BMImetric metric = new BMImetric(62,1.77);
                    metric.calculateBMI();
                }
            }
            else if (ch.equalsIgnoreCase("E")) {
                break;
            }
        }
        System.out.println("\tThank You For Using Our Service\t");
        sc.close();
    }
}
