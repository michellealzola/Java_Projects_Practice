package javafx_projects;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DormMealPlanCalculator extends Application
{

	public static void main(String[] args)
	{
		Application.launch(args);

	}
	
	@Override
	public void start(Stage primaryStage)
	{
		// create JavaFX properties to hold the selected dormitory, meal plan, and total charges
		StringProperty selectedDormitory = new SimpleStringProperty();
		StringProperty selectedMealPlan = new SimpleStringProperty();
		DoubleProperty totalCharges = new SimpleDoubleProperty();

		// create ComboBox controls for the dormitories and meal plans
		ComboBox<String> dormitoryComboBox = new ComboBox<>();
		dormitoryComboBox.getItems().addAll("Allen Hall", "Pike Hall", "Farthing Hall", "University Suites");
		dormitoryComboBox.valueProperty().bindBidirectional(selectedDormitory);

		ComboBox<String> mealPlanComboBox = new ComboBox<>();
		mealPlanComboBox.getItems().addAll("7 meals per week", "14 meals per week", "Unlimited meals");
		mealPlanComboBox.valueProperty().bindBidirectional(selectedMealPlan);

		// create bindings to calculate the total charges
		DoubleBinding dormitoryCharge = (DoubleBinding) Bindings.when(selectedDormitory.isEqualTo("Allen Hall"))
		        .then(1800.0)
		        .otherwise(Bindings.when(selectedDormitory.isEqualTo("Pike Hall"))
		                .then(2200.0)
		                .otherwise(Bindings.when(selectedDormitory.isEqualTo("Farthing Hall"))
		                        .then(2800.0)
		                        .otherwise(Bindings.when(selectedDormitory.isEqualTo("University Suites"))
		                        		.then(3000.0)
		                        		.otherwise(0))));
		DoubleBinding mealPlanCharge = (DoubleBinding) Bindings.when(selectedMealPlan.isEqualTo("7 meals per week"))
		        .then(600.0)
		        .otherwise(Bindings.when(selectedMealPlan.isEqualTo("14 meals per week"))
		                .then(1100.0)
		                .otherwise(Bindings.when(selectedMealPlan.isEqualTo("Unlimited meals"))
				                .then(1800.0)
				                .otherwise(0)));
		totalCharges.bind(dormitoryCharge.add(mealPlanCharge));

		// create a label to display the total charges
		Label totalChargesLabel = new Label();
		totalChargesLabel.textProperty().bind(Bindings.format("Total charges: $%.2f", totalCharges));

		// create a layout for the controls and label
		VBox root = new VBox(15, dormitoryComboBox, mealPlanComboBox, totalChargesLabel);
		root.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(15));	
		

		// create a scene and show it
		Scene scene = new Scene(root, 500, 250);
		scene.getStylesheets().add("happyUni.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Happy University Dormitory and Meal Plan Calculator");
		primaryStage.show();


		
	}

}
