package io.github.projectunified.unigroup.core;

import org.jetbrains.annotations.Nullable;

/**
 * The response when querying a group from {@link GroupProvider}.
 * Contains the error getter, the handled boolean method, and the {@link Group} interface object.
 */
public interface GroupResponse {
    /**
     * Create a handled {@link GroupResponse} with the given group.
     *
     * @param group the group
     * @return the group response
     */
    static GroupResponse of(Group group) {
        return of(null, true, group);
    }

    /**
     * Create a handled {@link GroupResponse} with the given group.
     *
     * @param group the group
     * @return the group response
     */
    static GroupResponse handled(Group group) {
        return of(null, true, group);
    }

    /**
     * Create an unhandled {@link GroupResponse}.
     *
     * @return the unhandled group response
     */
    static GroupResponse notHandled() {
        return of(null, false, null);
    }

    /**
     * Create an error {@link GroupResponse} with the given throwable.
     *
     * @param throwable the error cause
     * @return the error group response
     */
    static GroupResponse error(Throwable throwable) {
        return of(throwable, false, null);
    }

    /**
     * Create a {@link GroupResponse} with the given error, handled status, and group.
     *
     * @param error   the error
     * @param handled whether it was handled
     * @param group   the group
     * @return the group response
     */
    static GroupResponse of(Throwable error, boolean handled, Group group) {
        return new GroupResponse() {
            @Override
            public Throwable error() {
                return error;
            }

            @Override
            public boolean handled() {
                return handled;
            }

            @Override
            public Group group() {
                return group;
            }
        };
    }

    /**
     * Get the error when constructing or querying the group.
     *
     * @return the optional throwable error
     */
    default @Nullable Throwable error() {
        return null;
    }

    /**
     * Check if the group query can be handled by the provider.
     *
     * @return true if handled, otherwise false
     */
    default boolean handled() {
        return true;
    }

    /**
     * Get the group interface object.
     *
     * @return the group interface object, or null if not handled or error
     */
    @Nullable Group group();
}
