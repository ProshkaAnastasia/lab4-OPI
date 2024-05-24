package normal_beans;

import java.io.IOException;
import java.io.Serializable;

import beans.DataBaseCDIBean;
import classes.Point;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.UPRecord;

@Named("pointRequest")
@RequestScoped
public class PointCDIBean implements Serializable{
    @Inject
    PointCollectionCDIBean pointList;
    @Inject
    UserBean userBean;
    @Inject 
    DataBaseCDIBean dataBase;
    double x;
    double y;
    long r;
    public double getX(){
        return x;
    }
    public void setX(double x){
        this.x = x;
    }
    public double getY(){
        return x;
    }
    public void setY(double y){
        this.y = y;
    }
    public long getR(){
        return r;
    }
    public void setR(long r){
        this.r = r;
    }

    public String processAreaRequest() throws IOException{
        //System.out.println("Something happened hrrrr");
        //System.out.println(x + " " + y + " " + r);
        try{
            Point point = new Point(x, y);
            long radius = r;
            UPRecord record = processPoint(point, radius);
            if (userBean.authorized()){
                System.out.println("Saving record");
                record.setUser(userBean.getUser());
                dataBase.saveRecord(record);
            }
            pointList.addPoint(record);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } 
        return "redirect";
    }

    public boolean checkFirstQuater(Point point, long r){
        if (point.xValue() < 0 || point.yValue() < 0){
            return false;
        }
        return (point.xValue() <= (double)r / 2 && point.yValue() <= r);
    }

    public boolean checkSecondQuater(Point point, long r){
        if (point.xValue() > 0 || point.yValue() < 0){
            return false;
        }
        return point.yValue() <= point.xValue() + (double)r / 2;
    }

    public boolean checkThirdQuater(Point point, long r){
        if (point.xValue() > 0 || point.yValue() > 0){
            return false;
        }
        return point.xValue() * point.xValue() + point.yValue() * point.yValue() <= r * r;
    }

    public boolean checkFourthQuater(Point point, long r){
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
}
