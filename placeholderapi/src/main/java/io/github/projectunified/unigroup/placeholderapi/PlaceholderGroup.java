package io.github.projectunified.unigroup.placeholderapi;

import io.github.projectunified.unigroup.core.Group;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings("JavaReflectionMemberAccess")
public class PlaceholderGroup implements Group {
    private static final Function<String, OfflinePlayer> GET_OFFLINE_PLAYER_FUNCTION;

    static {
        Function<String, OfflinePlayer> function;
        try {
            Method method = Bukkit.class.getMethod("getOfflinePlayerIfCached", String.class);
            function = name -> {
                try {
                    return (OfflinePlayer) method.invoke(null, name);
                } catch (ReflectiveOperationException e) {
                    return null;
                }
            };
        } catch (NoSuchMethodException e) {
            function = name -> {
                @SuppressWarnings("deprecation")
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                return offlinePlayer.hasPlayedBefore() ? offlinePlayer : null;
            };
        }
        GET_OFFLINE_PLAYER_FUNCTION = function;
    }

    private final boolean handled;
    private final OfflinePlayer player;
    private final String ownerPlaceholder;
    private final String displayPlaceholder;
    private final boolean onlineOnly;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public PlaceholderGroup(UUID member, String ownerPlaceholder, String displayPlaceholder, boolean onlineOnly) {
        this.ownerPlaceholder = ownerPlaceholder;
        this.displayPlaceholder = displayPlaceholder;
        this.onlineOnly = onlineOnly;
        this.player = Bukkit.getOfflinePlayer(member);
        this.handled = !onlineOnly || player.isOnline();
    }

    public PlaceholderGroup(OfflinePlayer player, String ownerPlaceholder, String displayPlaceholder, boolean onlineOnly) {
        this.ownerPlaceholder = ownerPlaceholder;
        this.displayPlaceholder = displayPlaceholder;
        this.onlineOnly = onlineOnly;
        this.player = player;
        this.handled = !onlineOnly || player.isOnline();
    }

    private static Optional<UUID> normalize(String nameOrUuid) {
        if (nameOrUuid == null || nameOrUuid.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(UUID.fromString(nameOrUuid));
        } catch (IllegalArgumentException ignored) {
        }

        try {
            OfflinePlayer offlinePlayer = GET_OFFLINE_PLAYER_FUNCTION.apply(nameOrUuid);
            return Optional.ofNullable(offlinePlayer).map(OfflinePlayer::getUniqueId);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public String ownerPlaceholder() {
        return ownerPlaceholder;
    }

    public String getOwnerPlaceholder() {
        return ownerPlaceholder;
    }

    public String displayPlaceholder() {
        return displayPlaceholder;
    }

    public String getDisplayPlaceholder() {
        return displayPlaceholder;
    }

    public boolean isOnlineOnly() {
        return onlineOnly;
    }

    @Override
    public boolean exists() {
        return owner() != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            String rawOwner = (ownerPlaceholder != null && !ownerPlaceholder.isEmpty())
                    ? PlaceholderAPI.setPlaceholders(player, ownerPlaceholder)
                    : null;
            owner = normalize(rawOwner).orElse(null);
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            UUID ownerId = owner();
            if (ownerId != null) {
                if (displayPlaceholder != null && !displayPlaceholder.isEmpty()) {
                    name = PlaceholderAPI.setPlaceholders(player, displayPlaceholder);
                } else {
                    String fetchedName = Bukkit.getOfflinePlayer(ownerId).getName();
                    name = fetchedName != null ? fetchedName : "";
                }
            } else {
                name = null;
            }
            nameSet = true;
        }
        return name;
    }
}
