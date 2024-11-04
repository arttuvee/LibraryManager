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

@SpringBootApplication(scanBasePackages = { "controller" })
public class MainUI extends Application {

    public static void main(String[] args) {
        //SpringApplication.run(MainApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Locale.setDefault(new Locale("ja", "JP"));
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