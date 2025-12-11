
import enums.RoomType;
import services.BookingService;
import services.Impl.Service;
import services.RoomService;
import services.UserService;
import services.Impl.BookingServiceImpl;
import services.Impl.RoomServiceImpl;
import services.Impl.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        RoomService roomService = new RoomServiceImpl();
        UserService userService = new UserServiceImpl();
        BookingService bookingService = new BookingServiceImpl();

        Service service = new Service(roomService, userService, bookingService);


        // create static rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // create static users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // create static bookings
        try {
            service.bookRoom(1, 2, DF.parse("30/06/2026"), DF.parse("07/07/2026"));
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        try {
            service.bookRoom(1, 2, DF.parse("07/07/2026"), DF.parse("30/06/2026"));
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        try {
            service.bookRoom(1, 1, DF.parse("07/07/2026"), DF.parse("08/07/2026"));
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        try {
            service.bookRoom(2, 1, DF.parse("07/07/2026"), DF.parse("09/07/2026"));
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        try {
            service.bookRoom(2, 3, DF.parse("07/07/2026"), DF.parse("08/07/2026"));
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        // create static room
        try {
            service.setRoom(1, RoomType.SUITE, 10000);
        } catch (IllegalArgumentException e) {
            System.out.println("Room error: " + e.getMessage());
        }




        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("============== HOTEL MENU ==============");
            System.out.println("1. Add Room");
            System.out.println("2. Add User");
            System.out.println("3. Book Room");
            System.out.println("4. Print All (Rooms + Bookings)");
            System.out.println("5. Print All Users");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String op = sc.nextLine();

            try {
                switch (op) {

                    case "1":
                        System.out.print("Room number: ");
                        int rn = Integer.parseInt(sc.nextLine());

                        System.out.print("Room type (STANDARD/JUNIOR/SUITE): ");
                        RoomType rt = RoomType.valueOf(sc.nextLine().toUpperCase());

                        System.out.print("Price per night: ");
                        int price = Integer.parseInt(sc.nextLine());

                        service.setRoom(rn, rt, price);
                        System.out.println("Room saved");
                        break;

                    case "2":
                        System.out.print("UserId: ");
                        int uid = Integer.parseInt(sc.nextLine());

                        System.out.print("Balance: ");
                        int bal = Integer.parseInt(sc.nextLine());

                        service.setUser(uid, bal);
                        System.out.println("User saved");
                        break;

                    case "3":
                        System.out.print("UserId: ");
                        int buid = Integer.parseInt(sc.nextLine());

                        System.out.print("Room number: ");
                        int brn = Integer.parseInt(sc.nextLine());

                        System.out.print("Check-in (dd/MM/yyyy): ");
                        Date in = DF.parse(sc.nextLine());

                        System.out.print("Check-out (dd/MM/yyyy): ");
                        Date out = DF.parse(sc.nextLine());

                        service.bookRoom(buid, brn, in, out);
                        System.out.println("Booking success");
                        break;

                    case "4":
                        service.printAll();
                        break;

                    case "5":
                        service.printAllUsers();
                        break;

                    case "0":
                        System.out.println("Goodbye");
                        return;

                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }
}