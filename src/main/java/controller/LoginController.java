package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserPreferences;
import database.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Text loginTitle;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    private static boolean isAdmin;
    private static int currentUserId;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageChoiceBox.getItems().addAll("English", "Suomi", "Japanese", "Ukrainian");
        String savedLanguage = UserPreferences.getLanguage();
        languageChoiceBox.setValue(savedLanguage);

        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleLanguageChange(newValue);
            UserPreferences.setLanguage(newValue);
        });

        // Delay the language change handling until the UI is fully initialized
        Platform.runLater(() -> handleLanguageChange(savedLanguage));
    }

    private void handleLanguageChange(String language) {
        Locale locale;
        switch (language) {
            case "Suomi":
                locale = new Locale("fi", "FI");
                break;
            case "Japanese":
                locale = new Locale("ja", "JP");
                break;
            case "Ukrainian":
                locale = new Locale("uk", "UA");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("messages", locale);
        setUIText();

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setTitle(bundle.getString("login.title"));
    }

    private void setUIText() {
        loginTitle.setText(bundle.getString("login.title"));
        loginButton.setText(bundle.getString("login.submit"));
        registerButton.setText(bundle.getString("login.register"));
        usernameField.setPromptText(bundle.getString("login.username"));
        passwordField.setPromptText(bundle.getString("login.password"));
    }

    @FXML
    private void handleLoginButtonAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = UserDAO.getUserByName(username);
            if (user != null) {
                String storedPassword = user.getPassword();

                if (password != null && password.equals(storedPassword)) {
                    isAdmin = "admin".equals(user.getRole());
                    currentUserId = user.getId();

                    // Load the MainView.fxml file
                    Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainView.fxml")), bundle);

                    // Create a new stage for MainView
                    Stage mainStage = new Stage();
                    mainStage.setTitle("Library Manager");
                    mainStage.setScene(new Scene(mainRoot));
                    mainStage.show();

                    // Close the login window
                    Stage loginStage = (Stage) loginButton.getScene().getWindow();
                    loginStage.close();
                } else {
                    // Handle invalid login
                    showAlert(bundle.getString("login.error.password"), bundle.getString("login.error.password"));
                }
            } else {
                // Handle invalid login
                showAlert(bundle.getString("login.error.username"), bundle.getString("login.error.username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    @FXML
    private void handleRegisterButtonAction() throws IOException {
        // Load the RegisterView.fxml file
        Parent registerRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RegisterView.fxml")), bundle);

        // Create a new stage for RegisterView
        Stage registerStage = new Stage();
        registerStage.setTitle(bundle.getString("register.title"));
        registerStage.setScene(new Scene(registerRoot));
        registerStage.show();

        // Close the login window
        Stage loginStage = (Stage) registerButton.getScene().getWindow();
        loginStage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}