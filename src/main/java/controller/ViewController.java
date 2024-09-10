package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ViewController {

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
    private Pane varastoPane;
    @FXML
    private Pane lainatPane;
    @FXML
    private Pane laskutPane;
    @FXML
    private Pane palautuksetPane;

    @FXML
    public void initialize() {
        // Check if the user is an admin and set the visibility of the varastoButton
        boolean isAdmin = checkUserRole();
        varastoButton.setVisible(isAdmin);
        // Default page
        showPane(varastoPane, varastoButton);
    }

    private boolean checkUserRole() {
        // TODO: Check user role
        return true;
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

    private void showPane(Pane pane, Button button) {
        mainStackPane.getChildren().forEach(node -> node.setVisible(false));
        pane.setVisible(true);
        resetButtonStyles();
        button.getStyleClass().add("active");

    }

    private void resetButtonStyles() {
        varastoButton.getStyleClass().remove("active");
        lainatButton.getStyleClass().remove("active");
        palautuksetButton.getStyleClass().remove("active");
        laskutButton.getStyleClass().remove("active");
    }

    private void handleLogoutButtonAction() {

    }
}