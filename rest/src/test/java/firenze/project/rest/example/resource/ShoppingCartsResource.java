package firenze.project.rest.example.resource;

import com.google.common.collect.ImmutableList;
import firenze.project.rest.example.domain.ShoppingCart;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.List;

@Path("/shopping-cart")
public class ShoppingCartsResource {
    @GET
    public List<ShoppingCart> getAll(@QueryParam("firstOne") Boolean firstOne) {
        if (firstOne) {
            return ImmutableList.of(new ShoppingCart("1"));
        }
        return ImmutableList.of(new ShoppingCart("1"), new ShoppingCart("2"));
    }

    @GET
    @Path("{id}")
    public ShoppingCart get(@PathParam("id") String id) {
        return new ShoppingCart(id);
    }
}
