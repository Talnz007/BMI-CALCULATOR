public class BMImetric extends BMI {
    public BMImetric(double weightKg, double heightM) {
        this.WeightKg = weightKg;
        this.HeightM = heightM;
    }

    public double calculateBMI() {
        return (WeightKg / (HeightM * HeightM));
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
