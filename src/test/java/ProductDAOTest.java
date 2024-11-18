import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import database.ProductDAO;
import model.Product;

public class ProductDAOTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetAllProducts() throws Exception{
        // Set up the mock result set
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("Product_ID")).thenReturn(1);
        when(mockResultSet.getInt("release_year")).thenReturn(2020);
        when(mockResultSet.getInt("age_limit")).thenReturn(18);
        when(mockResultSet.getInt("saldo")).thenReturn(10);
        when(mockResultSet.getInt("borrow_time")).thenReturn(14);
        when(mockResultSet.getString("code")).thenReturn("P001");
        when(mockResultSet.getString("type")).thenReturn("Book");
        when(mockResultSet.getString("genre")).thenReturn("Fiction");
        when(mockResultSet.getString("author")).thenReturn("Author Name");
        when(mockResultSet.getString("producer")).thenReturn("Producer Name");

        // Mock the static method DatabaseConnection.getConnection()
        mockStatic(DatabaseConnection.class);
        when(DatabaseConnection.getConnection()).thenReturn(mockConnection);

        List<Product> products = ProductDAO.getAllProducts();

        // Verify the results
        assertNotNull(products);
        assertEquals(1, products.size());
        Product product = products.get(0);
        assertEquals(1, product.getId());
        assertEquals(2020, product.getJulkaisuvuosi());
        assertEquals(18, product.getIkaraja());
        assertEquals(10, product.getSaldo());
        assertEquals(14, product.getLainaaika());
        assertEquals("P001", product.getKoodi());
        assertEquals("Book", product.getTyyppi());
        assertEquals("Fiction", product.getGenre());
        assertEquals("Author Name", product.getAuthor());
        assertEquals("Producer Name", product.getProducer());
    }
}