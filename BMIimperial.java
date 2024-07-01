public class BMIimperial extends BMI {
    private double WeightLb;
    private double HeightFeet;
    private double HeightInches;

    public BMIimperial(double weightLb, double heightFeet, double heightInches) {
        this.WeightLb = weightLb;
        this.HeightFeet = heightFeet;
        this.HeightInches = heightInches;
        this.HeightTotal = heightFeet * 12 + heightInches;
    }

    public double calculateBMI() {
        return (WeightLb / (HeightTotal * HeightTotal)) * 703;
    }

    public String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "You are Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "You are Normal weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "You are Overweight";
        } else {
            return "You are Suffering from obesity";
        }
    }
}
