package firenze.project.rest.example.resource;

import com.google.common.collect.ImmutableList;
import firenze.project.rest.example.domain.ShoppingCart;
import firenze.project.rest.example.domain.User;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/users")
public class UserResource {
    @GET
    public List<User> getAll() {
        return ImmutableList.of();
    }

    @POST
    public void add() {
    }

    @Path("/{id}/account")
    public AccountResource getAccount() {
        return new AccountResource();
    }
}
