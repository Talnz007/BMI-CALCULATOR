import java.util.Scanner;

public class BMImetric extends BMI {
    protected void BmiMetric() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Weight (Kg): ");
        super.WeightKg = sc.nextDouble();

        // Input validation for weight (ensure it's non-negative)
        if (super.WeightKg < 0) {
            System.out.println("Weight cannot be negative.");
            return; // Exit the method
        }

        System.out.println("Enter Your Height (Meters): ");
        super.HeightM = sc.nextDouble();

        // Input validation for height (ensure it's non-negative)
        if (super.HeightM < 0) {
            System.out.println("Height cannot be negative.");
            return; // Exit the method
        }

        double BMI = (super.WeightKg / (super.HeightM * super.HeightM));
        System.out.println("Your BMI is: " + BMI);

        if (BMI < 18.5) {
            System.out.println("\nYou are Underweight");
        } else if (BMI >= 18.5 && BMI <= 24.9) {
            System.out.println("\nYou are Normal weight");
        } else if (BMI >= 25 && BMI <= 29.9) {
            System.out.println("\nYou are Overweight");
        } else if (BMI >= 30) {
            System.out.println("\nYou are Suffering from obesity");
        }
        sc.close();
    }
}
