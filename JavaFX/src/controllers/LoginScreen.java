package controllers;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Singleton;

/**
 * A login screen that offers login via username/password.
 */
public class LoginScreen extends Application {
    // UI references.
    private TextField userText;
    private PasswordField passText;

    @Override
    public void start(Stage stage) {
        userText = new TextField();
        passText = new PasswordField();

        Button login = new Button("Login");
        BooleanBinding booleanBind = Bindings.or(userText.textProperty().isEmpty(),
                passText.textProperty().isEmpty());
        login.disableProperty().bind(booleanBind);
        login.setOnAction(e -> attemptLogin(stage));
        passText.setOnKeyReleased(p -> {
            if (p.getCode() == KeyCode.ENTER) {
                login.fire();
            }
        });

        Button register = new Button("Register");
        register.setOnAction(e -> new RegistrationScreen().start(stage));

        Label user = new Label("Username:");
        Label pass = new Label("Password: ");
        HBox u = new HBox();
        u.getChildren().addAll(user, userText);
        u.setAlignment(Pos.CENTER);
        HBox p = new HBox();
        p.getChildren().addAll(pass, passText);
        p.setAlignment(Pos.CENTER);
        HBox b = new HBox(5);
        b.getChildren().addAll(login, register);
        b.setAlignment(Pos.CENTER);
        VBox v = new VBox(5);
        v.getChildren().addAll(u, p, b);
        v.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(v, 500, 200));
        stage.setTitle("Water Report Login");
        stage.show();
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(Stage stage) {
    	boolean usercancel = false; // if username doesn't exist
    	boolean wrongcancel = false; // if incorrect login
        // Store values at the time of the login attempt.
        String username = userText.getText().toString();
        String password = passText.getText().toString();
        // Check for a valid username.
        if (!isUsernameValid(username) || !Singleton.getInstance().mappings.containsKey(username)) {
            usercancel = true;
        }
        // Check for valid login attempt.
        if (Singleton.getInstance().mappings.keySet().contains(username)) {
            wrongcancel = !Singleton.getInstance().lookup(username, password);
        }
    	if (usercancel) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Invalid username");
    		alert.setHeaderText("Invalid username given");
    		alert.setContentText("Username does not exist");
    		alert.showAndWait();
    	} else if (wrongcancel) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Login Unsuccessful");
    		alert.setHeaderText("Invalid login");
    		alert.setContentText("Username does not match password");
    		alert.showAndWait();
    	}
    	// Successful login
    	if (!wrongcancel) {
    		MainScreen m = new MainScreen();
    		m.setUsername(username); // pass current user's information down
        	m.start(stage);
    	}
    }

    private boolean isUsernameValid(String username) {
        return username != null && username.length() > 0;
    }

    public static void main(String[] args) {
    	launch(args);
    }
}
