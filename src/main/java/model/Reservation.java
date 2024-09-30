package model;

import java.sql.Date;

public class Reservation {
    private int id;
    private Date endDate;
    private double fine;
    private boolean returned;
    private int userId;
    private int productId;

    //No-argument constructor
    public Reservation() {
    }

    //Parameterized constructor
    public Reservation(Date endDate, double fine, boolean returned, int userId, int productId) {
        this.endDate = endDate;
        this.fine = fine;
        this.returned = returned;
        this.userId = userId;
        this.productId = productId;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
