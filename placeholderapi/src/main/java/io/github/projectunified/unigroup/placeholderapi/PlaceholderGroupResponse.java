package io.github.projectunified.unigroup.placeholderapi;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class PlaceholderGroupResponse implements GroupResponse {
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
    private final Group group;
    private final Throwable error;

    public PlaceholderGroupResponse(String ownerPlaceholder, String displayPlaceholder, boolean onlineOnly, UUID member) {
        if (ownerPlaceholder == null || ownerPlaceholder.isEmpty()) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Owner is not set");
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(member);
        if (onlineOnly && !player.isOnline()) {
            this.handled = false;
            this.group = null;
            this.error = null;
            return;
        }

        String rawOwner = PlaceholderAPI.setPlaceholders(player, ownerPlaceholder);
        Optional<UUID> optionalOwner = normalize(rawOwner);
        if (!optionalOwner.isPresent()) {
            this.handled = true;
            this.group = new PlaceholderGroup(null, null);
            this.error = null;
            return;
        }

        UUID owner = optionalOwner.get();
        String groupName;
        if (displayPlaceholder == null || displayPlaceholder.isEmpty()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(owner);
            groupName = op.getName() != null ? op.getName() : "";
        } else {
            groupName = PlaceholderAPI.setPlaceholders(player, displayPlaceholder);
        }

        this.handled = true;
        this.error = null;
        this.group = new PlaceholderGroup(owner, groupName);
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
}
