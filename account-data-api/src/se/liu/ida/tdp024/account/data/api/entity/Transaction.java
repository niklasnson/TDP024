/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

/**
 *
 * @author nikni292
 */
public interface Transaction extends Serializable {
    public long getID();
    public void setID(long id);
    public String getType();
    public void setType(String type);
    public int getAmount();
    public void setAmount(int amount);
    public String getCreated();
    public void setCreated(String date);
    public String getStatus();
    public void setStatus(String status);
    public Account getAccount();
    public void setAccount(Account account);
}
