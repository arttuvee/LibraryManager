package controller;

import database.ProductDAO;
import database.ReservationDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.text.Text;
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
    public Button kotiButton;
    @FXML
    public Button varastoButton;
    @FXML
    public Button lainatButton;
    @FXML
    public Button laskutButton;
    @FXML
    public Button asetuksetButton;
    @FXML
    public Button logoutButton;
    @FXML
    public ChoiceBox<String> languageChoiceBox;

    // Pages
    @FXML
    public StackPane mainStackPane;
    @FXML
    public Pane kotiPane;
    @FXML
    public Pane varastoPane;
    @FXML
    public Pane lainatPane;
    @FXML
    public Pane laskutPane;
    @FXML
    public Pane asetuksetPane;

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

    // Titles
    @FXML
    private Text storageTitle;
    @FXML
    private Text loansTitle;
    @FXML
    private Text billsTitle;
    @FXML
    private Text settingsTitle;
    @FXML
    private Text bookshelfTitle;

    // Kirjahylly TableView
    @FXML
    public TableView<Product> kirjahyllyTable;
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
    public TableView<Product> varastoTable;
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
    public TableView<Reservation> lainaTable;
    @FXML
    private TableColumn<Reservation, String> nimiColumnLainat;
    @FXML
    private TableColumn<Reservation, String> tekijaColumnLainat;
    @FXML
    private TableColumn<Reservation, String> tyyppiColumnLainat;
    @FXML
    private TableColumn<Reservation, String> lainaaikaColumnLainat;

    // Laskut tableview
    @FXML
    private TableColumn<Reservation, String> billsColName;
    @FXML
    private TableColumn<Reservation, String> billsColReason;
    @FXML
    private TableColumn<Reservation, String> billsColAmount;



    @FXML
    public void initialize() {
        System.out.println("Initializing ViewController");
        try {
            boolean isAdmin = LoginController.isAdmin();
            System.out.println("User role checked: " + isAdmin);
            varastoButton.setVisible(isAdmin);
            showPane(kotiPane, kotiButton);
            loadProductData();
            setupTableView();
            loadReservationData();
            setupRowFactory();

            // Setup language choice box
            languageChoiceBox.getItems().addAll("English", "Suomi", "Japanese", "Ukrainian");
            String savedLanguage = UserPreferences.getLanguage();
            languageChoiceBox.setValue(savedLanguage);

            // Delay the language change handling until the UI is fully initialized
            Platform.runLater(() -> {
                handleLanguageChange(savedLanguage);
                languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    handleLanguageChange(newValue);
                    UserPreferences.setLanguage(newValue);
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
            case "Ukrainian":
                locale = new Locale("uk", "UA");
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

        bookshelfTitle.setText(bundle.getString("bookshelf.title"));
        storageTitle.setText(bundle.getString("storage.title"));
        loansTitle.setText(bundle.getString("loans.title"));
        billsTitle.setText(bundle.getString("bills.title"));
        settingsTitle.setText(bundle.getString("settings.title"));

        lisääNimi.setPromptText(bundle.getString("storage.add.name"));
        lisääJulkaisuvuosi.setPromptText(bundle.getString("storage.add.year"));
        lisääTekijä.setPromptText(bundle.getString("storage.add.author"));
        lisääJulkaisija.setPromptText(bundle.getString("storage.publisher"));
        lisääIkäraja.setPromptText(bundle.getString("storage.add.age"));
        lisääTyyppi.setPromptText(bundle.getString("storage.add.type"));
        lisääKuvaus.setPromptText(bundle.getString("storage.add.description"));
        lisääGenre.setPromptText(bundle.getString("storage.add.genre"));
        lisääSaldo.setPromptText(bundle.getString("storage.add.saldo"));

        billsColName.setText(bundle.getString("storage.add.name"));
        billsColReason.setText(bundle.getString("bills.reason"));
        billsColAmount.setText(bundle.getString("bills.amount"));

        nameColumn.setText(bundle.getString("bookshelf.title"));
        julkaisuColumn.setText(bundle.getString("bookshelf.year"));
        tekijaColumn.setText(bundle.getString("bookshelf.author"));
        julkaisijaColumn.setText(bundle.getString("bookshelf.publisher"));
        ikarajaColumn.setText(bundle.getString("bookshelf.age"));
        tyyppiColumn.setText(bundle.getString("bookshelf.type"));
        kuvausColumn.setText(bundle.getString("bookshelf.description"));
        genreColumn.setText(bundle.getString("bookshelf.genre"));
        saldoColumn.setText(bundle.getString("bookshelf.saldo"));

        nimiColumnVarasto.setText(bundle.getString("bookshelf.title"));
        julkaisuColumnVarasto.setText(bundle.getString("bookshelf.year"));
        tekijäColumnVarasto.setText(bundle.getString("bookshelf.author"));
        julkaisijaColumnVarasto.setText(bundle.getString("bookshelf.publisher"));
        ikärajaColumnVarasto.setText(bundle.getString("bookshelf.age"));
        tyyppiColumnVarasto.setText(bundle.getString("bookshelf.type"));
        kuvausColumnVarasto.setText(bundle.getString("bookshelf.description"));
        genreColumnVarasto.setText(bundle.getString("bookshelf.genre"));
        saldoColumnVarasto.setText(bundle.getString("bookshelf.saldo"));

        nimiColumnLainat.setText(bundle.getString("loans.title"));
        tekijaColumnLainat.setText(bundle.getString("bookshelf.author"));
        tyyppiColumnLainat.setText(bundle.getString("bookshelf.type"));
        lainaaikaColumnLainat.setText(bundle.getString("loans.time"));

        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        stage.setTitle("Library Manager");

        // Muista vaihtaa myös tuotteiden arvot taulukoissa
        setupTableView();

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
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")), bundle);
        Stage loginStage = new Stage();
        loginStage.setTitle(bundle.getString("login.title"));
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
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
        asetuksetButton.getStyleClass().remove("active");
    }

    private void setupTableView() {

        // Get current language
        String languagecode = switch (languageChoiceBox.getValue()){
            case "Suomi" -> "fi";
            case "Japanese" -> "ja";
            case "Ukrainian" -> "uk";
            default -> "en";
        };


        // Setup columns for kirjahyllyTable
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName(languagecode)));
        julkaisuColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getJulkaisuvuosi())));
        tekijaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        julkaisijaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProducer()));
        ikarajaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIkaraja()).asObject());
        tyyppiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTyyppi()));
        kuvausColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKuvaus(languagecode)));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        saldoColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSaldo()).asObject());

        kirjahyllyTable.refresh();


        // Setup columns for varastoTable
        idColumnVarasto.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nimiColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName(languagecode)));
        julkaisuColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getJulkaisuvuosi())));
        tekijäColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        julkaisijaColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProducer()));
        ikärajaColumnVarasto.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIkaraja()).asObject());
        tyyppiColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTyyppi()));
        kuvausColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKuvaus(languagecode)));
        genreColumnVarasto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        saldoColumnVarasto.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSaldo()).asObject());

        varastoTable.refresh();

        // Setup columns for lainaTable
        nimiColumnLainat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        tekijaColumnLainat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        tyyppiColumnLainat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        lainaaikaColumnLainat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toString()));

        lainaTable.refresh();
    }

    public ObservableList<Product> globalProductObservableList;

    private void loadProductData() {
        System.out.println("Loading product data");
        try {
            System.out.println("Attempting to retrieve products from database");
            List<Product> products = ProductDAO.getAllProducts();
            System.out.println("Products retrieved: " + products.size());

            ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
            this.globalProductObservableList = productObservableList;
            kirjahyllyTable.setItems(productObservableList);
            varastoTable.setItems(productObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error", "Retrieving product data failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error: " + e.getMessage());
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
            showAlert("Database error", "Retrieving loan data failed: " + e.getMessage());
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

            // calculate the borrow time based on the type of the product
            int borrowTime = Objects.equals(tyyppi, "Kirja") || Objects.equals(tyyppi, "kirja") ? 28 : 14;

            Product product = new Product(julkaisuvuosi, ikäraja, saldo, borrowTime, koodi, tyyppi, genre, tekijä, julkaisija);
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