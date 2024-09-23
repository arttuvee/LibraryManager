package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewController {

    @FXML
    private Button kotiButton;
    @FXML
    private Button varastoButton;
    @FXML
    private Button lainatButton;
    @FXML
    private Button palautuksetButton;
    @FXML
    private Button laskutButton;
    @FXML
    private Button logoutButton;

    @FXML
    private StackPane mainStackPane;
    @FXML
    private Pane kotiPane;
    @FXML
    private Pane varastoPane;
    @FXML
    private Pane lainatPane;
    @FXML
    private Pane laskutPane;
    @FXML
    private Pane palautuksetPane;

    @FXML
    public void initialize() {
        // Check if the user is an admin and update menu items
        boolean isAdmin = checkUserRole();
        varastoButton.setVisible(isAdmin);
        // Default page
        showPane(kotiPane, kotiButton);
    }

    private boolean checkUserRole() {
        // TODO: Check user role !
        return true;
    }

    @FXML
    private void handleKotiButtonAction() {
        showPane(kotiPane, kotiButton);

    }
    @FXML
    private void handleVarastoButtonAction() {
        showPane(varastoPane, varastoButton);
    }

    @FXML
    private void handleLainatButtonAction() {
        showPane(lainatPane, lainatButton);
    }

    @FXML
    private void handlePalautuksetButtonAction() {
        showPane(palautuksetPane, palautuksetButton);
    }

    @FXML
    private void handleLaskutButtonAction() {
        showPane(laskutPane, laskutButton);
    }

    @FXML
    private void handleLogoutButtonAction() throws IOException {
        // Load the LoginView.fxml file
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));

        // Create a new stage for LoginView
        Stage loginStage = new Stage();
        loginStage.setTitle("LibraryManager - Login");
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();

        // TODO: Logout user !

        // Close the main window
        Stage mainStage = (Stage) logoutButton.getScene().getWindow();
        mainStage.close();
    }

    private void showPane(Pane pane, Button button) {
        mainStackPane.getChildren().forEach(node -> node.setVisible(false));
        pane.setVisible(true);
        resetButtonStyles();
        button.getStyleClass().add("active");

    }

    private void resetButtonStyles() {
        kotiButton.getStyleClass().remove("active");
        varastoButton.getStyleClass().remove("active");
        lainatButton.getStyleClass().remove("active");
        palautuksetButton.getStyleClass().remove("active");
        laskutButton.getStyleClass().remove("active");
    }
}