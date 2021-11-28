package firenze.project.rest.example.resource;

import firenze.project.rest.example.domain.Account;
import jakarta.ws.rs.GET;

public class AccountResource {

    public AccountResource() {
    }

    @GET
    public Account getAccount() {
        return new Account("accountName");
    }
}
