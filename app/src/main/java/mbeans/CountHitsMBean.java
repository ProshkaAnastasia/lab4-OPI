package mbeans;

public interface CountHitsMBean {
    //void addRecord(UPRecord record);
    long returnSuccessfulHits();
    long returnTotalHits();
}
