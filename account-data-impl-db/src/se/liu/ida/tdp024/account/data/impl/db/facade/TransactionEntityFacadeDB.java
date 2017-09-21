/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author nikni292
 */

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public long create(String type, int amount, Date created, String status, Account account) {
        EntityManager em = EMF.getEntityManager();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            em.getTransaction().begin();
        
            Transaction transaction = new TransactionDB();
            
            transaction.setType(type);
            transaction.setAmount(amount);
             
            transaction.setCreated(dateFormat.format(created));
            
            transaction.setStatus(status);
            transaction.setAccount(account);
            
            em.persist(transaction);

            em.getTransaction().commit();

            return transaction.getID();
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
    public Transaction find(long id) {     
        EntityManager em = EMF.getEntityManager();
        Query q = em.createQuery(
                "SELECT a FROM TransactionDB a WHERE a.id = :id",
                Transaction.class)
                .setParameter("id", id);
        return (Transaction)q.getSingleResult();
    }
    
}
