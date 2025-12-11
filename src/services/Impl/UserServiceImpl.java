package services.Impl;

import entities.User;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User getById(int id) {
        return users.stream()
                .filter(user -> user.getUserId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(int id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                users.set(i, updatedUser);
                return;
            }
        }
    }
}
