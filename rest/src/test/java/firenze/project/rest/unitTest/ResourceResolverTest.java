package firenze.project.rest.unitTest;

import com.google.common.collect.ImmutableSet;
import firenze.project.rest.domain.Resolver;
import firenze.project.rest.domain.ResourceContainer;
import firenze.project.rest.ResourceResolver;
import firenze.project.rest.example.resource.HelloResource;
import firenze.project.rest.example.resource.ShoppingCartsResource;
import firenze.project.rest.example.resource.UserResource;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceResolverTest extends UnitTest {

    @Test
    void should_resolve_resource() {
        //given
        ResourceResolver resourceResolver = new ResourceResolver(context);
        Set<Class<?>> rootResources = ImmutableSet.of(HelloResource.class, ShoppingCartsResource.class, UserResource.class);
        //when
        Resolver resource = resourceResolver.build(rootResources);
        //then
        assertThat(resource).isInstanceOf(ResourceContainer.class);
        ResourceContainer resourceContainer = (ResourceContainer) resource;
        assertThat(resourceContainer.getSubResources().size()).isEqualTo(3);
    }
}
