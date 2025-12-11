package services;

import entities.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAll();

    Room getById(int roomNumber);

    void save(Room room);

}
