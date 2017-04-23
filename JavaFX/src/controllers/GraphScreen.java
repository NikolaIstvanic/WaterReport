package controllers;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;

public class GraphScreen extends Application {
	/**
	 * Username of the current user
	 */
	private String username;
	/**
     * X axis of the graph.
     */
    private NumberAxis xAxis = new NumberAxis();
    /**
     * Y axis of the graph.
     */
    private NumberAxis yAxis = new NumberAxis();
    /**
	 * Graph of virus and contaminant PPM over time
	 */
	private LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

	@Override
	public void start(Stage stage) {
		Label location = new Label("Location:");
		Label year = new Label("Year:");
		TextField locationText = new TextField();
		TextField yearText = new TextField();

		GridPane grid = new GridPane();
        GridPane.setConstraints(location, 0, 0);
        GridPane.setConstraints(year, 0, 1);
        GridPane.setConstraints(locationText, 1, 0);
        GridPane.setConstraints(yearText, 1, 1);

        grid.setHgap(5);
        grid.setVgap(5);

        grid.getChildren().addAll(location, year, locationText, yearText);
        grid.setAlignment(Pos.CENTER);

        yAxis.setLabel("Parts per Million");
        xAxis.setLabel("Date");
        lineChart = new LineChart<>(xAxis, yAxis);

		Button graph = new Button("Graph");
		graph.disableProperty().bind(Bindings.or(Bindings.isEmpty(
	            locationText.textProperty()), Bindings.isEmpty(yearText.textProperty())));
        graph.setOnAction(e -> {
        	if (!Pattern.matches("[-+]?[0-9]*", yearText.getText().toString())) {
        		Alert alert = new Alert(AlertType.WARNING);
        		alert.setTitle("Ivalid year");
        		alert.setHeaderText("Invalid value for year");
        		alert.setContentText("Year field must be number");
        		alert.showAndWait();
            } else {
            	lineChart.setLegendVisible(true);
                lineChart.setTitle("Contaminant and Virus PPM in "
                		+ locationText.getText().toString() + " over "
                		+ yearText.getText().toString());
                XYChart.Series<Number, Number> viru = new XYChart.Series<>(Singleton.getInstance()
                		.VPPMValues(locationText.getText().toString(), yearText.getText().toString()));
                viru.setName("Virus PPM");
                lineChart.getData().add(viru);
                XYChart.Series<Number, Number> cont = new XYChart.Series<>(Singleton.getInstance().
                		CPPMValues(locationText.getText().toString(), yearText.getText().toString()));
                cont.setName("Contaminant PPM");
                lineChart.getData().add(cont);
            }
        });

		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			MainScreen m = new MainScreen();
            m.setUsername(username);
            m.start(stage);
		});

		HBox h = new HBox(5);
		h.getChildren().addAll(graph, cancel);
		h.setAlignment(Pos.CENTER);

		VBox v = new VBox(5);
		v.getChildren().addAll(lineChart, grid, h);
		v.setAlignment(Pos.CENTER);

		stage.setScene(new Scene(v, 1000, 1000));
        stage.setTitle("Quality Report Graph");
        stage.show();
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
