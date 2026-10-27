package io.github.projectunified.unigroup.core;

import java.util.UUID;

/**
 * The interface to fetch groups
 */
public interface GroupProvider {
    /**
     * Get the group response by the member id
     *
     * @param member the id of the member
     * @return the group response
     */
    GroupResponse byMember(UUID member);
}
