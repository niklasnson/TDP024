package se.liu.ida.tdp024.account.logic.impl.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDataException;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private final AccountEntityFacade accountEntityFacade;
    private final AccountJsonSerializer ajs = new AccountJsonSerializerImpl();
    private final HTTPHelper http = new HTTPHelperImpl();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    private long getPersonId(String personName)
            throws AccountLogicException, UnsupportedEncodingException {
        String name;
        try {
            name = URLEncoder.encode(personName,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw e;
        }
        String answerJson = http.get("http://localhost:8060/find." + name);
        PersonDTO[] persons = ajs.fromJson(answerJson, PersonDTO[].class);
        if (persons == null || persons.length == 0) {
            throw new AccountLogicException("Person not included in response.");
        } 
        return persons[0].getId();
    }
        
    private long getBankId(String bankName)
            throws AccountLogicException, UnsupportedEncodingException {
        String name;
        try {
            name = URLEncoder.encode(bankName,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println(e);
            throw e;
        }
        String answerJson = http.get("http://localhost:8070/find." + name);
        BankDTO[] persons = ajs.fromJson(answerJson, BankDTO[].class);
        if (persons.length == 0) {
            throw new AccountLogicException("Bank not included in response.");
        } 
        return persons[0].getId();
    }

    @Override
    public long create(String accountType, String personName, String bankName)
            throws AccountLogicException, UnsupportedEncodingException, AccountDataException {
        try {
           long personId = getPersonId(personName);
           long bankId = getBankId(bankName);
           
           return accountEntityFacade.create(accountType,bankId,personId);
        }
        catch (AccountDataException e) {
            throw e;
        }
        catch (AccountLogicException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (Exception e) {
            throw new AccountLogicException("Caught a exception e: " + e);
        }
    }

    @Override
    public List<Account> find(String personName) 
    throws AccountLogicException, UnsupportedEncodingException {        
        try {
            long id = getPersonId(personName);
            System.out.println("=====> Found personId: "+id);
            return accountEntityFacade.find(id);
        } catch (AccountLogicException e) {
            System.out.println(e);
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
    }

    @Override
    public String debit(long id, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount is negative in debit");
        }
        return accountEntityFacade.debit(id, amount);
    }

    @Override
    public String credit(long id, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount is negative in credit");
        }
        return accountEntityFacade.credit(id, amount);        
    }

    @Override
    public List<Transaction> transactions(long id) {
        try {
            return accountEntityFacade.transactions(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<Transaction>();
    }
}
