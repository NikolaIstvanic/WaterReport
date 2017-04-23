package controllers;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;
import model.WaterReport;

/**
 * A login screen that offers login via username/password.
 */
public class MainScreen extends Application {
	/**
	 * Current user's username
	 */
	private String username;

    @Override
    public void start(Stage stage) {
        Button logout = new Button("Logout");
        logout.setOnAction(e -> new LoginScreen().start(stage));

        Button profile = new Button("Profile");
        profile.setOnAction(e -> {
        	ProfileScreen p = new ProfileScreen();
        	p.setUsername(username);
        	p.start(stage);
        });

        Button history = new Button("History");
        history.setOnAction(e -> {
        	Stage helper = new Stage();
            TextArea a = new TextArea();
            a.setWrapText(true);
            a.setEditable(false);
            for (WaterReport wr : Singleton.getInstance().waterreports) {
            	a.appendText(wr.toString());
            }
            helper.setTitle("Water Report History");
            helper.setScene(new Scene(a));
            helper.show();
        });

        Button create = new Button("Submit Report");
        create.setOnAction(e -> {
    		SubmitWaterScreen s = new SubmitWaterScreen();
    		s.setUsername(username);
    		s.start(stage);
        });

        HBox bottom = new HBox(10);
        bottom.getChildren().addAll(create, history);
        bottom.setAlignment(Pos.CENTER);

        if (Singleton.getInstance().mappings.get(username).getmPosition().equals("Worker")) {
        	Button quality = new Button("Submit Quality Report");
        	quality.setOnAction(e -> {
        		SubmitQualityScreen s = new SubmitQualityScreen();
        		s.setUsername(username);
        		s.start(stage);
        	});
        	bottom.getChildren().add(quality);
        }

        /* Check if Manager type of User */
        if (Singleton.getInstance().mappings.get(username).getmPosition().equals("Manager")) {
        	Button graph = new Button("Graph");
        	graph.setOnAction(e -> {
        		GraphScreen g = new GraphScreen();
        		g.setUsername(username);
        		g.start(stage);
        	});
        	bottom.getChildren().add(graph);
        }

        HBox top = new HBox(10);
        top.getChildren().addAll(logout, profile);
        top.setAlignment(Pos.CENTER);

        VBox v = new VBox(10);
        v.getChildren().addAll(top, bottom);
        v.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(v, 500, 200));
        stage.setTitle("Water Report");
        stage.show();
    }

    /**
     * Set the current user's username so that other screens can see it
     * beyond MainScreen.
     *
     * @param username current user's username
     */
    public void setUsername(String username) {
    	this.username = username;
    }
}
