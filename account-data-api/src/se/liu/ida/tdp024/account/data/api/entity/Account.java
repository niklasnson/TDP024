package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {
    public List<Transaction> getTransactions();
    public void setTransactions(List<Transaction> transactions);
    public long getId(); 
    public void setId(long id);
    public String getAccountType(); 
    public void setAccountType(String account);
    public long getBankKey();
    public void setBankKey(long id);
    public long getPersonKey();
    public void setPersonKey(long id);
    public int getHoldings();
    public void setHoldings(int val);
    public int alterHoldings(int amount);
}
