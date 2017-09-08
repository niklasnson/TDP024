package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Override 
    public int create() {
        return 0; 
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override 
    public void setId(int id) {
        this.id = id;
    }
}
