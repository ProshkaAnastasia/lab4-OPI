package normal_beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import beans.DataBaseCDIBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.UPRecord;
import models.User;

@Named("user")
@SessionScoped
public class UserBean implements Serializable {
    User user;
    @Inject
    DataBaseCDIBean dataBase;
    @Inject
    PointCollectionCDIBean points;

    public boolean authorized(){
        return user != null;
    }

    public User getUser(){
        return user;
    }

    public List<UPRecord> getUserPoints() {
        return user.getRecords();
    }

    @PostConstruct
    public void loadUser(){
        System.out.println("Loading user");
        FacesContext ctx = FacesContext.getCurrentInstance();
        var cookies =  ctx.getExternalContext().getRequestCookieMap();
        if (cookies.get("token") != null){
            user = dataBase.getUser(cookies.get("token").toString());
            if (user == null){
                ctx.getExternalContext().getRequestCookieMap().remove("token");
                return;
            }
            System.out.println(points);
            points.setPoints(user.getRecords());
        } else {
            points.setPoints(new ArrayList<UPRecord>());
        }
        //cookies.forEach((key, value) -> System.out.println(key + " " + value));
    }

    @PreDestroy
    public void saveChanges(){
        if (authorized()){
            dataBase.updateUser(user);
        }
    }

    public void changeUser(User user){
        this.user = user;
        if (user != null){
            user.getRecords().addAll(0, points.getPoints());
            points.setPoints(user.getRecords());
            //points.addPoints(user.getRecords());
            //System.out.println(user.getName());
            //System.out.println(user.getLogin());
        } else {
            points.setPoints(new ArrayList<>());
        }
    }

    public String quit() {
        System.out.println("Exit");
        if (authorized()){
            user.getRecords().stream().forEach((element) -> {
                element.setUser(this.user);
            });
            dataBase.updateUser(user);
        }
        changeUser(null);
        return "redirect";
    }
    
}
