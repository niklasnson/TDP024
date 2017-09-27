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
    private long id;
    
    private long bankKey;
    private long personKey;
    private int holdings;
    private String accountType;
    /*
    @OneToMany(mappedBy="account", targetEntity=TransactionDB.class) 
    private List<Transaction> transactions;
    
    
    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    */
    @Override
    public long getId() {
        return id;
    }
    
    @Override 
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public long getBankKey() {
        return bankKey;
    }
    @Override
    public void setBankKey(long id) {
        bankKey = id;
    }
    
    @Override
    public long getPersonKey() {
        return personKey;
    }
    @Override
    public void setPersonKey(long id) {
        personKey = id;
    }
    @Override
    public int getHoldings() {
        return holdings;
    }
    @Override
    public void setHoldings(int val) {
        holdings = val;
    }
    @Override 
    public int alterHoldings(int amount) {
        holdings += amount;
        return holdings;
    }
    
    @Override
    public String getAccountType() {
        return accountType;
    }
    
    @Override
    public void setAccountType(String account) {
        accountType = account;
    }
    
    @Override
    public String toString() {
        return "<AccountDB id:" + id + ", pKey:" + personKey + ", bKey:"+ bankKey +", hold:" + holdings + ">";
    }

    
}
