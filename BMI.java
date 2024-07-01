import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BMI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BMI Calculator");

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Weight Label and Input
        Label weightLabel = new Label("Weight (Kg):");
        GridPane.setConstraints(weightLabel, 0, 0);
        TextField weightInput = new TextField();
        GridPane.setConstraints(weightInput, 1, 0);

        // Height Label and Input
        Label heightLabel = new Label("Height (Meters):");
        GridPane.setConstraints(heightLabel, 0, 1);
        TextField heightInput = new TextField();
        GridPane.setConstraints(heightInput, 1, 1);

        // Calculate Button
        Button calculateButton = new Button("Calculate BMI");
        GridPane.setConstraints(calculateButton, 1, 2);

        // Result Label
        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 1, 3);

        // Add all elements to the grid
        grid.getChildren().addAll(weightLabel, weightInput, heightLabel, heightInput, calculateButton, resultLabel);

        // Set the event for the calculate button
        calculateButton.setOnAction(e -> {
            try {
                double weightKg = Double.parseDouble(weightInput.getText());
                double heightM = Double.parseDouble(heightInput.getText());
                double bmi = weightKg / (heightM * heightM);
                resultLabel.setText(String.format("Your BMI is: %.2f\n%s", bmi, getBmiCategory(bmi)));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        // Scene
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "You are Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "You are Normal weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "You are Overweight";
        } else if (bmi >= 30) {
            return "You are Suffering from obesity";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
