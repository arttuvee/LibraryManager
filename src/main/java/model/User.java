package model;

public class User {
    // Fields
    private int id;
    private String name;
    private String en_name;
    private String fi_name;
    private String ja_name;
    private String ua_name;
    private String email;
    private int age;
    private String role;
    private String password;

    // No-argument constructor
    public User() {
    }

    // Parameterized constructor
    public User(String en_name, String fi_name, String ja_name, String ua_name, String email, String password, int age, String role) {
        this.en_name = en_name;
        this.fi_name = fi_name;
        this.ja_name = ja_name;
        this.ua_name = ua_name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.role = role;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}