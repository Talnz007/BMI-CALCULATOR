import java.util.Scanner;

public class BMIimperial extends BMI {
    protected void BmiMetric() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Weight (Lb): ");
        super.WeightLb = sc.nextDouble();

        // Input validation for weight (to make sure the value is non-negative)
        if (super.WeightLb < 0) {
            System.out.println("Weight cannot be negative.");
            return; // Exiting the method
        }

        System.out.println("Enter Your Height in Feet: ");
        super.HeightFeet = sc.nextDouble();
        System.out.println("Enter Your Height in Inches: ");
        super.HeightInches = sc.nextDouble();

        // Input validation for height (again checking that value is non-negative)
        if (super.HeightFeet < 0 || super.HeightInches < 0) {
            System.out.println("Height cannot be negative.");
            return; // Exiting the method
        }

        super.HeightTotal = super.HeightInches + (super.HeightFeet * 12);

        // BMI calculation
        double BMI = (super.WeightLb / (super.HeightTotal * super.HeightTotal)) * 703;
        System.out.println("Your BMI is: " + BMI);

        // Categorize BMI into diffrent types based on BMI
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
