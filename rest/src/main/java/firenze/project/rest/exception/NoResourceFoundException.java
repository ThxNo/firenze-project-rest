package firenze.project.rest.exception;

import firenze.project.rest.domain.SimpleRequest;

import java.text.MessageFormat;

public class NoResourceFoundException extends RuntimeException {
    public NoResourceFoundException(SimpleRequest request) {
        super(MessageFormat.format("no resource matched with request: ", request.toString()));
    }
}
