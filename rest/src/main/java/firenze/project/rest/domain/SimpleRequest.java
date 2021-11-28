package firenze.project.rest.domain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleRequest {
    private String path;
    private String method;
    private String queryParams;
    private Object requestBody;

    @SneakyThrows
    public static SimpleRequest of(HttpServletRequest request) {
        return SimpleRequest.builder()
                .path(request.getRequestURI())
                .method(request.getMethod())
                .queryParams(request.getQueryString())
                .requestBody(request.getReader())
                .build();
    }
}
