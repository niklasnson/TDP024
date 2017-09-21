package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


@Path("/account")
public class AccountService {
    @GET
    @Path("create")
    public Response create(
            @QueryParam("name") String name) {

        //categoryLogicFacade.create(name);
        System.out.println("====>" + name);
        return Response.ok().build();

    }    
}
