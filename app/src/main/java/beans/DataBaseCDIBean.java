package beans;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.UPRecord;
import models.User;
import service.UPRecordService;
import service.UserService;

@Named("dataBase")
@ApplicationScoped
public class DataBaseCDIBean implements Serializable {
    private UserService uService = new UserService();
    private UPRecordService rService = new UPRecordService();
    public void saveUser(User user){
        uService.saveUser(user);
    }
    public void saveRecord(UPRecord record){
        rService.saveRecord(record);
    }
    public User getUser(String login, String password){
        return uService.getUserByLoginAndPassword(login, password);
    }
    public User getUser(String token){
        return uService.getUserByToken(token);
    }
    public boolean containsUser(String login){
        return uService.containsUser(login);
    }
    public void updateUser(User user){
        uService.updateUser(user);
    }
}
