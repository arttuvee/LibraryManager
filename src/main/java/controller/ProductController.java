package controller;

import database.ProductDAO;
import model.Product;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.sql.SQLException;
import java.util.List;

public class ProductController {

    @GetMapping
    public static List<Product> getAllProducts() {
        try {
            return ProductDAO.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public static Product getProductById(int id) {
        try {
            return ProductDAO.getProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{name}")
    public static List<Product> getProductsByName(String name) {
        try {
            return ProductDAO.getProductsByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping
    public static void addProduct(Product product) {
        try {
            ProductDAO.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{product}")
    public static void updateProduct(Product product) {
        try {
            ProductDAO.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public static void deleteProduct(int id) {
        try {
            ProductDAO.deleteProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
