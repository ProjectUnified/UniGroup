package io.github.projectunified.unigroup.core;

import java.util.UUID;

/**
 * The interface to get information from the group.
 * Should check if the group is handled by calling {@link #handled()} and exists by calling {@link #exists()} before calling other methods.
 */
public interface Group {
    /**
     * Check if the group can be handled by the provider.
     *
     * @return true if handled, otherwise false
     */
    default boolean handled() {
        return true;
    }

    /**
     * Check if the group exists
     *
     * @return true if it does, otherwise false
     */
    boolean exists();

    /**
     * Get the owner of the group.
     *
     * @return the owner UUID, or null if the group does not exist
     */
    UUID owner();

    /**
     * Get the name of the group.
     *
     * @return the name, or null if the group does not exist
     */
    String name();
}
