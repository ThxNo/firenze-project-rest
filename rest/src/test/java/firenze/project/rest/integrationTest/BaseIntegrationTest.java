package firenze.project.rest.integrationTest;

import firenze.project.rest.SimpleRestApplication;
import firenze.project.rest.example.App;
import firenze.project.rest.server.servlet.RestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseIntegrationTest {
    private static Server server;
    private static SimpleRestApplication restApplication;

    @BeforeAll
    static void beforeAll() throws Exception {
        restApplication = new SimpleRestApplication(App.class);
        restApplication.run();
    }


    @AfterAll
    static void afterAll() throws Exception {
        restApplication.stop();
    }
}
