package se.liu.ida.tdp024.account.data.test.facade;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class AccountEntityFacadeTest {

    //---- Unit under test ----//
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreate() throws Exception {
        long id = accountEntityFacade.create("CHECK",1,2);
        Assert.assertEquals(1,id);
        id = accountEntityFacade.create("CHECK",1,1);
        Assert.assertEquals(2,id);
    }
    
    @Test 
    public void testFind()  throws Exception {
        accountEntityFacade.create("CHECK",1,2);
        accountEntityFacade.create("CHECK",1,1);
        accountEntityFacade.create("CHECK",1,3);
        accountEntityFacade.create("CHECK",1,2);
        accountEntityFacade.create("CHECK",1,1);
        accountEntityFacade.create("CHECK",1,3);
        accountEntityFacade.create("CHECK",1,2);
        accountEntityFacade.create("CHECK",1,1);
        accountEntityFacade.create("CHECK",1,3);
        List<Account> result = accountEntityFacade.find(1);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.size() == 3);
    }
    
    @Test
    public void testDebit()  throws Exception {
        long id1 = accountEntityFacade.create("CHECK",1,1);
        int amount = 100;
        // Removing imaginary moneys.
        String answer = accountEntityFacade.debit(id1, amount);
        Assert.assertEquals("FAILED", answer);
        
        // Add moneys to remove them.
        accountEntityFacade.credit(id1, amount);
        
        // Removing the full amount
        answer = accountEntityFacade.debit(id1, amount);
        Assert.assertEquals("OK", answer);
        
        // Should have 0 in account balance
        List<Account> account = accountEntityFacade.find(id1);
        Assert.assertEquals(0,account.get(0).getHoldings());
        
        // How does removing negative moneys even work?
        answer = accountEntityFacade.debit(id1,-1);
        Assert.assertEquals("FAILED", answer);
        
        // Removing from nonexisting account
        answer = accountEntityFacade.debit(47114711,-1);
        Assert.assertEquals("FAILED", answer);
        
        // Add moneys to remove them.
        amount = 100;
        accountEntityFacade.credit(id1, amount);
        // Stress testing the locks.
        amount = 1;
        for (int i = 0; i < 100; i++) {
            answer = accountEntityFacade.debit(id1, amount);  
            Assert.assertEquals("OK", answer);      
        }
        // Check if it is 0 again
        account = accountEntityFacade.find(id1);
        Assert.assertEquals(0,account.get(0).getHoldings());        
    }
    
    @Test
    public void testCredit()  throws Exception {
        long id1 = accountEntityFacade.create("CHECK",1,1);
        int amount = 100;
        // Adding easy moneys.
        String answer = accountEntityFacade.credit(id1, amount);
        Assert.assertEquals("OK", answer);
        
        // Check if transaction fell through
        List<Account> account = accountEntityFacade.find(id1);
        System.out.println(account);
        Assert.assertEquals(amount,account.get(0).getHoldings());
        
        // How does adding negative moneys even work?
        answer = accountEntityFacade.credit(id1,-1);
        Assert.assertEquals("FAILED", answer);
        
        // Adding to nonexisting account
        answer = accountEntityFacade.credit(47114711,-1);
        Assert.assertEquals("FAILED", answer);
        
        // Stress testing the locks.
        amount = 1;
        for (int i = 0; i < 100; i++) {
            answer = accountEntityFacade.credit(id1, amount);  
            Assert.assertEquals("OK", answer);      
        }
        // Check if it holds 100+100 moneys
        account = accountEntityFacade.find(id1);
        Assert.assertEquals(200,account.get(0).getHoldings());
    }
    
    @Test
    public void testDebitTransaction()  throws Exception {
        // When performing a debit, one transaction should be created
        long id1 = accountEntityFacade.create("CHECK",1,1);
        int amount = 100;
        int transactions = 0;
        // FAIL creates transaction
        accountEntityFacade.debit(id1, amount);
        transactions++;
        // PASS creates transaction
        accountEntityFacade.credit(id1, amount);
        transactions++;
        // PASS creates transaction
        accountEntityFacade.debit(id1, amount);  
        transactions++;      
        // FAIL, does not create transaction
        accountEntityFacade.debit(id1,-1);
        // FAIL does not create transaction
        accountEntityFacade.debit(47114711,-1);        
        // PASS creates transaction
        transactions++;
        amount = 100;
        accountEntityFacade.credit(id1, amount);
        
        amount = 1;
        for (int i = 0; i < 100; i++) {
            // PASS creates transaction
            accountEntityFacade.debit(id1, amount);
            transactions++;
        }
        try {
            List<Transaction> transactionList = accountEntityFacade.transactions(id1);
            Assert.assertTrue(transactionList != null);
            Assert.assertEquals(transactions,transactionList.size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Test
    public void testCreditTransaction() throws Exception  {
        // When performing a debit, one transaction should be created
        long id1 = accountEntityFacade.create("CHECK",1,1);
        int amount = 100;
        int transactions = 0;
        // FAIL creates transaction
        accountEntityFacade.credit(id1, amount);
        transactions++;
        
        // FAIL, does not create transaction
        accountEntityFacade.credit(id1,-1);
        // FAIL does not create transaction
        accountEntityFacade.credit(47114711,-1);        
        // PASS creates transaction
        amount = 1;
        for (int i = 0; i < 100; i++) {
            // PASS creates transaction
            accountEntityFacade.credit(id1, amount);
            transactions++;
        }
        try {
            List<Transaction> transactionList = accountEntityFacade.transactions(id1);
            Assert.assertTrue(transactionList != null);
            Assert.assertEquals(transactions,transactionList.size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    @Test
    public void testTransactions() throws Exception  {
        int amount = 100;
        String type = "DEBIT";
        Date date = new Date();
        long accountId = accountEntityFacade.create("CHECK", 1, 1);     
        Account account = accountEntityFacade.find(accountId).get(0);
        long transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        transactionId = transactionEntityFacade.create(type, amount, date, "OK", account);
        try {
            List<Transaction> transactions = accountEntityFacade.transactions(accountId);

            Assert.assertTrue(transactions != null);
            Assert.assertTrue(transactions.size() > 0);
            Assert.assertTrue(transactions.size() == 4);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
