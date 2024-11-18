import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.UserDAO;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @InjectMocks
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testAddUser() throws SQLException {
        String query = "INSERT INTO users (name, email, age, role, password) VALUES (?, ?, ?, ?, ?)";

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDAO.addUser(new User("Matti", "matti.meikalainen@testi.fi", "salasana", 20, "user"));

        verify(mockPreparedStatement).setString(1, "Matti");
        verify(mockPreparedStatement).setString(2, "matti.meikalainen@testi.fi");
        verify(mockPreparedStatement).setInt(3, 20);
        verify(mockPreparedStatement).setString(4, "user");
        verify(mockPreparedStatement).setString(5, "salasana");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteUser() throws SQLException {
        int userId = 1;
        String query = "DELETE FROM users WHERE id = ?";

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDAO.deleteUserById(userId);

        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).executeUpdate();
    }
}