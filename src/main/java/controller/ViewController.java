package controller;

import database.ProductDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
    private TextField lisääNimi;
    @FXML
    private TextField lisääJulkaisuPVM;
    @FXML
    private TextField lisääTekijä;
    @FXML
    private TextField lisääJulkaisija;
    @FXML
    private TextField lisääIkäraja;
    @FXML
    private TextField lisääTyyppi;
    @FXML
    private TextField lisääKuvaus;
    @FXML
    private TextField lisääGenre;
    @FXML
    private TextField lisääSaldo;

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> julkaisuPVMColumn;
    @FXML
    private TableColumn<Product, String> tekijaColumn;
    @FXML
    private TableColumn<Product, String> julkaisijaColumn;
    @FXML
    private TableColumn<Product, Integer> ikarajaColumn;
    @FXML
    private TableColumn<Product, String> tyyppiColumn;
    @FXML
    private TableColumn<Product, String> kuvausColumn;
    @FXML
    private TableColumn<Product, String> genreColumn;
    @FXML
    private TableColumn<Product, Integer> saldoColumn;

    @FXML
    public void initialize() {
        System.out.println("Initializing ViewController");
        try {
            boolean isAdmin = checkUserRole();
            System.out.println("User role checked: " + isAdmin);
            varastoButton.setVisible(isAdmin);
            showPane(kotiPane, kotiButton);
            System.out.println("Pane shown: kotiPane");
            setupTableView();
            System.out.println("TableView setup completed");
            loadProductData();
            System.out.println("Product data loaded");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Initialization Error", "An error occurred during initialization: " + e.getMessage());
        }
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
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));
        Stage loginStage = new Stage();
        loginStage.setTitle("LibraryManager - Login");
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
        // TODO: Logout user !
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

    private void setupTableView() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        julkaisuPVMColumn.setCellValueFactory(new PropertyValueFactory<>("julkaisuPVM"));
        tekijaColumn.setCellValueFactory(new PropertyValueFactory<>("tekija"));
        julkaisijaColumn.setCellValueFactory(new PropertyValueFactory<>("julkaisija"));
        ikarajaColumn.setCellValueFactory(new PropertyValueFactory<>("ikaraja"));
        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<>("tyyppi"));
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));
    }

    private void loadProductData() {
        System.out.println("Loading product data");
        try {
            System.out.println("Attempting to retrieve products from database");
            List<Product> products = ProductDAO.getAllProducts();
            System.out.println("Products retrieved: " + products.size());

            ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
            productTableView.setItems(productObservableList);
            System.out.println("Product data set in TableView");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while loading product data: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        // Implement a method to show an alert dialog
    }
}

