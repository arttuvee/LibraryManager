import controller.LoginController;
import database.UserDAO;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
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
public class LoginControllerTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private TextField usernameField;

    @Mock
    private PasswordField passwordField;

    @Mock
    private Button loginButton;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController.usernameField = usernameField;
        loginController.passwordField = passwordField;
        loginController.loginButton = loginButton;
    }

    @Test
    public void testHandleLoginButtonAction_Success() throws Exception {
        when(usernameField.getText()).thenReturn("testUser");
        when(passwordField.getText()).thenReturn("testPassword");

        User user = new User();
        user.setId(1);
        user.setPassword("testPassword");
        user.setRole("user");

        when(userDAO.getUserByName("testUser")).thenReturn(user);

        loginController.handleLoginButtonAction();

        assertEquals(1, LoginController.getCurrentUserId());
        assertFalse(LoginController.isAdmin());
    }

    @Test
    public void testHandleLoginButtonAction_InvalidPassword() throws Exception {
        when(usernameField.getText()).thenReturn("testUser");
        when(passwordField.getText()).thenReturn("wrongPassword");

        User user = new User();
        user.setPassword("testPassword");

        when(userDAO.getUserByName("testUser")).thenReturn(user);

        loginController.handleLoginButtonAction();

        // Verify that the login window is not closed
        verify(loginButton.getScene().getWindow(), never()).hide();
    }

    @Test
    public void testHandleLoginButtonAction_UserNotFound() throws Exception {
        when(usernameField.getText()).thenReturn("unknownUser");
        when(passwordField.getText()).thenReturn("anyPassword");

        when(userDAO.getUserByName("unknownUser")).thenReturn(null);

        loginController.handleLoginButtonAction();

        // Verify that the login window is not closed
        verify(loginButton.getScene().getWindow(), never()).hide();
    }
}