package services.Impl;

import entities.Booking;
import services.BookingService;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public List<Booking> getAll() {
        return bookings;
    }

    @Override
    public void save(Booking booking) {
        bookings.add(booking);
    }
}
