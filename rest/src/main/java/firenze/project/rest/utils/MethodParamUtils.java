package firenze.project.rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.exception.InvalidMethodParameterException;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class MethodParamUtils {

    private static final List<Class<? extends Annotation>> validAnnotations = List.of(PathParam.class, QueryParam.class);

    public static Object[] resolve(Method method, SimpleRequest request, String path) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> resolveMethodParam(parameter, request, path))
                .toArray();
    }

    @SneakyThrows
    private static Object resolveMethodParam(Parameter parameter, SimpleRequest request, String resourcePath) {
        if (!isValid(parameter)) {
            throw new InvalidMethodParameterException();
        }
        if (parameter.isAnnotationPresent(PathParam.class)) {
            String paramName = parameter.getAnnotation(PathParam.class).value();
            String pathParam = PathUtils.getPathParam(paramName, request.getPath(), resourcePath);
            return new ObjectMapper().readValue(pathParam, parameter.getType());
        } else {
            String paramName = parameter.getAnnotation(QueryParam.class).value();
            String pathParam = PathUtils.getQueryParam(paramName, request.getQueryParams());
            return new ObjectMapper().readValue(pathParam, parameter.getType());
        }
    }

    private static boolean isValid(Parameter parameter) {
        return validAnnotations.stream().anyMatch(parameter::isAnnotationPresent);
    }
}
