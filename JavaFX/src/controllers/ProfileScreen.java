package controllers;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;
import model.User;

public class ProfileScreen extends Application {
	/**
	 * Username for the current user of this screen.
	 */
	private String username;

	@Override
	public void start(Stage stage) {
		ToggleGroup group = new ToggleGroup();
		RadioButton mr = new RadioButton("Mr.");
    	mr.setToggleGroup(group);
    	RadioButton ms = new RadioButton("Ms.");
    	ms.setToggleGroup(group);
    	RadioButton mrs = new RadioButton("Mrs.");
    	mrs.setToggleGroup(group);

    	User curr = Singleton.getInstance().mappings.get(username);
    	if (curr.getmTitle().equals("Mr.")) {
    		group.selectToggle(mr);
    	} else if (curr.getmTitle().equals("Ms.")) {
    		group.selectToggle(ms);
    	} else {
    		group.selectToggle(mrs);
    	}

    	TextField emailText = new TextField(curr.getmEmail());
		TextField addressText = new TextField(curr.getmHomeAddress());

		Button submit = new Button("Submit changes");
		submit.setOnAction(e -> {
			curr.setmEmail(emailText.getText().toString());
			curr.setmHomeAddress(addressText.getText().toString());
			String sel = group.getSelectedToggle().toString().split("\'")[1];
			curr.setmTitle(sel.substring(0, sel.length()));
			stage.close();
        	MainScreen m = new MainScreen();
        	m.setUsername(username);
        	Singleton.getInstance().updateUsers();
        	m.start(stage);
        });

		GridPane grid = new GridPane();
        Label email = new Label("Email:");
        Label address = new Label("Home Address:");
        grid.setHgap(10);
        grid.setVgap(10);

        GridPane.setConstraints(email, 0, 0);
        GridPane.setConstraints(address, 0, 1);
        GridPane.setConstraints(mr, 0, 2);
        GridPane.setConstraints(ms, 0, 3);
        GridPane.setConstraints(mrs, 0, 4);
        GridPane.setConstraints(emailText, 1, 0);
        GridPane.setConstraints(addressText, 1, 1);

        grid.getChildren().addAll(email, address, mr, ms, mrs,
        		emailText, addressText);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);

        VBox v = new VBox(10);
        v.getChildren().addAll(grid, submit);
        v.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(v, 500, 300));
        stage.setTitle("Profile");
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
