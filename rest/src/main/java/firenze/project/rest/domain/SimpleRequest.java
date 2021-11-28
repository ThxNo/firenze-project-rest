package firenze.project.rest.domain;

import firenze.project.rest.utils.PathUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleRequest {
    private String path;
    private String method;
    private Map<String, String> queryParams;
    private Object requestBody;

    @SneakyThrows
    public static SimpleRequest of(HttpServletRequest request) {
        return SimpleRequest.builder()
                .path(request.getRequestURI())
                .method(request.getMethod())
                .queryParams(PathUtils.toQueryMap(request.getQueryString()))
                .requestBody(request.getReader())
                .build();
    }
}
