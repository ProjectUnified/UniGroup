package io.github.projectunified.unigroup.builder;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * A fallback {@link GroupProvider} representing an error during creation or configuration.
 */
public class ErrorGroupProvider implements GroupProvider {
    private final Throwable throwable;

    /**
     * Create a new ErrorGroupProvider with the given throwable.
     *
     * @param throwable the error cause
     */
    public ErrorGroupProvider(Throwable throwable) {
        this.throwable = Objects.requireNonNull(throwable, "throwable");
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Optional<Throwable> getError() {
        return Optional.of(throwable);
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return GroupResponse.error(throwable);
    }

    @Override
    public String toString() {
        return "ErrorGroupProvider{error=" + throwable.getMessage() + "}";
    }
}
