package controller;

import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final UserController userController;

    @Autowired
    public MainController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userController.getAllUsers();
    }

}