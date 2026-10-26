package io.github.projectunified.unigroup.builder;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * A fallback {@link GroupProvider} representing an error during creation or configuration.
 */
public class ErrorGroupProvider implements GroupProvider {
    private static final Group UNHANDLED_GROUP = new Group() {
        @Override
        public boolean handled() {
            return false;
        }

        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public UUID owner() {
            return null;
        }

        @Override
        public String name() {
            return null;
        }
    };

    private final Throwable throwable;

    /**
     * Create a new ErrorGroupProvider with the given throwable.
     *
     * @param throwable the error cause
     */
    public ErrorGroupProvider(Throwable throwable) {
        this.throwable = Objects.requireNonNull(throwable, "throwable");
    }

    @Override
    public Optional<Throwable> getError() {
        return Optional.of(throwable);
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return UNHANDLED_GROUP;
    }

    @Override
    public String toString() {
        return "ErrorGroupProvider{error=" + throwable.getMessage() + "}";
    }
}
