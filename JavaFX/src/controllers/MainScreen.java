package controllers;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;

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
        BorderPane main = new BorderPane();

        Button logout = new Button("Logout");
        logout.setOnAction(e -> new LoginScreen().start(stage));

        Button profile = new Button("Profile");
        profile.setOnAction(e -> new ProfileScreen().start(stage));

        Button history = new Button("History");
        history.setOnAction(e -> new HistoryScreen().start(stage));

        Button create = new Button("Create");
        create.setOnAction(e -> new CreateScreen().start(stage));

        HBox bottom = new HBox(10);
        bottom.getChildren().addAll(create, history);
        bottom.setAlignment(Pos.CENTER);

        /* Check if Manager type of User */
        if (Singleton.getInstance().mappings.get(username).getmPosition().equals("Manager")) {
        	Button graph = new Button("Graph");
        	graph.setOnAction(e -> new GraphScreen().start(stage));
        	bottom.getChildren().add(graph);
        }

        HBox top = new HBox(10);
        top.getChildren().addAll(logout, profile);
        top.setAlignment(Pos.CENTER);

        VBox v = new VBox(10);
        v.getChildren().addAll(top, bottom);
        v.setAlignment(Pos.CENTER);

        main.setCenter(v);
        stage.setScene(new Scene(main, 500, 200));
        stage.setTitle("Water Report");
        stage.show();
    }

    /**
     * Set the current user's username so that other screens can see it
     * beyond MainScreen.
     *
     * @param username current user's username
     */
    public void setUser(String username) {
    	this.username = username;
    }
}
