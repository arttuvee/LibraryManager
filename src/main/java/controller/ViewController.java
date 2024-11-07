package controller;

import database.ProductDAO;
import database.ReservationDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Product;
import model.Reservation;
import model.UserPreferences;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewController {

    // Menu buttons
    @FXML
    private Button kotiButton;
    @FXML
    private Button varastoButton;
    @FXML
    private Button lainatButton;
    @FXML
    private Button laskutButton;
    @FXML
    private Button asetuksetButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ChoiceBox<String> languageChoiceBox;

    // Pages
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
    private Pane asetuksetPane;

    // Add product fields
    @FXML
    private TextField lisääNimi;
    @FXML
    private TextField lisääJulkaisuvuosi;
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
    private TextField lisääKoodi;

    // Kirjahylly TableView
    @FXML
    private TableView<Product> kirjahyllyTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> julkaisuColumn;
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

    // Varasto TableView
    @FXML
    private TableView<Product> varastoTable;
    @FXML
    private TableColumn<Product, Integer> idColumnVarasto;
    @FXML
    private TableColumn<Product, String> nimiColumnVarasto;
    @FXML
    private TableColumn<Product, String> julkaisuColumnVarasto;
    @FXML
    private TableColumn<Product, String> tekijäColumnVarasto;
    @FXML
    private TableColumn<Product, String> julkaisijaColumnVarasto;
    @FXML
    private TableColumn<Product, Integer> ikärajaColumnVarasto;
    @FXML
    private TableColumn<Product, String> tyyppiColumnVarasto;
    @FXML
    private TableColumn<Product, String> kuvausColumnVarasto;
    @FXML
    private TableColumn<Product, String> genreColumnVarasto;
    @FXML
    private TableColumn<Product, Integer> saldoColumnVarasto;

    // Lainat tableview
    @FXML
    private TableView<Reservation> lainaTable;
    @FXML
    private TableColumn<Reservation, String> nimiColumnLainat;
    @FXML
    private TableColumn<Reservation, String> tekijaColumnLainat;
    @FXML
    private TableColumn<Reservation, String> tyyppiColumnLainat;
    @FXML
    private TableColumn<Reservation, String> lainaaikaColumnLainat;

    @FXML
    public void initialize() {
        System.out.println("Initializing ViewController");
        try {
            boolean isAdmin = LoginController.isAdmin();
            System.out.println("User role checked: " + isAdmin);
            varastoButton.setVisible(isAdmin);
            showPane(kotiPane, kotiButton);
            setupTableView();
            loadProductData();
            loadReservationData();
            setupRowFactory();

            // Setup language choice box
            languageChoiceBox.getItems().addAll("English", "Suomi", "Japanese");
            String savedLanguage = UserPreferences.getLanguage();
            languageChoiceBox.setValue(savedLanguage);

            // Delay the language change handling until the UI is fully initialized
            Platform.runLater(() -> {
                handleLanguageChange(savedLanguage);
                languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    handleLanguageChange(newValue);
                });
            });

            System.out.println("Product data loaded");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Virhe", "Tapahtui virhe: " + e.getMessage());
        }
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
            default:
                locale = new Locale("en", "US");
                break;
        }
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        kotiButton.setText(bundle.getString("menu.bookself"));
        varastoButton.setText(bundle.getString("menu.storage"));
        lainatButton.setText(bundle.getString("menu.loans"));
        laskutButton.setText(bundle.getString("menu.bills"));
        asetuksetButton.setText(bundle.getString("menu.settings"));
        logoutButton.setText(bundle.getString("menu.logout"));

        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        stage.setTitle("LibraryManager");
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
    private void handleLaskutButtonAction() {
        showPane(laskutPane, laskutButton);
    }
    @FXML
    private void handleAsetuksetButtonAction() {
        showPane(asetuksetPane, asetuksetButton);
    }

    @FXML
    private void handleLogoutButtonAction() throws IOException {
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));
        Stage loginStage = new Stage();
        loginStage.setTitle("login.title");
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
        laskutButton.getStyleClass().remove("active");
    }

    private void setupTableView() {
        // Setup columns for kirjahyllyTable
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        julkaisuColumn.setCellValueFactory(new PropertyValueFactory<>("julkaisuvuosi"));
        tekijaColumn.setCellValueFactory(new PropertyValueFactory<>("tekija"));
        julkaisijaColumn.setCellValueFactory(new PropertyValueFactory<>("julkaisija"));
        ikarajaColumn.setCellValueFactory(new PropertyValueFactory<>("ikaraja"));
        tyyppiColumn.setCellValueFactory(new PropertyValueFactory<>("tyyppi"));
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        // Setup columns for varastoTable
        idColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("id"));
        nimiColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        julkaisuColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("julkaisuvuosi"));
        tekijäColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("tekija"));
        julkaisijaColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("julkaisija"));
        ikärajaColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("ikaraja"));
        tyyppiColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("tyyppi"));
        kuvausColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));
        genreColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("genre"));
        saldoColumnVarasto.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        // Setup columns for lainaTable
        nimiColumnLainat.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tekijaColumnLainat.setCellValueFactory(new PropertyValueFactory<>("author"));
        tyyppiColumnLainat.setCellValueFactory(new PropertyValueFactory<>("type"));
        lainaaikaColumnLainat.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private void loadProductData() {
        System.out.println("Loading product data");
        try {
            System.out.println("Attempting to retrieve products from database");
            List<Product> products = ProductDAO.getAllProducts();
            System.out.println("Products retrieved: " + products.size());

            ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
            kirjahyllyTable.setItems(productObservableList);
            varastoTable.setItems(productObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Tietokantavirhe", "Tuotetietojen lataaminen epäonnistui: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Virhe", "Tapahtui virhe: " + e.getMessage());
        }
    }

    private void loadReservationData() {
        try {
            int userId = LoginController.getCurrentUserId(); // Get the current user ID
            List<Reservation> reservations = ReservationDAO.getReservationsByUserId(userId);
            ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList(reservations);
            lainaTable.setItems(reservationObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Tietokantavirhe", "Lainatietojen lataaminen epäonnistui: " + e.getMessage());
        }
    }

    private void setupRowFactory() {
        lainaTable.setRowFactory(new Callback<TableView<Reservation>, TableRow<Reservation>>() {
            @Override
            public TableRow<Reservation> call(TableView<Reservation> tableView) {
                return new TableRow<Reservation>() {
                    @Override
                    protected void updateItem(Reservation reservation, boolean empty) {
                        super.updateItem(reservation, empty);
                        if (reservation != null && reservation.isReturned()) {
                            setStyle("-fx-background-color: rgba(0,155,0,0.6);");
                        } else {
                            setStyle("");
                        }
                    }
                };
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAddButtonAction() {
        String nimi = lisääNimi.getText();
        String julkaisuvuosiStr = lisääJulkaisuvuosi.getText();
        String tekijä = lisääTekijä.getText();
        String julkaisija = lisääJulkaisija.getText();
        String ikärajaStr = lisääIkäraja.getText();
        String tyyppi = lisääTyyppi.getText();
        String kuvaus = lisääKuvaus.getText();
        String genre = lisääGenre.getText();
        String saldoStr = lisääSaldo.getText();
        String koodi = lisääKoodi.getText();

        int julkaisuvuosi;
        int ikäraja;
        int saldo;
        try {
            julkaisuvuosi = Integer.parseInt(julkaisuvuosiStr);
            ikäraja = Integer.parseInt(ikärajaStr);
            saldo = Integer.parseInt(saldoStr);

            Product product = new Product(nimi, julkaisuvuosi, tekijä, julkaisija, ikäraja, tyyppi, kuvaus, genre, saldo, koodi);
            ProductDAO.addProduct(product);
            showAlert("Tuotteen lisäys onnistui", "Tuote lisätty onnistuneesti!");
            loadProductData();
        } catch (NumberFormatException e) {
            showAlert("Virhe", "Virheellinen syöte: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Tietokantavirhe", "Tuotteen lisääminen epäonnistui: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveButtonAction() {
        Product selectedProduct = varastoTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                ProductDAO.deleteProduct(selectedProduct.getId());
                showAlert("Tuotteen poisto onnistui", "Tuote poistettu onnistuneesti!");
                loadProductData();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Tietokantavirhe", "Tuotteen poistaminen epäonnistui: " + e.getMessage());
            }
        } else {
            showAlert("Virhe", "Ei tuotetta valittuna. Valitse listasta tuote, jonka haluat poistaa.");
        }
    }

    @FXML
    private void handleLainaaButtonAction() {
        Product selectedProduct = kirjahyllyTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                int userId = LoginController.getCurrentUserId(); // Get the current user ID
                Reservation reservation = new Reservation(Date.valueOf(LocalDate.now()), 0.0, false, userId, selectedProduct.getId());
                ReservationDAO.addReservation(reservation);
                showAlert("Lainaus onnistui", "Tuote lainattu onnistuneesti!");
                loadReservationData();
                loadProductData();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Tietokantavirhe", "Tuotteen lainaaminen epäonnistui: " + e.getMessage());
            }
        } else {
            showAlert("Virhe", "Ei tuotetta valittuna. Valitse listasta tuote, jonka haluat lainata.");
        }
    }

    @FXML
    private void handlePalautaButtonAction() {
        Reservation selectedReservation = lainaTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                ReservationDAO.returnReservation(selectedReservation.getId());
                selectedReservation.setReturned(true);
                lainaTable.refresh();
                loadProductData();
                showAlert("Palautus onnistui", "Laina palautettu onnistuneesti!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Tietokantavirhe", "Lainan palauttaminen epäonnistui: " + e.getMessage());
            }
        } else {
            showAlert("Virhe", "Ei lainaa valittuna. Valitse listasta laina, jonka haluat palauttaa.");
        }
    }
}