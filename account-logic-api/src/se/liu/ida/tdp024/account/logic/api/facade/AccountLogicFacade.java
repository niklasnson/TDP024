package se.liu.ida.tdp024.account.logic.api.facade;

import java.io.UnsupportedEncodingException;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;


public interface AccountLogicFacade {
    public long create(String accountType, String personName, String bankName) throws Exception, UnsupportedEncodingException;
    public List<Account> find(String personName) throws Exception, UnsupportedEncodingException;
    public String debit(long id, int amount);
    public String credit(long id, int amount);
    public List<Transaction> transactions(long id);
}
