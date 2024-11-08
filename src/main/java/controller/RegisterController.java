package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

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

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        backButton.setText(bundle.getString("register.back"));
        registerButton.setText(bundle.getString("register.submit"));
        nameField.setPromptText(bundle.getString("register.username"));
        emailField.setPromptText(bundle.getString("register.email"));
        passwordField.setPromptText(bundle.getString("register.password"));
        ageField.setPromptText(bundle.getString("register.age"));
    }

    @FXML
    private void handleBackButton() throws IOException {
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")), bundle);
        Stage loginStage = new Stage();
        loginStage.setTitle(bundle.getString("login.title"));
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
        Stage registerStage = (Stage) backButton.getScene().getWindow();
        registerStage.close();
    }

    // TODO: Localize error messages
    private boolean validateFields() {
        if (isFieldEmpty(passwordField.getText(), bundle.getString("register.error.password")) ||
                isFieldEmpty(nameField.getText(), bundle.getString("register.error.username")) ||
                isFieldEmpty(emailField.getText(), bundle.getString("register.error.email"))) {
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(String field, String errorMessage) {
        if (field == null || field.isEmpty()) {
            showAlert(bundle.getString("error.title"), errorMessage);
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
        String role = "user";

        User user = new User(name, email, password, age, role);
        try {
            UserDAO.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")), bundle);
        Stage mainStage = new Stage();
        mainStage.setTitle(bundle.getString("login.title"));
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