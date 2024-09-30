package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import database.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButtonAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = UserDAO.getUserByName(username);
            if (user != null) {
                String storedPassword = user.getPassword();
                System.out.println("Input Password: " + password);
                System.out.println("Stored Password: " + storedPassword);

                if (password != null && password.equals(storedPassword)) {
                    // Load the MainView.fxml file
                    Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")));

                    // Create a new stage for MainView
                    Stage mainStage = new Stage();
                    mainStage.setTitle("LibraryManager");
                    mainStage.setScene(new Scene(mainRoot));
                    mainStage.show();

                    // Close the login window
                    Stage loginStage = (Stage) loginButton.getScene().getWindow();
                    loginStage.close();
                } else {
                    // Handle invalid login
                    System.out.println("Invalid username or password");
                }
            } else {
                // Handle invalid login
                System.out.println("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleRegisterButtonAction() throws IOException {
        // Load the RegisterView.fxml file
        Parent registerRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RegisterView.fxml")));

        // Create a new stage for RegisterView
        Stage registerStage = new Stage();
        registerStage.setTitle("LibraryManager - Register");
        registerStage.setScene(new Scene(registerRoot));
        registerStage.show();

        // Close the login window
        Stage loginStage = (Stage) registerButton.getScene().getWindow();
        loginStage.close();
    }
}