import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        User user = new User();

        user.setId(1);
        user.setName("Matti");
        user.setEmail("matti.meikalainen@testi.fi");
        user.setPassword("salasana");
        user.setAge(20);
        user.setRole("user");

        assertEquals(1, user.getId());
        assertEquals("Matti", user.getName());
        assertEquals("matti.meikalainen@testi.fi", user.getEmail());
        assertEquals("salasana", user.getPassword());
        assertEquals(20, user.getAge());
        assertEquals("user", user.getRole());
    }

    @Test
    public void testUserParameterizedConstructor() {
        User user = new User("Matti", "matti.meikalainen@testi.fi", "salasana", 20, "user");

        assertEquals("Matti", user.getName());
        assertEquals("matti.meikalainen@testi.fi", user.getEmail());
        assertEquals("salasana", user.getPassword());
        assertEquals(20, user.getAge());
        assertEquals("user", user.getRole());
    }
}
