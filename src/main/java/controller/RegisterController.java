package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.User;
import database.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterController {
    @FXML
    private Button backButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

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
    private void handleRegisterButtonAction() throws IOException {
        String name = nameField.getText();
        String password = passwordField.getText();
        String role = "user";


        User user = new User();
        user.setName(name);

        user.setPassword(password);  // Salasana tallennetaan
        user.setRole(role);

        try {
            UserDAO.addUser(user);  // Tallennetaan käyttäjä tietokantaan

            // Avaa pääikkuna
            Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")));
            Stage mainStage = new Stage();
            mainStage.setTitle("Main View");
            mainStage.setScene(new Scene(mainRoot));
            mainStage.show();

            // Sulje rekisteröinti-ikkuna
            Stage registerStage = (Stage) registerButton.getScene().getWindow();
            registerStage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
