package se.liu.ida.tdp024.account.rest.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicException;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;


@Path("/account")
public class AccountService {
    private final AccountLogicFacade  accountLogicFacade =
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final AccountJsonSerializer ajs = new AccountJsonSerializerImpl();
    
    @GET
    @Path("/create")
    public Response create(
        @QueryParam("name") String personName,
        @QueryParam("bank") String bankName,
        @QueryParam("accounttype") String accountType) {

        try {
            long id = accountLogicFacade.create(accountType,personName,bankName);
            System.out.println("====> " + id);
            return Response.ok().entity("OK").build();
        } catch (AccountLogicException e) {
            return Response.serverError().entity("FAILED").build();
        } catch (UnsupportedEncodingException e) {
            return Response.serverError().entity("FAILED").build();
        } catch (Exception ex) {
            return Response.serverError().entity("FAILED").build();
        }
        
    }
    
    @GET
    @Path("/find/person")
    public Response find(@QueryParam("person") String person) {
        try {
            List<Account> accounts = accountLogicFacade.find(person);
            System.out.println("====> account.size: " + accounts.size());
            return Response.ok().entity(ajs.toJson(accounts)).build();
        } catch (AccountLogicException e) {
            return Response.serverError().entity("[]").build();
        } catch (UnsupportedEncodingException e) {
            return Response.serverError().entity("[]").build();
        } catch (Exception ex) {
            return Response.serverError().entity("[]").build();
        }
    }
    
    @GET
    @Path("/debit")
    public Response debit(
            @QueryParam("id") long id,
            @QueryParam("amount") int amount) {
            String result = accountLogicFacade.debit(id, amount);
            return Response.ok().entity(result).build();
    }
    
    @GET
    @Path("/credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") int amount) {
            String result = accountLogicFacade.credit(id, amount);
            return Response.ok().entity(result).build();
    }
    @GET
    @Path("/transactions")
    public Response transactions(
            @QueryParam("id") long id) {
            List<Transaction> transactions = accountLogicFacade.transactions(id);
            return Response.ok().entity(ajs.toJson(transactions)).build();
    }
    
}
