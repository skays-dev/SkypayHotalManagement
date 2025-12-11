package services;

import entities.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int id);

    void save(User user);

    void update(int id, User user);
}
