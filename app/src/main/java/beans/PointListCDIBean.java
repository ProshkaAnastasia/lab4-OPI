package beans;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import java.util.ArrayList;

import classes.Point;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbeans.CountHits;
import mbeans.SuccessPercentage;
import models.UPRecord;
import models.User;

@Named("pointList")
@SessionScoped
public class PointListCDIBean implements Serializable {

    @Inject
    private DataBaseCDIBean dataBase;

    private User user;
    private List<UPRecord> points;

    private String x = "";
    private String y = "";
    private String r = "";

    private CountHits hitsCount;
    private SuccessPercentage percentage;

    @PostConstruct
    void init(){
        points = new ArrayList<>();
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName count = new ObjectName("mbeans:type=CountHits");
            ObjectName perc = new ObjectName("mbeans:type=SuccessPercentage");
            hitsCount = new CountHits();
            percentage = new SuccessPercentage();
            mbs.registerMBean(hitsCount, count);
            mbs.registerMBean(percentage, perc);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    void destroy(){
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName count = new ObjectName("mbeans:type=CountHits");
            ObjectName perc = new ObjectName("mbeans:type=SuccessPercentage");
            mbs.unregisterMBean(count);
            mbs.unregisterMBean(perc);
        } catch (MalformedObjectNameException | InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    public List<UPRecord> getPoints(){
        return points;
    }

    public void setPoints(List<UPRecord> points){
        this.points = points;
    }

    public String getX(){
        return x;
    }

    public void setX(String x){
        this.x = x;
    }

    public String getY(){
        return y;
    }

    public void setY(String y){
        this.y = y;
    }

    public String getR(){
        return r;
    }

    public void setR(String R){
        this.r = R;
    }

    private String name = "Фото";
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void clearName(){
        this.name = "";
    }

    public String processAreaRequest() throws IOException{
        try{
            Point point = new Point(Double.parseDouble(x), Double.parseDouble(y));
            long radius = Long.parseLong(r);
            UPRecord record = processPoint(point, radius);
            points.add(0, record);
            hitsCount.addRecord(record);
            percentage.addRecord(record);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } 
        return "redirect";
    }

    public Boolean checkFirstQuater(Point point, long r){
        if (point.xValue() < 0 || point.yValue() < 0){
            return false;
        }
        return (point.xValue() <= (double)r / 2 && point.yValue() <= r);
    }

    public Boolean checkSecondQuater(Point point, long r){
        if (point.xValue() > 0 || point.yValue() < 0){
            return false;
        }
        return point.yValue() <= point.xValue() + (double)r / 2;
    }

    public Boolean checkThirdQuater(Point point, long r){
        if (point.xValue() > 0 || point.yValue() > 0){
            return false;
        }
        return point.xValue() * point.xValue() + point.yValue() * point.yValue() <= r * r;
    }

    public Boolean checkFourthQuater(Point point, long r){
        if (point.xValue() < 0 || point.yValue() > 0){
            return false;
        }
        return (point.xValue() == 0 && point.yValue() >= -r) || (point.yValue() == 0 && point.xValue() <= ((double)r / 2));
    }

    public UPRecord processPoint(Point point, long r){
        //System.out.println(point.xValue() + " " + point.yValue() + " " + r);
        boolean result = checkFirstQuater(point, r) || checkSecondQuater(point, r) || checkThirdQuater(point, r) || checkFourthQuater(point, r);
        //System.out.println(result);
        UPRecord record = new UPRecord(point, r, result);
        //System.out.println(record.isResult());
        return record;
    }

    public boolean isUser(){
        return user != null;
    }

    String login;
    String password;

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

    public String authorize() {
        System.out.println(login);
        System.out.println(password);
        User pot_user = dataBase.getUser(login, password);
        System.out.println("Something happened in authorize");
        if (pot_user != null){
            this.user = pot_user;
            this.points.addAll(user.getRecords());
            login = "";
            //password = "";
            return "to_main";
        }
        return "redirect";
    }
}



