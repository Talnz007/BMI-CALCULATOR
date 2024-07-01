import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BMICalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BMI Calculator");

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Create the choice for units
        Label unitLabel = new Label("Choose Units:");
        GridPane.setConstraints(unitLabel, 0, 0);
        ChoiceBox<String> unitChoice = new ChoiceBox<>();
        unitChoice.getItems().addAll("Metric", "Imperial");
        GridPane.setConstraints(unitChoice, 1, 0);

        // Weight input
        Label weightLabel = new Label("Weight:");
        GridPane.setConstraints(weightLabel, 0, 1);
        TextField weightInput = new TextField();
        GridPane.setConstraints(weightInput, 1, 1);

        // Height inputs for metric
        Label heightLabelMetric = new Label("Height (meters):");
        GridPane.setConstraints(heightLabelMetric, 0, 2);
        TextField heightInputMetric = new TextField();
        GridPane.setConstraints(heightInputMetric, 1, 2);

        // Height inputs for imperial
        Label heightLabelFeet = new Label("Height (feet):");
        GridPane.setConstraints(heightLabelFeet, 0, 3);
        TextField heightInputFeet = new TextField();
        GridPane.setConstraints(heightInputFeet, 1, 3);

        Label heightLabelInches = new Label("Height (inches):");
        GridPane.setConstraints(heightLabelInches, 0, 4);
        TextField heightInputInches = new TextField();
        GridPane.setConstraints(heightInputInches, 1, 4);

        // Calculate button
        Button calculateButton = new Button("Calculate BMI");
        GridPane.setConstraints(calculateButton, 1, 5);

        // Result label
        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 1, 6);

        // Add elements to the grid
        grid.getChildren().addAll(unitLabel, unitChoice, weightLabel, weightInput, heightLabelMetric, heightInputMetric, heightLabelFeet, heightInputFeet, heightLabelInches, heightInputInches, calculateButton, resultLabel);

        // Hide imperial height inputs by default
        heightLabelFeet.setVisible(false);
        heightInputFeet.setVisible(false);
        heightLabelInches.setVisible(false);
        heightInputInches.setVisible(false);

        // Show/hide inputs based on unit choice
        unitChoice.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.equals("Metric")) {
                heightLabelMetric.setVisible(true);
                heightInputMetric.setVisible(true);
                heightLabelFeet.setVisible(false);
                heightInputFeet.setVisible(false);
                heightLabelInches.setVisible(false);
                heightInputInches.setVisible(false);
            } else if (newValue.equals("Imperial")) {
                heightLabelMetric.setVisible(false);
                heightInputMetric.setVisible(false);
                heightLabelFeet.setVisible(true);
                heightInputFeet.setVisible(true);
                heightLabelInches.setVisible(true);
                heightInputInches.setVisible(true);
            }
        });

        // Calculate BMI
        calculateButton.setOnAction(e -> {
            String unit = unitChoice.getValue();
            if (unit == null) {
                resultLabel.setText("Please select a unit.");
                return;
            }

            try {
                if (unit.equals("Metric")) {
                    double weightKg = Double.parseDouble(weightInput.getText());
                    double heightM = Double.parseDouble(heightInputMetric.getText());
                    BMImetric metric = new BMImetric(weightKg, heightM);
                    double bmi = metric.calculateBMI();
                    resultLabel.setText(String.format("Your BMI is: %.2f\n%s", bmi, metric.getBmiCategory(bmi)));
                } else if (unit.equals("Imperial")) {
                    double weightLb = Double.parseDouble(weightInput.getText());
                    double heightFeet = Double.parseDouble(heightInputFeet.getText());
                    double heightInches = Double.parseDouble(heightInputInches.getText());
                    BMIimperial imperial = new BMIimperial(weightLb, heightFeet, heightInches);
                    double bmi = imperial.calculateBMI();
                    resultLabel.setText(String.format("Your BMI is: %.2f\n%s", bmi, imperial.getBmiCategory(bmi)));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        // Create the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
