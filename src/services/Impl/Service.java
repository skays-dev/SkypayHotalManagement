package services.Impl;

import entities.Booking;
import entities.Room;
import entities.User;
import enums.RoomType;
import services.BookingService;
import services.RoomService;
import services.UserService;
import java.util.Date;
import java.util.List;

import static utils.DateUtils.formatDate;

public class Service {
    private final RoomService roomService;
    private final UserService userService;
    private final BookingService bookingService;

    public Service(RoomService roomService, UserService userService, BookingService bookingService) {
        this.roomService = roomService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public void setRoom(int roomNumber, RoomType type, int pricePerNight) {
        Room existing = roomService.getById(roomNumber);

        if (existing == null) {
            roomService.save(new Room(roomNumber, type, pricePerNight));
        } else {
            throw new IllegalArgumentException("Room is already registered with number: " + roomNumber);
        }
    }

    public void setUser(int userId, int balance) {
        User existing = userService.getById(userId);

        if (existing == null) {
            userService.save(new User(userId, balance));
        } else {
            throw new IllegalArgumentException("User is already registered with id: " + userId);
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (!checkIn.before(checkOut))
            throw new IllegalArgumentException("checkIn must be before checkOut.");

        User user = userService.getById(userId);
        if (user == null)
            throw new IllegalArgumentException("User not found.");

        Room room = roomService.getById(roomNumber);
        if (room == null)
            throw new IllegalArgumentException("Room not found.");

        for (Booking b : bookingService.getAll()) {
            if (b.getRoomNumber() == roomNumber) {

                boolean overlaps =
                        checkIn.before(b.getCheckOut()) &&
                                b.getCheckIn().before(checkOut);

                if (overlaps)
                    throw new IllegalArgumentException("Room is already booked for this period.");
            }
        }

        long diff = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        if (diff <= 0)
            throw new IllegalArgumentException("Booking must be at least 1 night.");

        int cost = (int) diff * room.getRoomPricePerNight();

        if (user.getBalance() < cost)
            throw new IllegalArgumentException("User does not have enough balance.");

        user.setBalance(user.getBalance() - cost);
        userService.update(user.getUserId(), user);

        bookingService.save(new Booking(userId, roomNumber, checkIn, checkOut));
    }

    public void printAll() {
        List<Room> rooms = roomService.getAll();
        List<Booking> bookings = bookingService.getAll();

        System.out.println("\n=== Rooms ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room r = rooms.get(i);
            System.out.println("Room number: " + r.getRoomNumber() + ", Room Type: " + r.getRoomType() + ", Price per night: " + r.getRoomPricePerNight());
        }

        System.out.println("\n=== Booking ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            Booking b = bookings.get(i);

            Room room = roomService.getById(bookings.get(i).getRoomNumber());


            long diff = (b.getCheckOut().getTime() - b.getCheckIn().getTime()) / (1000 * 60 * 60 * 24);
            int cost = (int) diff * room.getRoomPricePerNight();

            System.out.println("User: " + b.getUserId() +
                    ", Room number: " + b.getRoomNumber() +
                    ", Price per night: " + room.getRoomPricePerNight() +
                    ", Total amount: " + cost +
                    ", Start: " + formatDate(b.getCheckIn()) +
                    ", End: " + formatDate(b.getCheckOut())

            );
        }
    }

    public void printAllUsers() {
        List<User> users = userService.getAll();

        System.out.println("\n=== Users ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            User u = users.get(i);
            System.out.println("Id: " + u.getUserId() +
                    ", balance: " + u.getBalance());
        }


        System.out.println();
    }
}
