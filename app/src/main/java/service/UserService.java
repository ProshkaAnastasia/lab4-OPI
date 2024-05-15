package service;

import models.User;
import service.dao.UserDAO;
import service.dao.UserDaoPostgres;

public class UserService {
    private UserDAO userDAO = new UserDaoPostgres();
    public User getUserByToken(String token){
        return userDAO.getByToken(token);
    }
    public User getUserById(long id){
        return userDAO.getById(id);
    }
    public User getUserByLoginAndPassword(String login, String password){
        return userDAO.getByLoginAndPassword(login, password);
    }

    public boolean containsUser(String login){
        return userDAO.containsUser(login);
    }
    
    public void saveUser(User user){
        userDAO.save(user);
    }
    public void updateUser(User user){
        userDAO.update(user);
    }
    public void removeUser(User user){
        userDAO.delete(user);
    }
}
