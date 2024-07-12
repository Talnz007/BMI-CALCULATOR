document.addEventListener("DOMContentLoaded", function () {
    const unitChoice = document.getElementById("unitChoice");
    const metricInputs = document.getElementById("metricInputs");
    const imperialInputs = document.getElementById("imperialInputs");
    const calculateButton = document.getElementById("calculateButton");
    const resultLabel = document.getElementById("resultLabel");

    const EDAMAM_APP_ID = '773ade91';
    const EDAMAM_APP_KEY = '2ae4b85354799148ab3b5153a6c71c1d';
    const SPOONACULAR_API_KEY = '9627afef187c4f7a94ca2a826896f895 ';

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

        if (unit === "Metric") {
            weight = parseFloat(document.getElementById("weightInputMetric").value);
            height = parseFloat(document.getElementById("heightInputMetric").value) / 100; // Convert cm to meters
        } else {
            const feet = parseFloat(document.getElementById("heightInputFeet").value);
            const inches = parseFloat(document.getElementById("heightInputInches").value);
            weight = parseFloat(document.getElementById("weightInputImperial").value) * 0.453592; // Convert lb to kg
            height = ((feet * 12) + inches) * 0.0254; // Convert feet and inches to meters
        }

        if (isNaN(weight) || isNaN(height) || weight <= 0 || height <= 0) {
            resultLabel.textContent = "Please enter valid numbers.";
            return;
        }

        const bmi = weight / (height * height);
        const bmiCategory = getBmiCategory(bmi);
        resultLabel.innerHTML = `Your BMI is: ${bmi.toFixed(2)} (${bmiCategory})`;

        fetchHealthTips(bmi, bmiCategory);
    }

    function getBmiCategory(bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    async function fetchHealthTips(bmi, category) {
        let query = "";
        if (category === "Underweight") {
            query = "high calorie foods";
        } else if (category === "Normal weight") {
            query = "healthy diet";
        } else if (category === "Overweight") {
            query = "low calorie foods";
        } else if (category === "Obese") {
            query = "weight loss foods";
        }

        try {
            const edamamResponse = await fetch(`https://api.edamam.com/api/food-database/v2/parser?ingr=${query}&app_id=${EDAMAM_APP_ID}&app_key=${EDAMAM_APP_KEY}`);
            const edamamData = await edamamResponse.json();

            const spoonacularResponse = await fetch(`https://api.spoonacular.com/recipes/complexSearch?query=${query}&apiKey=${SPOONACULAR_API_KEY}`);
            const spoonacularData = await spoonacularResponse.json();

            displayHealthTipsAndRecipes(edamamData, spoonacularData, category);
        } catch (error) {
            resultLabel.innerHTML += `<br>Could not fetch health tips. Please try again later.`;
        }
    }

    function displayHealthTipsAndRecipes(edamamData, spoonacularData, category) {
        const edamamHints = edamamData.hints;
        const spoonacularResults = spoonacularData.results;

        const uniqueEdamamHints = Array.from(new Set(edamamHints.map(hint => hint.food.label)))
            .map(label => edamamHints.find(hint => hint.food.label === label));
        const uniqueSpoonacularResults = Array.from(new Set(spoonacularResults.map(result => result.title)))
            .map(title => spoonacularResults.find(result => result.title === title));

        let tips = "<br>Health Tips:<ul>";
        tips += getStaticHealthTips(category);
        tips += "</ul>";

        let recipes = "<br>Recipes:<ul>";
        uniqueEdamamHints.forEach(hint => {
            recipes += `<li>${hint.food.label} - ${hint.food.category}</li>`;
        });
        uniqueSpoonacularResults.forEach(result => {
            recipes += `<li>${result.title}</li>`;
        });
        recipes += "</ul>";

        resultLabel.innerHTML += tips + recipes;
    }

    function getStaticHealthTips(category) {
        const tips = {
            "Underweight": [
                "Include nutrient-rich foods in your diet.",
                "Consider eating more frequent, smaller meals.",
                "Add healthy fats like nuts and avocados."
            ],
            "Normal weight": [
                "Maintain a balanced diet with a variety of nutrients.",
                "Keep up with regular physical activity.",
                "Stay hydrated and avoid sugary drinks."
            ],
            "Overweight": [
                "Incorporate more fruits and vegetables into your meals.",
                "Reduce your intake of sugary and high-fat foods.",
                "Increase your physical activity gradually."
            ],
            "Obese": [
                "Consult a healthcare provider for personalized advice.",
                "Focus on whole, unprocessed foods.",
                "Consider a balanced diet with controlled portions."
            ]
        };

        return tips[category].map(tip => `<li>${tip}</li>`).join("");
    }
});
