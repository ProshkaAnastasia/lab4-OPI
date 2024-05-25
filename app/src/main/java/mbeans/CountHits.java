package mbeans;

import java.util.List;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import models.UPRecord;

public class CountHits extends NotificationBroadcasterSupport implements CountHitsMBean {
    private long successfulCount = 0;
    private long totalCount = 0;
    private long misses = 0;

    @Override
    public long returnSuccessfulHits() {
        return successfulCount;
    }

    @Override
    public long returnTotalHits() {
        return totalCount;
    }

    public void addRecord(UPRecord record) {
        System.out.println("RECORD WAS ADDED TO COUNTHITS");
        totalCount++;
        if (record.result){
            misses = 0;
            successfulCount++;
        } else {
            misses++;
            if (misses >= 2){
                Notification notification = new Notification("TwoPointsMissed",
                        this, misses, "Count of missed points equals 2");
                sendNotification(notification);
            }
        }
    }

    public void setRecords(List <UPRecord> records){
        successfulCount = records.stream().filter(r -> r.result).count();
        totalCount = records.size();
        misses = 0;
    }

    @Override
    public long getSuccessfulHits() {
        return successfulCount;
    }

    @Override
    public long getTotalHits() {
        return totalCount;
    }
    
}
