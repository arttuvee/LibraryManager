package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        // Main loop, listens for web requests
        SpringApplication.run(MainApplication.class, args);
    }
}
