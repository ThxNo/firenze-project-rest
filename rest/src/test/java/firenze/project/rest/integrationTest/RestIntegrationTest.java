package firenze.project.rest.integrationTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RestIntegrationTest extends BaseIntegrationTest {
    @Test
    void should_handle_correct_request() {
        given()
                .get("/hello")
                .then()
                .statusCode(200)
                .body(Matchers.containsString("Hello Servlet"));
    }

    @Test
    void should_handle_request_with_path_param() {
        given()
                .get("/shopping-cart/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.is("1"));
    }

    @Test
    void should_handle_request_with_sub_resource() {
        given()
                .get("/users/1/account")
                .then()
                .statusCode(200)
                .body("accountName", Matchers.is("accountName"));
    }

    @Test
    void should_handle_wrong_request_with_404() {
        given()
                .get("/not-exist-uri")
                .then()
                .statusCode(404)
                .body(Matchers.containsString("Path Not Found"));
    }
}
