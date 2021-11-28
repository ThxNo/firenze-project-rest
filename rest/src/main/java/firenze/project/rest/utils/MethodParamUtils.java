package firenze.project.rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.exception.InvalidMethodParameterException;
import jakarta.ws.rs.PathParam;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class MethodParamUtils {
    public static Object[] resolve(Method method, SimpleRequest request, String path) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> resolvePathParam(parameter, request.getPath(), path))
                .toArray();
    }

    @SneakyThrows
    private static Object resolvePathParam(Parameter parameter, String requestPath, String resourcePath) {
        if (!isValid(parameter)) {
            throw new InvalidMethodParameterException();
        }
        String paramName = parameter.getAnnotation(PathParam.class).value();
        String pathParam = PathUtils.getPathParam(paramName, requestPath, resourcePath);
        return new ObjectMapper().readValue(pathParam, parameter.getType());
    }

    private static boolean isValid(Parameter parameter) {
        return parameter.isAnnotationPresent(PathParam.class);
    }
}
