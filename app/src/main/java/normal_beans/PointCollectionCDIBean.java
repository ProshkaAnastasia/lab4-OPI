package normal_beans;

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

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import mbeans.CountHits;
import mbeans.SuccessPercentage;
import models.UPRecord;


@Named("pointCollection")
@SessionScoped
public class PointCollectionCDIBean implements Serializable{
    private List<UPRecord> points;
    private CountHits hitsCount;
    private SuccessPercentage percentage;
    
    @PostConstruct
    void init(){
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

    public void addPoint(UPRecord record) {
        points.add(0, record);
        hitsCount.addRecord(record);
        percentage.addRecord(record);
    }

    public List<UPRecord> getPoints(){
        return points;
    }

    public void setPoints(List <UPRecord> records) {
        points = records;
        hitsCount.setRecords(records);
        percentage.setRecords(records);
    }

    public void addPoints(List <UPRecord> records) {
        points.addAll(records);
    }

    public void clear(){
        points.clear();
    }
}

