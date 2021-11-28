package firenze.project.rest.domain;

import firenze.project.rest.exception.ResourceHandleException;
import firenze.project.rest.utils.MethodParamUtils;
import firenze.project.rest.utils.PathUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceInstance implements Resolver {
    private String httpMethod;
    private String path;
    private Method method;
    private Object instance;

    @Override
    public ResourceInstance resolve(SimpleRequest request) {
        if (isMatched(request)) {
            return this;
        }
        return null;
    }

    private boolean isMatched(SimpleRequest request) {
        return request.getMethod().equals(httpMethod) && PathUtils.isMatch(request.getPath(), path);
    }

    public SimpleResponse handle(SimpleRequest request) {
        try {
            Object[] parameters = MethodParamUtils.resolve(method, request, path);
            return SimpleResponse.builder().statusCode(200).responseBody(method.invoke(instance, parameters)).build();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ResourceHandleException();
        }
    }
}
