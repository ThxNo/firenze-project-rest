package firenze.project.rest;

import firenze.project.rest.context.RestContext;
import firenze.project.rest.domain.ResourceInstance;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.domain.SimpleResponse;
import lombok.Setter;

import javax.inject.Inject;
import java.util.Objects;

@Setter
public class RestHttpDispatcher {
    @Inject
    private RestContext context;

    public SimpleResponse dispatch(SimpleRequest request) {
        ResourceResolver resourceResolver = context.getResourceResolver();
        ResourceInstance resource = resourceResolver.resolve(request);
        if (Objects.isNull(resource)) {
            return SimpleResponse.builder().statusCode(404).responseBody("Path Not Found!").build();
        }
        return resource.handle(request);
    }
}
