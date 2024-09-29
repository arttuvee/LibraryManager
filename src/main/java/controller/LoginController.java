package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    // This method will be called when the login button is clicked in login view
    // Open the main view and close the login view
    @FXML
    private void handleLoginButtonAction() throws IOException {
        // Load the MainView.fxml file
        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")));

        // TODO: Login verification here !

        // Create a new stage for MainView
        Stage mainStage = new Stage();
        mainStage.setTitle("LibraryManager");
        mainStage.setScene(new Scene(mainRoot));
        mainStage.show();

        // Close the login window
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();
    }

    // This method will be called when the register button is clicked in login view
    // Open the register view and close the login view
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