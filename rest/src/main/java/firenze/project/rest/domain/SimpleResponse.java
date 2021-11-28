package firenze.project.rest.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponse {
    private Integer statusCode;
    private Object responseBody;

    public void to(HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("Application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}
