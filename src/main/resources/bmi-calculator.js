document.addEventListener("DOMContentLoaded", function () {
    const unitChoice = document.getElementById("unitChoice");
    const metricInputs = document.getElementById("metricInputs");
    const imperialInputs = document.getElementById("imperialInputs");
    const calculateButton = document.getElementById("calculateButton");
    const resultLabel = document.getElementById("resultLabel");

    unitChoice.addEventListener("change", function () {
        if (unitChoice.value === "Metric") {
            metricInputs.style.display = "grid";
            imperialInputs.style.display = "none";
        } else {
            metricInputs.style.display = "none";
            imperialInputs.style.display = "grid";
        }
    });

    calculateButton.addEventListener("click", function () {
        updateResult();
    });

    function updateResult() {
        const unit = unitChoice.value;
        let weight, height;
        try {
            if (unit === "Metric") {
                weight = parseFloat(document.getElementById("weightInputMetric").value);
                height = parseFloat(document.getElementById("heightInputMetric").value);
                if (isNaN(weight) || isNaN(height) || weight <= 0 || height <= 0) {
                    throw new Error("Invalid input");
                }
            } else {
                const feet = parseFloat(document.getElementById("heightInputFeet").value);
                const inches = parseFloat(document.getElementById("heightInputInches").value);
                weight = parseFloat(document.getElementById("weightInputImperial").value);
                if (isNaN(weight) || isNaN(feet) || isNaN(inches) || weight <= 0 || feet < 0 || inches < 0) {
                    throw new Error("Invalid input");
                }
                height = (feet * 12) + inches; // Total height in inches
            }

            const bmi = calculateBMI(unit, weight, height);
            const bmiCategory = getBmiCategory(bmi);
            resultLabel.textContent = `Your BMI is: ${bmi.toFixed(2)} (${bmiCategory})`;
        } catch (ex) {
            resultLabel.textContent = ex.message; // Display the error message caught
        }
    }

    function calculateBMI(unit, weight, height) {
        if (unit === "Metric") {
            return weight / Math.pow(height, 2);
        } else {
            // For imperial units, height is in inches and weight is in pounds
            return (weight / Math.pow(height, 2)) * 703;
        }
    }

    function getBmiCategory(bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }
});
