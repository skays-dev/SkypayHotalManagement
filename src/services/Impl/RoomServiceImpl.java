package services.Impl;

import entities.Room;
import entities.User;
import services.RoomService;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    private ArrayList<Room> rooms = new ArrayList<>();


    @Override
    public List<Room> getAll() {
        return rooms;
    }

    @Override
    public Room getById(int roomNumber) {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Room room) {
        rooms.add(room);
    }
}
