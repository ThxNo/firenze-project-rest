package firenze.project.rest.unitTest;

import firenze.project.rest.context.RestContext;
import firenze.project.rest.example.App;
import firenze.project.tiny.spring.di.container.AutoContainerConfig;
import firenze.project.tiny.spring.di.container.FDIContainer;
import org.junit.jupiter.api.BeforeAll;

public class UnitTest {
    protected static final RestContext context = new RestContext();

    @BeforeAll
    static void beforeAll() {
        AutoContainerConfig autoContainerConfig = new AutoContainerConfig();
        autoContainerConfig.registerPackage(App.class.getPackageName());
        FDIContainer container = new FDIContainer(autoContainerConfig);
        context.init(App.class, container);
    }
}
