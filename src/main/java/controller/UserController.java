package controller;

import database.UserDAO;
import model.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

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
    public static User getUserById(int id) {
        try {
            return UserDAO.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
