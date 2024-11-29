import controller.ViewController;
import database.ProductDAO;
import database.ReservationDAO;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Product;
import model.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ViewControllerTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ReservationDAO reservationDAO;

    @InjectMocks
    private ViewController viewController;

    @Mock
    private Button kotiButton;
    @Mock
    private Button varastoButton;
    @Mock
    private Button lainatButton;
    @Mock
    private Button laskutButton;
    @Mock
    private Button asetuksetButton;
    @Mock
    private Button logoutButton;
    @Mock
    private ChoiceBox<String> languageChoiceBox;
    @Mock
    private StackPane mainStackPane;
    @Mock
    private Pane kotiPane;
    @Mock
    private Pane varastoPane;
    @Mock
    private Pane lainatPane;
    @Mock
    private Pane laskutPane;
    @Mock
    private Pane asetuksetPane;
    @Mock
    private TableView<Product> kirjahyllyTable;
    @Mock
    private TableView<Product> varastoTable;
    @Mock
    private TableView<Reservation> lainaTable;

    @BeforeAll
    public static void initToolkit() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewController.kotiButton = kotiButton;
        viewController.varastoButton = varastoButton;
        viewController.lainatButton = lainatButton;
        viewController.laskutButton = laskutButton;
        viewController.asetuksetButton = asetuksetButton;
        viewController.logoutButton = logoutButton;
        viewController.languageChoiceBox = languageChoiceBox;
        viewController.mainStackPane = mainStackPane;
        viewController.kotiPane = kotiPane;
        viewController.varastoPane = varastoPane;
        viewController.lainatPane = lainatPane;
        viewController.laskutPane = laskutPane;
        viewController.asetuksetPane = asetuksetPane;
        viewController.kirjahyllyTable = kirjahyllyTable;
        viewController.varastoTable = varastoTable;
        viewController.lainaTable = lainaTable;
    }

    @Test
    public void testInitialize() throws Exception {
        when(productDAO.getAllProducts()).thenReturn(List.of(new Product()));
        when(reservationDAO.getReservationsByUserId(anyInt())).thenReturn(List.of(new Reservation()));

        viewController.initialize();

        verify(productDAO, times(1)).getAllProducts();
        verify(reservationDAO, times(1)).getReservationsByUserId(anyInt());
        assertNotNull(viewController.globalProductObservableList);
    }
}