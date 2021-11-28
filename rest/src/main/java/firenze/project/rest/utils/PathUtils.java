package firenze.project.rest.utils;

import firenze.project.rest.exception.NoPathParamMatchedException;

import java.util.Objects;
import java.util.regex.Pattern;

public class PathUtils {
    private static final String PATH_SEPARATOR = "/";
    private static final String PATH_PARAM_REGEX = "^\\{([a-zA-Z_-]+)\\}$";

    public static String concat(String parentPath, String value) {
        if (Objects.isNull(parentPath) || parentPath.isEmpty()) {
            return PATH_SEPARATOR + getUrlContent(value);
        }
        return PATH_SEPARATOR + getUrlContent(parentPath) + PATH_SEPARATOR + getUrlContent(value);
    }

    public static String getUrlContent(String url) {
        url = url.startsWith(PATH_SEPARATOR) ? url.substring(1) : url;
        url = url.endsWith(PATH_SEPARATOR) ? url.substring(0, url.length() - 1) : url;
        return url;
    }

    public static boolean isMatch(String requestPath, String resourcePath) {
        String[] requestPathResource = requestPath.split(PATH_SEPARATOR);
        String[] resourcePathResource = resourcePath.split(PATH_SEPARATOR);
        if (requestPathResource.length != resourcePathResource.length) {
            return false;
        }
        for (int i = 0; i < requestPathResource.length; i++) {
            if (!isPathParam(resourcePathResource[i]) && !requestPathResource[i].equals(resourcePathResource[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPathParam(String pathParam) {
        return Pattern.matches(PATH_PARAM_REGEX, pathParam);
    }

    public static String getPathParam(String pathParam, String requestPath, String resourcePath) {
        String[] resourcePathResources = resourcePath.split(PATH_SEPARATOR);
        String[] requestPathResources = requestPath.split(PATH_SEPARATOR);
        for (int i = 0; i < resourcePathResources.length && i < requestPathResources.length; i++) {
            if (isPathParam(resourcePathResources[i])
                    && pathParam.equals(resourcePathResources[i].replaceAll("[\\{\\}]", ""))) {
                return requestPathResources[i];
            }
        }
        throw new NoPathParamMatchedException();
    }
}
