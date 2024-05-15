package normal_beans;

import java.io.Serializable;

import beans.DataBaseCDIBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.User;

@Named("registration")
@RequestScoped
public class RegistrationBean implements Serializable {
    @Inject
    DataBaseCDIBean dataBase;

    String name;
    String login;
    String password;

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

    public String signUp(){
        User newUser = new User(name, login, password);
        System.out.println("Sign up");
        dataBase.saveUser(newUser);
        return "redirect";
    }
}
