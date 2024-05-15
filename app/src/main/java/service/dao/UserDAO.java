package service.dao;

import models.User;

public interface UserDAO {
    User getByToken(String token);
    User getById(long id);
    User getByLoginAndPassword(String login, String password);
    boolean containsUser(String login);
    void save(User user);
    void update(User user);
    void delete(User user);
} 
