package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {
    @FXML
    private Button backButton;

    @FXML
    private Button registerButton;

    @FXML
    private void handleBackButton() throws IOException {
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));

        // Create a new stage for the login view
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();

        // Close the register window
        Stage registerStage = (Stage) backButton.getScene().getWindow();
        registerStage.close();
    }

    @FXML
    private void handleRegisterButtonAction() throws IOException{
        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")));

        // new stage for mainview
        Stage mainStage = new Stage();
        mainStage.setTitle("Main View");
        mainStage.setScene(new Scene(mainRoot));
        mainStage.show();

        // Close the register window.
        Stage registerStage = (Stage) registerButton.getScene().getWindow();
        registerStage.close();
    }

}