package firenze.project.rest.context;

import firenze.project.rest.ResourceResolver;
import firenze.project.tiny.spring.di.container.FDIContainer;
import lombok.Getter;

@Getter
public class RestContext {
    private Class<?> mainClass;
    private FDIContainer container;
    private ResourceResolver resourceResolver;

    public void init(Class<?> mainClass, FDIContainer container) {
        this.mainClass = mainClass;
        this.container =container;
        this.resourceResolver = new ResourceResolver(this);
    }

}
