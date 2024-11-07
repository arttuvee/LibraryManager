package view;

import app.MainApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import model.UserPreferences;
import java.util.prefs.Preferences;

@SpringBootApplication(scanBasePackages = { "controller" })
public class MainUI extends Application {

    public static void main(String[] args) {
        //SpringApplication.run(MainApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            String savedLanguage = UserPreferences.getLanguage();
            Locale locale;
            switch (savedLanguage) {
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
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")), bundle);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(bundle.getString("login.title"));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}