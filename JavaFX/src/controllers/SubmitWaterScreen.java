package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;
import model.User;
import model.WaterReport;

public class SubmitWaterScreen extends Application {
	/**
	 * Current user's username
	 */
	private String username;

	@Override
	public void start(Stage stage) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
		Label date = new Label(sdf.format(new Date()));
		Label id = new Label("Report ID: " + (Singleton.getInstance().waterreports.size() + 1));
		Label user = new Label("Username: " + username);
		Label location = new Label("Location:");
		Label source = new Label("Source:");
		Label condition = new Label("Condition:");
		TextField locationText = new TextField();

		ToggleGroup sourceRadio = new ToggleGroup();
		RadioButton bottled = new RadioButton("Bottled");
    	bottled.setToggleGroup(sourceRadio);
    	bottled.setSelected(true);
    	RadioButton well = new RadioButton("Well");
    	well.setToggleGroup(sourceRadio);
    	RadioButton stream = new RadioButton("Stream");
    	stream.setToggleGroup(sourceRadio);
    	RadioButton lake = new RadioButton("Lake");
    	lake.setToggleGroup(sourceRadio);
    	RadioButton spring = new RadioButton("Spring");
    	spring.setToggleGroup(sourceRadio);
    	RadioButton other = new RadioButton("Other");
    	other.setToggleGroup(sourceRadio);

    	ToggleGroup condRadio = new ToggleGroup();
		RadioButton waste = new RadioButton("Waste");
		waste.setToggleGroup(condRadio);
		waste.setSelected(true);
    	RadioButton treat = new RadioButton("Treatable-Clear");
    	treat.setToggleGroup(condRadio);
    	RadioButton treatm = new RadioButton("Treatable-Muddy");
    	treatm.setToggleGroup(condRadio);
    	RadioButton potable = new RadioButton("Potable");
    	potable.setToggleGroup(condRadio);
    	RadioButton unknown = new RadioButton("Muddy");
    	unknown.setToggleGroup(condRadio);

		GridPane grid = new GridPane();
        GridPane.setConstraints(location, 0, 0);
        GridPane.setConstraints(source, 0, 1);
        GridPane.setConstraints(bottled, 0, 2);
        GridPane.setConstraints(stream, 0, 3);
        GridPane.setConstraints(lake, 0, 4);
        GridPane.setConstraints(spring, 0, 5);
        GridPane.setConstraints(other, 0, 6);
        GridPane.setConstraints(condition, 0, 7);
        GridPane.setConstraints(waste, 0, 8);
        GridPane.setConstraints(treat, 0, 9);
        GridPane.setConstraints(treatm, 0, 10);
        GridPane.setConstraints(potable, 0, 11);
        GridPane.setConstraints(unknown, 0, 12);
        GridPane.setConstraints(locationText, 1, 0);

        grid.getChildren().addAll(location, source, bottled, stream, lake,
        		spring, other, condition, waste, treat, treatm, potable,
        		unknown, locationText);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);

        Button submit = new Button("Submit Report");
        BooleanBinding booleanBind = locationText.textProperty().isEmpty();
        submit.disableProperty().bind(booleanBind);
        submit.setOnAction(e -> {
    		User currentUser = Singleton.getInstance().mappings.get(username);
    		String sourceSel = sourceRadio.getSelectedToggle().toString().split("\'")[1];
    		sourceSel = sourceSel.substring(0, sourceSel.length());
    		String condSel = condRadio.getSelectedToggle().toString().split("\'")[1];
    		condSel = condSel.substring(0, condSel.length());
            WaterReport w = new WaterReport(currentUser.getmUserName(),
                    date.getText().toString(), Singleton.getInstance().waterreports.size() + 1,
                    locationText.getText().toString(), sourceSel, condSel);
            Singleton.getInstance().waterreports.add(w);
            Singleton.getInstance().updateWaterReports();
            MainScreen m = new MainScreen();
            m.setUsername(username);
            m.start(stage);
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

        VBox v = new VBox(3);
        v.getChildren().addAll(date, id, user, grid, h);
        v.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(v, 500, 450));
        stage.setTitle("Submit Water Report");
        stage.show();
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
