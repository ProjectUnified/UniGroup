package io.github.projectunified.unigroup.placeholderapi;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PlaceholderGroupProvider implements GroupProvider {
    private final String ownerPlaceholder;
    private final String displayPlaceholder;
    private final boolean onlineOnly;

    public PlaceholderGroupProvider(String ownerPlaceholder, String displayPlaceholder, boolean onlineOnly) {
        this.ownerPlaceholder = ownerPlaceholder;
        this.displayPlaceholder = displayPlaceholder;
        this.onlineOnly = onlineOnly;
    }

    public PlaceholderGroupProvider(String ownerPlaceholder, String displayPlaceholder) {
        this(ownerPlaceholder, displayPlaceholder, false);
    }

    public PlaceholderGroupProvider(String ownerPlaceholder) {
        this(ownerPlaceholder, null, false);
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public String getOwnerPlaceholder() {
        return ownerPlaceholder;
    }

    public String getDisplayPlaceholder() {
        return displayPlaceholder;
    }

    public boolean isOnlineOnly() {
        return onlineOnly;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new PlaceholderGroupResponse(ownerPlaceholder, displayPlaceholder, onlineOnly, member);
    }
}
