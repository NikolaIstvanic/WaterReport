package controllers;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Admin;
import model.Manager;
import model.Singleton;
import model.User;
import model.Worker;

/**
 * A login screen that offers login via username/password.
 */
public class RegistrationScreen extends Application {
    // UI references.
    private TextField userText;
    private TextField emailText;
    private PasswordField passText1;
    private PasswordField passText2;
    private ToggleGroup group = new ToggleGroup();

    @Override
    public void start(Stage stage) {
    	RadioButton user = new RadioButton("User");
    	user.setToggleGroup(group);
    	user.setSelected(true);
    	RadioButton worker = new RadioButton("Worker");
    	worker.setToggleGroup(group);
    	RadioButton manager = new RadioButton("Manager");
    	manager.setToggleGroup(group);
    	RadioButton admin = new RadioButton("Admin");
    	admin.setToggleGroup(group);

        userText = new TextField();
        emailText = new TextField();
        passText1 = new PasswordField();
        passText2 = new PasswordField();

        Button register = new Button("Register");
        BooleanBinding booleanBind = userText.textProperty().isEmpty().or(
        		emailText.textProperty().isEmpty()).or(
        		passText1.textProperty().isEmpty()).or(
        		passText2.textProperty().isEmpty());
        register.disableProperty().bind(booleanBind);
        register.setOnAction(e -> attemptRegister(stage));
        passText2.setOnKeyReleased(p -> {
            if (p.getCode() == KeyCode.ENTER) {
                register.fire();
            }
        });

        Button cancel = new Button("Cancel registration");
        cancel.setOnAction(e -> new LoginScreen().start(stage));

        GridPane grid = new GridPane();
        Label userlabel = new Label("Username:");
        Label email = new Label("Email:");
        Label pass1 = new Label("Password:");
        Label pass2 = new Label("Re-enter password:");

        GridPane.setConstraints(userlabel, 0, 0);
        GridPane.setConstraints(email, 0, 1);
        GridPane.setConstraints(pass1, 0, 2);
        GridPane.setConstraints(pass2, 0, 3);
        GridPane.setConstraints(user, 0, 4);
        GridPane.setConstraints(worker, 0, 5);
        GridPane.setConstraints(manager, 0, 6);
        GridPane.setConstraints(admin, 0, 7);
        GridPane.setConstraints(userText, 1, 0);
        GridPane.setConstraints(emailText, 1, 1);
        GridPane.setConstraints(passText1, 1, 2);
        GridPane.setConstraints(passText2, 1, 3);

        grid.getChildren().addAll(userlabel, email, pass1, pass2, userText,
        		emailText, passText1, passText2, user, worker, manager, admin);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);

        HBox b = new HBox(10);
        b.getChildren().addAll(register, cancel);
        b.setAlignment(Pos.CENTER);

        VBox v = new VBox(10);
        v.getChildren().addAll(grid, b);
        v.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(v, 500, 300));
        stage.setTitle("Register");
        stage.show();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister(Stage stage) {
    	boolean usercancel = false; // if username already exists
    	boolean emailcancel = false; // if email is formated incorrectly
    	boolean passmatchcancel = false; // if passwords don't match
        // Store values at the time of the login attempt.
        String username = userText.getText().toString();
        String email = emailText.getText().toString();
        String password1 = passText1.getText().toString();
        String password2 = passText2.getText().toString();
        String type = group.getSelectedToggle().toString();
        // get selected radio button label
        type = type.split("\'")[1].substring(0, type.split("\'")[1].length());
        // Check if username doesn't already exist.
        if (!isUsernameValid(username) || Singleton.getInstance().mappings
        		.containsKey(username)) {
            usercancel = true;
        }
        // Check for valid email formatting.
        if (!isIDValid(email)) {
        	emailcancel = true;
        }
        // Check for matching and valid passwords.
        if (!password1.equals(password2)
        		|| !isPasswordValid(password1, password2)) {
        	passmatchcancel = true;
        }
    	if (usercancel) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Ivalid username");
    		alert.setHeaderText("Invalid username given");
    		alert.setContentText("Username already exists");
    		alert.showAndWait();
    	} else if (emailcancel) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Incorrect email formatting");
    		alert.setHeaderText("Email doesn't follow required formatting");
    		alert.setContentText("Email must be formatted example@test.com");
    		alert.showAndWait();
    	} else if (passmatchcancel) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Invalid password");
    		alert.setHeaderText("Password invalid");
    		alert.setContentText(
    				"Passwords must match and have at least one letter");
    		alert.showAndWait();
    	}
    	// Successful login
    	if (!(usercancel || emailcancel || passmatchcancel)) {
    		User userToAdd = type.equals("Worker") ? new Worker(username, password1, email, "Worker")
                    : type.equals("Manager") ? new Manager(username, password1, email, "Manager")
                    : type.equals("Admin") ? new Admin(username, password1, email, "Admin")
                    : new User(username, password1, email, "User");
            userToAdd.setmHomeAddress("");
            userToAdd.setmTitle("Mr.");
            Singleton.getInstance().addToMappings(username, userToAdd);
            new LoginScreen().start(stage);
    	}
    }

    /**
     * Validation for potential username:
     * must have length greater than 1
     * must not be null
     * @param username potential username
     * @return true if this username is valid
     */
    private boolean isUsernameValid(String username) {
        return username != null && username.length() > 0;
    }

    /**
     * Email validation (must have @something.extension)
     * @param id email trying to validate
     * @return true if this email is valid
     */
    private boolean isIDValid(String id) {
        return Pattern.matches("[A-Za-z0-9\\._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}", id);
    }

    private boolean isPasswordValid(String password1, String password2) {
        return password1.length() > 0 && password2.length() > 0;
    }
}
