package service.dao;

import models.UPRecord;
import models.User;

import java.util.List;

public interface UPRecordDAO {
    UPRecord getById(long id);
    List<UPRecord> getByUser(User user);
    void update(UPRecord record);
    void save(UPRecord record);
    void delete(UPRecord record);
}
