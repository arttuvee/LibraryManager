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

    public void setFi_name(String fi) {
        this.fi_name = fi;
    }

    public void setEn_name(String en) {
        this.en_name = en;
    }

    public void setJa_name(String ja) {
        this.ja_name = ja;
    }

    public void setUa_name(String ua) {
        this.ua_name = ua;
    }

    public String getFi_name() {
        return fi_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getJa_name() {
        return ja_name;
    }

    public String getUa_name() {
        return ua_name;
    }
}