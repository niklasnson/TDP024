package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import java.util.List;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDataException;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private final String C_FAILED = "FAILED";
    private final String C_OK = "OK";
    
    @Override
    public long create (String accountType, long nameKey, long bankKey) throws AccountDataException {
        
        EntityManager em = EMF.getEntityManager();
        
        if (!accountType.equals("CHECK") || !accountType.equals("SAVINGS") ) {
            AccountLogger logger = new AccountLoggerMonlog(); 
            logger.log(AccountLogger.LoggerLevel.INFO, "Litet meddelande", "Elefant!");

            throw new AccountDataException("accountType is invalid");
        }
        
        try {
            em.getTransaction().begin();

            Account account = new AccountDB();
        
            account.setBankKey(nameKey);
            account.setPersonKey(bankKey);
            account.setAccountType(accountType);
            account.setHoldings(0);
            
            em.persist(account);

            em.getTransaction().commit();

            return account.getId();
        }
        catch (Exception e) {
            // Logga fel;
            throw new ServiceConfigurationError("Commit fails");
        }
        finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
    @Override
    public List<Account> find (long id) {
        EntityManager em = EMF.getEntityManager();
        Query q = em.createQuery(
                "SELECT a FROM AccountDB a WHERE a.personKey = :id",
                AccountDB.class)
                .setParameter("id", id);
        return q.getResultList(); 
    }
    
    @Override 
    public String debit (long id, int amount) {
        String status = C_FAILED;
        if (amount < 0) {
            return status;
        }
        EntityManager em = EMF.getEntityManager();  
        Account account = em.find(AccountDB.class,id);       
        try {           
            if (account != null) {
                em.getTransaction().begin(); 
                
                em.lock(account,LockModeType.PESSIMISTIC_READ);
                em.lock(account,LockModeType.PESSIMISTIC_WRITE);
                
                if (account.getHoldings() >= amount) {
                    account.alterHoldings(-amount);
                } else {
                    return status;
                }
                em.getTransaction().commit();
                status = C_OK;
                return status;
            }
            return status;
        }
        catch (Exception e) {
            // Logga fel;
            System.out.println(e.toString());
            throw new ServiceConfigurationError("Commit fails");
        }
        finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            // Create new transaction
            Date date = new Date();
            transactionEntityFacade.create("DEBIT",amount,date,status,account);
        }
    }
    
    @Override
    public String credit (long id, int amount) {
        String status = C_FAILED;
        if (amount < 0) {
            return status;
        }
        EntityManager em = EMF.getEntityManager();
        Account account = em.find(AccountDB.class,id);        
                
        try {
            if (account != null) {           
                em.getTransaction().begin();
                
                em.lock(account,LockModeType.PESSIMISTIC_READ);
                em.lock(account,LockModeType.PESSIMISTIC_WRITE);
                
                account.alterHoldings(amount);
                em.getTransaction().commit();
                status = C_OK;
                return status;
            }

            return status;
        }
        catch (Exception e) {
            // Logga fel;
            System.out.println(e.toString());
            throw new ServiceConfigurationError("Commit fails");
        }
        finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            
            // Create new transaction
            Date date = new Date();
            transactionEntityFacade.create("CREDIT",amount,date,status,account);
        }
    }
    
    @Override 
    public List<Transaction> transactions (long id) 
    throws AccountDataException {
        EntityManager em = EMF.getEntityManager();
        
        Account account = em.find(AccountDB.class, id);
        if ( account == null) {
            throw new AccountDataException("Account not found");
        }
        
        Query q = em.createQuery(
                "SELECT t FROM TransactionDB t WHERE t.account = :account",
                Transaction.class)
                .setParameter("account", account);
        return q.getResultList();       
    }
}
