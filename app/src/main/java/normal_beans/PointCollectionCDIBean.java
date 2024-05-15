package normal_beans;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import models.UPRecord;


@Named("pointCollection")
@SessionScoped
public class PointCollectionCDIBean implements Serializable{
    private List<UPRecord> points;
    public void addPoint(UPRecord record) {
        points.add(0, record);
    }
    public List<UPRecord> getPoints(){
        return points;
    }
    public void setPoints(List <UPRecord> records) {
        points = records;
    }
    public void addPoints(List <UPRecord> records) {
        points.addAll(records);
    }
    public void clear(){
        points.clear();
    }
}

