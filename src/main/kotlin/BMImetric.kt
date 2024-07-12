class BMImetric(weightKg: Double, heightM: Double) : BMI() {
    init {
        this.WeightKg = weightKg
        this.HeightM = heightM
    }

    fun calculateBMI(): Double {
        return WeightKg / (HeightM * HeightM)
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
