import controller.TranslateController;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TranslateTest {
    @Test
    public void testTranslateText() {
        TranslateController translateController = new TranslateController();
        String langFrom = "en";
        String langTo = "fi";
        String text = "Hello, how are you doing?";
        String result = null;
        try {
            result = translateController.translateText(langFrom, langTo, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Hei, kuinka voit?", result);
    }
}
