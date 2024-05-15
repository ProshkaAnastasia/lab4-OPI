package service;

import service.dao.UPRecordDAO;
import service.dao.UPRecordDaoPostgres;

import java.util.List;

import models.UPRecord;
import models.User;

public class UPRecordService {
    private UPRecordDAO recordDAO = new UPRecordDaoPostgres();
    public List<UPRecord> getRecordsByUser(User user){
        return recordDAO.getByUser(user);
    }
    public void saveRecord(UPRecord record){
        recordDAO.save(record);
    }
}
