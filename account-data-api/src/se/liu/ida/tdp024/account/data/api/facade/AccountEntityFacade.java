package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountEntityFacade {
    public long create(String accountType, long personKey, long bankKey);
    public List<Account> find(long id);
    public String debit(long id, int amount);
    public String credit(long id, int amount);
    public List<Transaction> transactions (long id) throws Exception; // TODO: fix this exception class
}
