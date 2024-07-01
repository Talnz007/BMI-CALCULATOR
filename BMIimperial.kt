class BMIimperial(weightLb: Double, heightFeet: Double, heightInches: Double) : BMI() {
    init {
        this.WeightLb = weightLb
        this.HeightFeet = heightFeet
        this.HeightInches = heightInches
        this.HeightTotal = heightFeet * 12 + heightInches
    }

    fun calculateBMI(): Double {
        return (WeightLb / (HeightTotal * HeightTotal)) * 703
    }

    fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "You are Underweight"
            bmi in 18.5..24.9 -> "You are Normal weight"
            bmi in 25.0..29.9 -> "You are Overweight"
            else -> "You are Suffering from obesity"
        }
    }
}
