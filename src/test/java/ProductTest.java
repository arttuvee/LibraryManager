import model.Product;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {

    @Test
    public void testProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("en", "Product Name");
        product.setName("fi", "Tuotteen nimi");
        product.setName("ja", "製品名");
        product.setName("uk", "Назва продукту");
        product.setDescription("en", "Product Description");
        product.setDescription("fi", "Tuotteen kuvaus");
        product.setDescription("ja", "製品の説明");
        product.setDescription("uk", "Опис продукту");

        assertEquals(1, product.getId());
        assertEquals("Product Name", product.getName("en"));
        assertEquals("Tuotteen nimi", product.getName("fi"));
        assertEquals("製品名", product.getName("ja"));
        assertEquals("Назва продукту", product.getName("uk"));
        assertEquals("Product Description", product.getDescription("en"));
        assertEquals("Tuotteen kuvaus", product.getDescription("fi"));
        assertEquals("製品の説明", product.getDescription("ja"));
        assertEquals("Опис продукту", product.getDescription("uk"));
    }
}
