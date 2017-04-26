package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.QualityReport;
import model.Singleton;

public class SubmitQualityScreen extends Application {
	/**
	 * Current user's username
	 */
	private String username;
	private TextField virusPPM = new TextField();
	private TextField contaminantPPM = new TextField();
	private TextField locationText = new TextField();
	private ToggleGroup condRadio = new ToggleGroup();
	private Label date;

	@Override
	public void start(Stage stage) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
		date = new Label(sdf.format(new Date()));
		Label id = new Label("Report ID: " + (Singleton.getInstance().waterreports.size() + 1));
		Label user = new Label("Username: " + username);
		Label location = new Label("Location:");
		Label condition = new Label("Condition:");
		Label virus = new Label("Virus PPM:");
		Label contaminant = new Label("Contaminant PPM:");

		RadioButton safe = new RadioButton("Safe");
		safe.setToggleGroup(condRadio);
		safe.setSelected(true);
    	RadioButton treat = new RadioButton("Treatable");
    	treat.setToggleGroup(condRadio);
    	RadioButton unsafe = new RadioButton("Unsafe");
    	unsafe.setToggleGroup(condRadio);

		GridPane grid = new GridPane();
        GridPane.setConstraints(location, 0, 0);
        GridPane.setConstraints(condition, 0, 1);
        GridPane.setConstraints(safe, 0, 2);
        GridPane.setConstraints(treat, 0, 3);
        GridPane.setConstraints(unsafe, 0, 4);
        GridPane.setConstraints(virus, 0, 5);
        GridPane.setConstraints(contaminant, 0, 6);
        GridPane.setConstraints(locationText, 1, 0);
        GridPane.setConstraints(virusPPM, 1, 5);
        GridPane.setConstraints(contaminantPPM, 1, 6);

        grid.setHgap(5);
        grid.setVgap(5);

        grid.getChildren().addAll(location, condition, safe, treat, unsafe,
        		virus, contaminant, locationText, virusPPM, contaminantPPM);
        grid.setAlignment(Pos.CENTER);

        Button submit = new Button("Submit Quality Report");
        BooleanBinding booleanBind = locationText.textProperty().isEmpty().or(
        		virusPPM.textProperty().isEmpty()).or(contaminantPPM.textProperty().isEmpty());
        submit.disableProperty().bind(booleanBind);
        submit.setOnAction(e -> {
        	submitQualityReport(stage);
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
        	MainScreen m = new MainScreen();
            m.setUsername(username);
            m.start(stage);
        });

        HBox h = new HBox(5);
        h.getChildren().addAll(submit, cancel);
        h.setAlignment(Pos.CENTER);

        VBox v = new VBox(5);
        v.getChildren().addAll(date, id, user, grid, h);
        v.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(v, 500, 450));
        stage.setTitle("Submit Quality Report");
        stage.show();
	}

	public void submitQualityReport(Stage stage) {
		boolean virusCancel = false;
    	boolean contaminantCancel = false;
    	double v = 0;
    	double c = 0;
    	if (!Pattern.matches("[-+]?[0-9]*\\.?[0-9]+?", virusPPM.getText().toString())) {
            virusCancel = true;
        } else {
            v = Double.parseDouble(virusPPM.getText().toString());
        }
        if (!Pattern.matches("[-+]?[0-9]*\\.?[0-9]+?", contaminantPPM.getText().toString())) {
            contaminantCancel = true;
        } else {
            c = Double.parseDouble(contaminantPPM.getText().toString());
        }
        if (virusCancel) {
        	Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Ivalid PPM");
    		alert.setHeaderText("Invalid PPM value for Virus");
    		alert.setContentText("Invalid Virus PPM given");
    		alert.showAndWait();
        } else if (contaminantCancel) {
        	Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Ivalid PPM");
    		alert.setHeaderText("Invalid PPM value for Contaminant");
    		alert.setContentText("Invalid Contaminant PPM given");
    		alert.showAndWait();
        } else {
    		String condSel = condRadio.getSelectedToggle().toString().split("\'")[1];
    		condSel = condSel.substring(0, condSel.length());
    		QualityReport q = new QualityReport(username, date.getText().toString(),
                    Singleton.getInstance().qualityreports.size() + 1,
                    locationText.getText().toString(), condSel, v, c);
            Singleton.getInstance().qualityreports.add(q);
            Singleton.getInstance().updateQualityReports();
            MainScreen m = new MainScreen();
            m.setUsername(username);
            m.start(stage);
        }
	}

	/**
	 * Set the username for the current user using this
	 * screen.
	 *
	 * @param username current user's name
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
