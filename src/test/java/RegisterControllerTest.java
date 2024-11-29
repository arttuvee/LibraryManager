import controller.RegisterController;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private Button backButton;

    @Mock
    private Button registerButton;

    @Mock
    private TextField nameField;

    @Mock
    private TextField emailField;

    @Mock
    private PasswordField passwordField;

    @Mock
    private TextField ageField;

    @Mock
    private ResourceBundle bundle;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        setPrivateField(registerController, "backButton", backButton);
        setPrivateField(registerController, "registerButton", registerButton);
        setPrivateField(registerController, "nameField", nameField);
        setPrivateField(registerController, "emailField", emailField);
        setPrivateField(registerController, "passwordField", passwordField);
        setPrivateField(registerController, "ageField", ageField);
        setPrivateField(registerController, "bundle", bundle);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void invokePrivateMethod(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(target);
    }

    @Test
    public void testHandleRegisterButtonAction_Success() throws Exception {
        when(nameField.getText()).thenReturn("testUser");
        when(emailField.getText()).thenReturn("test@example.com");
        when(passwordField.getText()).thenReturn("testPassword");
        when(ageField.getText()).thenReturn("25");

        User user = new User("testUser", "test@example.com", "testPassword", 25, "user");

        doNothing().when(userDAO).addUser(user);

        invokePrivateMethod(registerController, "handleRegisterButtonAction");

        verify(userDAO, times(1)).addUser(user);
    }

    @Test
    public void testHandleRegisterButtonAction_InvalidFields() throws Exception {
        when(nameField.getText()).thenReturn("");
        when(emailField.getText()).thenReturn("test@example.com");
        when(passwordField.getText()).thenReturn("testPassword");
        when(ageField.getText()).thenReturn("25");

        invokePrivateMethod(registerController, "handleRegisterButtonAction");

        verify(userDAO, never()).addUser(any(User.class));
    }
}