package services;

import entities.Booking;

import java.util.Date;
import java.util.List;

public interface BookingService {
    List<Booking> getAll();

    void save(Booking booking);
}
