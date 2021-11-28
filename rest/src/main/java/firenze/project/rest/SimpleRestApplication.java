package firenze.project.rest;

import firenze.project.rest.context.RestContext;
import firenze.project.rest.server.Server;
import firenze.project.tiny.spring.di.container.AutoContainerConfig;
import firenze.project.tiny.spring.di.container.FDIContainer;

public class SimpleRestApplication {
    FDIContainer container;

    public SimpleRestApplication(Class<?> mainClass) {
        AutoContainerConfig autoContainerConfig = new AutoContainerConfig();
        autoContainerConfig.registerPackage(getClass().getPackageName());
        autoContainerConfig.registerPackage(mainClass.getPackageName());
        container = new FDIContainer(autoContainerConfig);
        container.get(RestContext.class).init(mainClass, container);
    }

    public void run() {
        try {
            container.get(Server.class).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        container.get(Server.class).stop();
    }
}
