package controller;


import database.ReservationDAO;
import model.Reservation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {


    @GetMapping("/user/{id}")
    public static List<Reservation> getReservationsByUserId(@PathVariable("id")int id) {
        try {
            return ReservationDAO.getReservationsByUserId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/return/{id}")
    public static void returnReservation(@PathVariable("id")int id) {
        try {
            ReservationDAO.returnReservation(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
