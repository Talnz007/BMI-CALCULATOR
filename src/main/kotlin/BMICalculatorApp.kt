import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLabelElement
import kotlin.js.JsName
import kotlin.math.pow

@JsName("formatNumber")
external fun formatNumber(number: Double, decimals: Int): String

fun main() {
    document.addEventListener("DOMContentLoaded", {
        val unitLabel = createLabel("Choose Units:")
        val unitChoice = createChoiceBox(listOf("Metric", "Imperial"))

        val weightLabelMetric = createLabel("Weight (kg):")
        val weightInputMetric = createInput("number", "Weight (kg)")

        val weightLabelImperial = createLabel("Weight (lb):")
        val weightInputImperial = createInput("number", "Weight (lb)")

        val heightLabelMetric = createLabel("Height (meters):")
        val heightInputMetric = createInput("number", "Height (m)")

        val heightLabelFeet = createLabel("Height (feet):")
        val heightInputFeet = createInput("number", "Height (feet)")

        val heightLabelInches = createLabel("Height (inches):")
        val heightInputInches = createInput("number", "Height (inches)")

        val calculateButton = createButton("Calculate BMI")
        val resultLabel = createDiv()

        // Hide imperial inputs by default
        setVisibility(weightLabelImperial, false)
        setVisibility(weightInputImperial, false)
        setVisibility(heightLabelFeet, false)
        setVisibility(heightInputFeet, false)
        setVisibility(heightLabelInches, false)
        setVisibility(heightInputInches, false)

        // Event listeners for unit choice
        unitChoice.addEventListener("change", {
            val selectedUnit = unitChoice.value
            when (selectedUnit) {
                "Metric" -> {
                    setVisibility(weightLabelMetric, true)
                    setVisibility(weightInputMetric, true)
                    setVisibility(heightLabelMetric, true)
                    setVisibility(heightInputMetric, true)

                    setVisibility(weightLabelImperial, false)
                    setVisibility(weightInputImperial, false)
                    setVisibility(heightLabelFeet, false)
                    setVisibility(heightInputFeet, false)
                    setVisibility(heightLabelInches, false)
                    setVisibility(heightInputInches, false)
                }
                "Imperial" -> {
                    setVisibility(weightLabelMetric, false)
                    setVisibility(weightInputMetric, false)
                    setVisibility(heightLabelMetric, false)
                    setVisibility(heightInputMetric, false)

                    setVisibility(weightLabelImperial, true)
                    setVisibility(weightInputImperial, true)
                    setVisibility(heightLabelFeet, true)
                    setVisibility(heightInputFeet, true)
                    setVisibility(heightLabelInches, true)
                    setVisibility(heightInputInches, true)
                }
            }
        })

        // Event listener for calculate button
        calculateButton.addEventListener("click", {
            val unit = unitChoice.value
            try {
                val weight = when (unit) {
                    "Metric" -> weightInputMetric.value.toDouble()
                    "Imperial" -> weightInputImperial.value.toDouble()
                    else -> throw NumberFormatException("Invalid unit")
                }
                val height = when (unit) {
                    "Metric" -> heightInputMetric.value.toDouble()
                    "Imperial" -> heightInputFeet.value.toDouble() * 0.3048 + heightInputInches.value.toDouble() * 0.0254
                    else -> throw NumberFormatException("Invalid unit")
                }

                val bmi = calculateBMI(unit, weight, height)
                val bmiCategory = getBmiCategory(bmi)

                resultLabel.textContent = "Your BMI is: ${formatNumber(bmi, 2)}\n$bmiCategory"
            } catch (ex: NumberFormatException) {
                resultLabel.textContent = "Please enter valid numbers."
            }
        })

        // Append elements to the body
        document.body?.appendChild(unitLabel)
        document.body?.appendChild(unitChoice)
        document.body?.appendChild(weightLabelMetric)
        document.body?.appendChild(weightInputMetric)
        document.body?.appendChild(weightLabelImperial)
        document.body?.appendChild(weightInputImperial)
        document.body?.appendChild(heightLabelMetric)
        document.body?.appendChild(heightInputMetric)
        document.body?.appendChild(heightLabelFeet)
        document.body?.appendChild(heightInputFeet)
        document.body?.appendChild(heightLabelInches)
        document.body?.appendChild(heightInputInches)
        document.body?.appendChild(calculateButton)
        document.body?.appendChild(resultLabel)
    })
}

fun calculateBMI(unit: String, weight: Double, height: Double): Double {
    return if (unit == "Metric") {
        weight / height.pow(2)
    } else {
        (weight / height.pow(2)) * 703
    }
}

fun getBmiCategory(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal weight"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }
}

// Utility functions to create HTML elements

fun createLabel(text: String): HTMLLabelElement {
    val label = document.createElement("label") as HTMLLabelElement
    label.textContent = text
    return label
}

fun createChoiceBox(options: List<String>): HTMLInputElement {
    val select = document.createElement("select") as HTMLInputElement
    options.forEach {
        val option = document.createElement("option")
        option.textContent = it
        select.appendChild(option)
    }
    return select
}

fun createInput(type: String, placeholder: String): HTMLInputElement {
    val input = document.createElement("input") as HTMLInputElement
    input.type = type
    input.placeholder = placeholder
    return input
}

fun createButton(text: String): HTMLButtonElement {
    val button = document.createElement("button") as HTMLButtonElement
    button.textContent = text
    return button
}

fun createDiv(): HTMLDivElement {
    return document.createElement("div") as HTMLDivElement
}

fun setVisibility(element: dynamic, isVisible: Boolean) {
    element.style.visibility = if (isVisible) "visible" else "hidden"
}
