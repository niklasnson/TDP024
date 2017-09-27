package se.liu.ida.tdp024.account.logic.impl.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
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
            throws Exception {
        if (personName.equals("")) {
            throw new Exception("personName can't be blank");
        }
        
        // Shall we expect 1 person or a list of persons?
        boolean isKey = true;
        try {
            Integer.parseInt(personName);
        }
        catch (NumberFormatException e) {
            isKey = false;
        }
        // URLencode 
        String name;
        try {
            name = URLEncoder.encode(personName,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw e;
        }
        
        String answerJson = http.get("http://localhost:8060/find." + name);
        
        PersonDTO person = null;
        if (isKey) {
            person = ajs.fromJson(answerJson, PersonDTO.class);
        } else {
            PersonDTO[] persons = ajs.fromJson(answerJson, PersonDTO[].class);
            person = persons[0];
        }
        
        if (person == null) {
            throw new Exception("Person not included in response. find."+name);
        } 
        return person.getId();
    }
        
    private long getBankId(String bankName)
            throws Exception, UnsupportedEncodingException {
        if (bankName.equals("")) {
            throw new Exception("bankName can't be blank");
        }
        String name;
        try {
            name = URLEncoder.encode(bankName,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw e;
        }
        String answerJson = http.get("http://localhost:8070/find." + name);
        BankDTO[] banks = ajs.fromJson(answerJson, BankDTO[].class);
        if (banks == null || banks.length == 0) {
            throw new Exception("Bank not included in response.");
        } 
        return banks[0].getId();
    }

    @Override
    public long create(String accountType, String personName, String bankName)
            throws Exception, UnsupportedEncodingException {
        try {
           long personId = getPersonId(personName);
           long bankId = getBankId(bankName);
           
           return accountEntityFacade.create(accountType,bankId,personId);
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Horrible catch all Exception: " + e);
        }
    }

    @Override
    public List<Account> find(String personName) 
    throws Exception, UnsupportedEncodingException {        
        try {
            long id = getPersonId(personName);
            System.out.println("=====> Found personId: "+id);
            return accountEntityFacade.find(id);
        } catch (Exception e) {
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
    public List<Transaction> transactions(long id) throws Exception {
        try {
            return accountEntityFacade.transactions(id);
        } catch (Exception e) {
            throw e;
        }
        // return new ArrayList<Transaction>();
    }
}
