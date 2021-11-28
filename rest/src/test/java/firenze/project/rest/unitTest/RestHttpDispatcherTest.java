package firenze.project.rest.unitTest;

import firenze.project.rest.RestHttpDispatcher;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.domain.SimpleResponse;
import firenze.project.rest.example.domain.Account;
import jakarta.ws.rs.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestHttpDispatcherTest extends UnitTest {
    RestHttpDispatcher restHttpDispatcher;
    @BeforeEach
    void setUp() {
        restHttpDispatcher = new RestHttpDispatcher();
        restHttpDispatcher.setContext(context);
    }

    @Test
    void should_dispatch_to_hello_resource() {
        //given
        SimpleRequest request = SimpleRequest.builder().method(HttpMethod.GET).path("/hello").build();
        //when
        SimpleResponse response = restHttpDispatcher.dispatch(request);
        //then
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getResponseBody().toString()).contains("Hello Servlet");
    }

    @Test
    void should_dispatch_to_user_resource() {
        //given
        SimpleRequest request = SimpleRequest.builder().method(HttpMethod.POST).path("/users").build();
        //when
        SimpleResponse response = restHttpDispatcher.dispatch(request);
        //then
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    void should_return_404_when_no_url_matched() {
        //given
        SimpleRequest request = SimpleRequest.builder().method(HttpMethod.GET).path("/no-match-url").build();
        //when
        SimpleResponse response = restHttpDispatcher.dispatch(request);
        //then
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    void should_dispatch_with_sub_resource() {
        //given
        SimpleRequest request = SimpleRequest.builder().method(HttpMethod.GET).path("/users/1/account").build();
        //when
        SimpleResponse response = restHttpDispatcher.dispatch(request);
        //then
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getResponseBody()).isInstanceOf(Account.class);
    }
}