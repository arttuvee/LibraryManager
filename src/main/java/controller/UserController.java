package controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import database.UserDAO;
import model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public static List<User> getAllUsers() {
        try {
            return UserDAO.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public static User getUserById(@PathVariable int id) {
        try {
            return UserDAO.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
