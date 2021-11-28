package firenze.project.rest;

import firenze.project.rest.context.RestContext;
import firenze.project.rest.domain.Resolver;
import firenze.project.rest.domain.ResourceContainer;
import firenze.project.rest.domain.ResourceInstance;
import firenze.project.rest.domain.SimpleRequest;
import firenze.project.rest.exception.HttpMethodNotFoundException;
import firenze.project.rest.exception.InvalidResourceClassException;
import firenze.project.rest.exception.NoResourceFoundException;
import firenze.project.rest.utils.PathUtils;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceResolver {
    private final RestContext context;
    private final Resolver resourceContainer;

    public ResourceResolver(RestContext context) {
        this.context = context;
        this.resourceContainer = initResourceContainer(context.getMainClass());
    }

    private Resolver initResourceContainer(Class<?> mainClass) {
        Reflections reflections = new Reflections(mainClass.getPackageName());
        return build(reflections.getTypesAnnotatedWith(Path.class));
    }

    public Resolver build(Set<Class<?>> rootResourceClass) {
        return ResourceContainer.builder()
                .subResources(rootResourceClass.stream().map(clazz ->
                {
                    String parentPath = PathUtils.concat("", clazz.getAnnotation(Path.class).value());
                    return build(clazz, parentPath);
                }).collect(Collectors.toList()))
                .build();
    }

    public Resolver build(Class<?> clazz, String parentPath) {
        Object clazzObject = context.getContainer().get(clazz);
        return build(clazzObject, parentPath);
    }

    private Resolver build(Object clazzObject, String parentPath) {
        Class<?> clazz = clazzObject.getClass();
        List<Resolver> subContainerResource = Stream.of(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Path.class) && !containHttpMethodAnnotation(method))
                .map(method -> buildSubResource(clazzObject, method, parentPath)).collect(Collectors.toList());
        List<Resolver> resourceInstances = Stream.of(clazz.getMethods())
                .filter(this::containHttpMethodAnnotation)
                .map(method -> buildInstance(clazz, method, parentPath))
                .collect(Collectors.toList());
        if (subContainerResource.isEmpty() && resourceInstances.isEmpty()) {
            throw new InvalidResourceClassException();
        } else if (subContainerResource.isEmpty() && Objects.equals(1, resourceInstances.size())) {
            return resourceInstances.get(0);
        }
        return ResourceContainer.builder()
                .subResources(Stream.concat(subContainerResource.stream(), resourceInstances.stream())
                        .filter(Objects::nonNull).collect(Collectors.toList()))
                .build();
    }

    private Resolver buildInstance(Class<?> clazz, Method method, String path) {
        String fullPath = method.isAnnotationPresent(Path.class) ?
                PathUtils.concat(path, method.getAnnotation(Path.class).value()) : path;
        return ResourceInstance.builder()
                .method(method)
                .path(fullPath)
                .httpMethod(getHttpMethodAnnotation(method))
                .instance(context.getContainer().get(clazz))
                .build();
    }

    private String getHttpMethodAnnotation(Method method) {
        return Arrays.stream(method.getAnnotations())
                .filter(this::annotateByHttpMethod)
                .map(this::getAnnotatedHttpMethod).findFirst()
                .orElseThrow(HttpMethodNotFoundException::new);
    }

    private String getAnnotatedHttpMethod(Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getAnnotations())
                .filter(it -> Objects.equals(it.annotationType(), HttpMethod.class))
                .findFirst()
                .map(it -> ((HttpMethod) it).value())
                .orElseThrow(HttpMethodNotFoundException::new);
    }

    private boolean containHttpMethodAnnotation(Method method) {
        return Arrays.stream(method.getAnnotations()).anyMatch(this::annotateByHttpMethod);
    }

    private boolean annotateByHttpMethod(Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getAnnotations())
                .anyMatch(annotation1 -> Objects.equals(annotation1.annotationType(), HttpMethod.class));
    }

    @SneakyThrows
    private Resolver buildSubResource(Object clazzObject, Method method, String parentPath) {
        return build(method.invoke(clazzObject), PathUtils.concat(parentPath, method.getAnnotation(Path.class).value()));
    }

    public ResourceInstance resolve(SimpleRequest request) {
        return resourceContainer.resolve(request);
    }
}
