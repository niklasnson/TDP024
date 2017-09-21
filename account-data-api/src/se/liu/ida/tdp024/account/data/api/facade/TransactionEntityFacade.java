/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.facade;

import java.util.Date;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author nikni292
 */
public interface TransactionEntityFacade {
    public long create(String type, 
                        int amount, 
                        Date created, 
                        String status, 
                        Account account);
    public Transaction find(long id);
    
}
