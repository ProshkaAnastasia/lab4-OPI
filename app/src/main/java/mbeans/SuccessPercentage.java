package mbeans;

import java.util.List;

import models.UPRecord;

public class SuccessPercentage implements SuccessPercentageMBean {

    private long success = 0;
    private long total = 0;

    @Override
    public double returnPercentage() {
        return ((double)(success) / (double)total);
    }

    public void addRecord(UPRecord record) {
        System.out.println("RECORD WAS ADDED TO PERCENTAGE");
        total++;
        if (record.result){
            success++;
        }
        System.out.println(total + " " + success);
    }

    public void setRecords(List <UPRecord> records){
        success = records.stream().filter(r -> r.result).count();
        total = records.size();
    }
    
}
