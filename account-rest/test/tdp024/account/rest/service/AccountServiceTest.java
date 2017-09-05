package tdp024.account.rest.service;

import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade;

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    @Test
    public void testCreate() {
    }
}