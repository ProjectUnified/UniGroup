package io.github.projectunified.unigroup.placeholderapi;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class PlaceholderGroupProvider implements GroupProvider {
    private final String ownerPlaceholder;
    private final String displayPlaceholder;
    private final boolean onlineOnly;
    private Optional<Throwable> error;

    public PlaceholderGroupProvider(String ownerPlaceholder, String displayPlaceholder, boolean onlineOnly) {
        this.ownerPlaceholder = ownerPlaceholder != null ? ownerPlaceholder : "";
        this.displayPlaceholder = displayPlaceholder != null ? displayPlaceholder : "";
        this.onlineOnly = onlineOnly;
    }

    public PlaceholderGroupProvider(String ownerPlaceholder, String displayPlaceholder) {
        this(ownerPlaceholder, displayPlaceholder, false);
    }

    public PlaceholderGroupProvider(String ownerPlaceholder) {
        this(ownerPlaceholder, "", false);
    }

    public PlaceholderGroupProvider() {
        this("", "", false);
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

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public Optional<Throwable> getError() {
        if (error == null) {
            if (ownerPlaceholder.isEmpty()) {
                error = Optional.of(new IllegalArgumentException("Owner placeholder is not set"));
            } else {
                error = Optional.empty();
            }
        }
        return error;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new PlaceholderGroup(member, ownerPlaceholder, displayPlaceholder, onlineOnly);
    }
}
