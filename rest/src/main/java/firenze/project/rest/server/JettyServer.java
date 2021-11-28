package firenze.project.rest.server;

import firenze.project.rest.server.servlet.RestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.inject.Inject;

public class JettyServer implements firenze.project.rest.server.Server {
    @Inject
    private RestServlet restServlet;
    Server server = new Server(8080);

    public void run() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        SessionIdManager idmanager = new DefaultSessionIdManager(server);
        server.setSessionIdManager(idmanager);
        SessionHandler sessionsHandler = new SessionHandler();
        context.setHandler(sessionsHandler);
        context.addServlet(new ServletHolder(restServlet), "/");
        server.setHandler(context);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
