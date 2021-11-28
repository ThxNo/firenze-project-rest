package firenze.project.rest.domain;

import firenze.project.rest.exception.NoResourceFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceContainer implements Resolver {
    private List<Resolver> subResources;

    @Override
    public ResourceInstance resolve(SimpleRequest request) {
        return subResources.stream()
                .map(subResource -> subResource.resolve(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
