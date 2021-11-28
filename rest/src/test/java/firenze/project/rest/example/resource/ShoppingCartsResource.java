package firenze.project.rest.example.resource;

import com.google.common.collect.ImmutableList;
import firenze.project.rest.example.domain.ShoppingCart;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/shopping-cart")
public class ShoppingCartsResource {
    @GET
    public List<ShoppingCart> getAll() {
        return ImmutableList.of();
    }

    @GET
    @Path("{id}")
    public ShoppingCart get(@PathParam("id") String id) {
        return new ShoppingCart(id);
    }
}
