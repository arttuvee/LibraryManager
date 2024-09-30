package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField ageField;

    @FXML
    private void handleBackButton() throws IOException {
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
        Stage registerStage = (Stage) backButton.getScene().getWindow();
        registerStage.close();
    }

    // Tarkistetaan, ettei kentät ole tyhjiä
    private boolean validateFields() {
        if (isFieldEmpty(passwordField.getText(), "Virheellinen salasana!") ||
                isFieldEmpty(nameField.getText(), "Virheellinen käyttäjätunnus!") ||
                isFieldEmpty(emailField.getText(), "Virheellinen sähköposti!")) {
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(String field, String errorMessage) {
        if (field == null || field.isEmpty()) {
            showAlert("Virhe", errorMessage);
            return true;
        }
        return false;
    }

    @FXML
    private void handleRegisterButtonAction() throws IOException {
        if (!validateFields()) {
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int age = Integer.parseInt(ageField.getText());
        String role = "user"; // or retrieve from another field if applicable

        User user = new User(name, email, password, age, role);
        try {
            UserDAO.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));
        Stage mainStage = new Stage();
        mainStage.setTitle("Login View");
        mainStage.setScene(new Scene(mainRoot));
        mainStage.show();

        Stage registerStage = (Stage) registerButton.getScene().getWindow();
        registerStage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}