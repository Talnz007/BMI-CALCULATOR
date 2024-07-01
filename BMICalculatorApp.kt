import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class BMICalculatorApp : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "BMI Calculator"

        // Create the grid pane
        val grid = GridPane()
        grid.padding = Insets(10.0, 10.0, 10.0, 10.0)
        grid.vgap = 8.0
        grid.hgap = 10.0

        // Create the choice for units
        val unitLabel = Label("Choose Units:")
        GridPane.setConstraints(unitLabel, 0, 0)
        val unitChoice = ChoiceBox<String>()
        unitChoice.items.addAll("Metric", "Imperial")
        GridPane.setConstraints(unitChoice, 1, 0)

        // Weight input for metric
        val weightLabelMetric = Label("Weight (kg):")
        GridPane.setConstraints(weightLabelMetric, 0, 1)
        val weightInputMetric = TextField()
        GridPane.setConstraints(weightInputMetric, 1, 1)

        // Weight input for imperial
        val weightLabelImperial = Label("Weight (lb):")
        GridPane.setConstraints(weightLabelImperial, 0, 2)
        val weightInputImperial = TextField()
        GridPane.setConstraints(weightInputImperial, 1, 2)

        // Height inputs for metric
        val heightLabelMetric = Label("Height (meters):")
        GridPane.setConstraints(heightLabelMetric, 0, 3)
        val heightInputMetric = TextField()
        GridPane.setConstraints(heightInputMetric, 1, 3)

        // Height inputs for imperial
        val heightLabelFeet = Label("Height (feet):")
        GridPane.setConstraints(heightLabelFeet, 0, 4)
        val heightInputFeet = TextField()
        GridPane.setConstraints(heightInputFeet, 1, 4)

        val heightLabelInches = Label("Height (inches):")
        GridPane.setConstraints(heightLabelInches, 0, 5)
        val heightInputInches = TextField()
        GridPane.setConstraints(heightInputInches, 1, 5)

        // Calculate button
        val calculateButton = Button("Calculate BMI")
        GridPane.setConstraints(calculateButton, 1, 6)

        // Result label
        val resultLabel = Label()
        GridPane.setConstraints(resultLabel, 1, 7)

        // Add elements to the grid
        grid.children.addAll(unitLabel, unitChoice, weightLabelMetric, weightInputMetric, weightLabelImperial, weightInputImperial, heightLabelMetric, heightInputMetric, heightLabelFeet, heightInputFeet, heightLabelInches, heightInputInches, calculateButton, resultLabel)

        // Hide imperial inputs by default
        weightLabelImperial.isVisible = false
        weightInputImperial.isVisible = false
        heightLabelFeet.isVisible = false
        heightInputFeet.isVisible = false
        heightLabelInches.isVisible = false
        heightInputInches.isVisible = false

        // Show/hide inputs based on unit choice
        unitChoice.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue == "Metric") {
                weightLabelMetric.isVisible = true
                weightInputMetric.isVisible = true
                heightLabelMetric.isVisible = true
                heightInputMetric.isVisible = true

                weightLabelImperial.isVisible = false
                weightInputImperial.isVisible = false
                heightLabelFeet.isVisible = false
                heightInputFeet.isVisible = false
                heightLabelInches.isVisible = false
                heightInputInches.isVisible = false
            } else if (newValue == "Imperial") {
                weightLabelMetric.isVisible = false
                weightInputMetric.isVisible = false
                heightLabelMetric.isVisible = false
                heightInputMetric.isVisible = false

                weightLabelImperial.isVisible = true
                weightInputImperial.isVisible = true
                heightLabelFeet.isVisible = true
                heightInputFeet.isVisible = true
                heightLabelInches.isVisible = true
                heightInputInches.isVisible = true
            }
        }

        // Calculate BMI
        calculateButton.setOnAction {
            val unit = unitChoice.value
            if (unit == null) {
                resultLabel.text = "Please select a unit."
                return@setOnAction
            }

            try {
                if (unit == "Metric") {
                    val weightKg = weightInputMetric.text.toDouble()
                    val heightM = heightInputMetric.text.toDouble()
                    val metric = BMImetric(weightKg, heightM)
                    val bmi = metric.calculateBMI()
                    resultLabel.text = "Your BMI is: %.2f\n%s".format(bmi, metric.getBmiCategory(bmi))
                } else if (unit == "Imperial") {
                    val weightLb = weightInputImperial.text.toDouble()
                    val heightFeet = heightInputFeet.text.toDouble()
                    val heightInches = heightInputInches.text.toDouble()
                    val imperial = BMIimperial(weightLb, heightFeet, heightInches)
                    val bmi = imperial.calculateBMI()
                    resultLabel.text = "Your BMI is: %.2f\n%s".format(bmi, imperial.getBmiCategory(bmi))
                }
            } catch (ex: NumberFormatException) {
                resultLabel.text = "Please enter valid numbers."
            }
        }

        // Create the scene
        val scene = Scene(grid, 400.0, 400.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(BMICalculatorApp::class.java)
        }
    }
}
