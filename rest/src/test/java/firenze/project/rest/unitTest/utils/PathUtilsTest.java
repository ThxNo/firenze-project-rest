package firenze.project.rest.unitTest.utils;

import firenze.project.rest.utils.PathUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathUtilsTest {
    @Test
    void should_concat_uri_path() {
        //given
        String parentPath = "/users/1/shopping-cart/";
        String value = "/{id}/";
        //when
        String result = PathUtils.concat(parentPath, value);
        //then
        assertThat(result).isEqualTo("/users/1/shopping-cart/{id}");
    }
}