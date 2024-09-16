package controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/id/{id}")
    public static User getUserById(@PathVariable("id") int id) {
        try {
            return UserDAO.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/name/{name}")
    public static User getUserByName(@PathVariable("name") String name) {
        try {
            return UserDAO.getUserByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping
    public static void addUser(@RequestBody User user) {
        try {
            UserDAO.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{user}")
    public static void updateUser(@RequestBody User user) {
        try {
            UserDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public static void deleteUserById(@PathVariable("id") int id) {
        try {
            UserDAO.deleteUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
