package io.github.projectunified.unigroup.core;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface to fetch groups
 */
public interface GroupProvider {
    /**
     * Get the error when constructing or using the provider
     *
     * @return the optional throwable error
     */
    default Optional<Throwable> getError() {
        return Optional.empty();
    }

    /**
     * Get the group by the member id
     *
     * @param member the id of the member
     * @return the group interface
     */
    Group getGroupByMember(UUID member);
}

