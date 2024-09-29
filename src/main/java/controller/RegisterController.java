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

    @FXML
    private void handleRegisterButtonAction() throws IOException {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int age = Integer.parseInt(ageField.getText());
        String role = "user"; // or retrieve from another field if applicable

        // Debug statements
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Age: " + age);
        System.out.println("Role: " + role);

        if (password == null || password.isEmpty()) {
            System.out.println("Password is null or empty");
            return;
        }

        User user = new User(name, email, password, age, role);
        try {
            UserDAO.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")));
        Stage mainStage = new Stage();
        mainStage.setTitle("Main View");
        mainStage.setScene(new Scene(mainRoot));
        mainStage.show();

        Stage registerStage = (Stage) registerButton.getScene().getWindow();
        registerStage.close();
    }
}