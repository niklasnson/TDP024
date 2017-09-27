/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.util.Date;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

/**
 *
 * @author nikni292
 */
public class TransactionEntityFacadeTest {
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private final StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreate() throws Exception {
        int amount = 100;
        String type = "DEBIT";
        Date date = new Date();
        
        long accountId = accountEntityFacade.create("CHECK", 1, 1);     
        Account account = accountEntityFacade.find(accountId).get(0);
        long transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        Transaction transaction = transactionEntityFacade.find(transactionId);
        
        Assert.assertFalse(transaction == null);
    }
    
    @Test
    public void testFind()  throws Exception {
        int amount = 100;
        String type = "DEBIT";
        Date date = new Date();
        long accountId = accountEntityFacade.create("CHECK", 1, 1);     
        Account account = accountEntityFacade.find(accountId).get(0);
        long transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        Transaction transaction = transactionEntityFacade.find(transactionId);
        
        Assert.assertFalse(transaction == null); 
        
    }
 
}