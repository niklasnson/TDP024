package se.liu.ida.tdp024.account.logic.test.facade;

import java.io.UnsupportedEncodingException;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade
            = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    @Test
    public void testCreate() throws Exception {
        long id = accountLogicFacade.create("CHECK","Marcus Bendtsen","NORDNET");
        Assert.assertTrue(id == 1);
        id = accountLogicFacade.create("CHECK","Marcus Bendtsen","NORDNET");
        Assert.assertTrue(id == 2);
    }
    @Test
    public void testCreateFail() {
        boolean exception = false;
        try {
            long id = accountLogicFacade.create("CHECK","Macus Bendtsen","NORDNET");
        }
        catch (Exception e) {
            exception = true;
        }
        Assert.assertTrue(exception);
    }
    @Test
    public void testFind() {
        String name = "Marcus Bendtsen";
        try {
            accountLogicFacade.create("CHECK", name,"NORDNET");
            accountLogicFacade.create("CHECK", name,"NORDNET");
            accountLogicFacade.create("CHECK", name,"NORDNET");
            accountLogicFacade.create("CHECK", name,"NORDNET");
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
        } catch (Exception ex) {
            Assert.assertTrue(false);           
        }
        
        List<Account> accounts;
        try {
            accounts = accountLogicFacade.find(name);
            Assert.assertTrue(accounts != null);
            Assert.assertTrue(accounts.size() > 0);
            Assert.assertTrue(accounts.size() == 4);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
        } catch (Exception ex) {
            Assert.assertTrue(false);           
        }
        
    }
}