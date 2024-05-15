package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import classes.Point;
import jakarta.persistence.*;

@Entity
@Table (name="records")
public class UPRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "x")
    private double x;

    public double getX(){
        return this.x;
    }  

    public void setX(double x) {
        this.x = x;
    }

    @Column(name = "y")
    private double y;

    public double getY(){
        return this.y;
    }  

    public void setY(double y) {
        this.y = y;
    }

    @Column(name = "R")
    public long R;

    public long getR(){
        return this.R;
    }  

    public void setR(long r) {
        this.R = r;
    }

    @Column(name = "result")
    public boolean result;

    public boolean isResult(){
        return this.result;
    }  

    public void setResult(boolean result) {
        this.result = result;
    }

    @Column(name = "time")
    public LocalDateTime time;

    public LocalDateTime getTime(){
        return this.time;
    }

    public void setTime(LocalDateTime time){
        this.time = time;
    }

    public String getStringTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        if (time != null){
            return time.format(formatter);
        } else {
            return "null";
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public UPRecord(){}
    
    public UPRecord(Point point, long rValue, boolean result){
        this.x = point.xValue();
        this.y = point.yValue();
        this.R = rValue;
        this.result = result;
        this.time = LocalDateTime.now();
    }

    public UPRecord(double x, double y, long r, boolean result){
        this.x = x;
        this.y = y;
        this.R = r;
        this.result = result;
        this.time = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }
}