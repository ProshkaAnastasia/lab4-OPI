package models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "login")
    String login;

    @Column(name = "password")
    String password;

    @Column(name = "token")
    String token;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UPRecord> records;

    public User(){}

    public User(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
        this.records = new ArrayList<>();
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
        this.records = new ArrayList<>();
    }

    public void setToken(String token){
        this.token = token;
    }

    public void insertRecord(UPRecord record){
        records.add(record);
        record.setUser(this);
    }

    public void deleteRecord(int index){
        UPRecord record = records.get(index);
        records.remove(index);
        record.setUser(null);
    }

    public List<UPRecord> getRecords(){
        return records;
    }

    public void setRecords(List<UPRecord> in){
        records = new ArrayList<>();
        in.stream().forEach((UPRecord r) -> {
            records.add(r);
        }); 
    }

    public String getName(){
        return name;
    }

    public String getLogin(){
        return login;
    }

    public long getId(){
        return id;
    }
}