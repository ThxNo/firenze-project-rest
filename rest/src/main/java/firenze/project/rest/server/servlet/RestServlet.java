package firenze.project.rest.server.servlet;

import firenze.project.rest.RestHttpDispatcher;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.domain.SimpleResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import java.io.IOException;

@WebServlet(value = "/*")
public class RestServlet extends HttpServlet {
    @Inject
    private RestHttpDispatcher dispatcher;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleResponse resp = dispatcher.dispatch(SimpleRequest.of(request));
        resp.to(response);
    }
}
