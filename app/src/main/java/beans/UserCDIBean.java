package beans;
import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.User;

@Named("userBean")
@SessionScoped
public class UserCDIBean implements Serializable{

    @Inject
    private DataBaseCDIBean dataBase;

    private String name;
    private String login;
    private String password;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String authorize(){
        System.out.println("login");
        return "redirect";
    }
    public String register(){
        System.out.println("register method");
        System.out.println(login);
        User user = new User(name, login, password);
        user.setToken(createToken());
        dataBase.saveUser(user);
        name = "";
        login = "";
        password = "";
        return "success";
    }
    private String createToken(){
        return "";
    }
}
