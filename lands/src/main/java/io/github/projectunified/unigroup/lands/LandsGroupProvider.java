package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;

public class LandsGroupProvider implements GroupProvider {
    private final Plugin plugin;
    private final LandsGroupType type;
    private Optional<Throwable> error;

    public LandsGroupProvider(Plugin plugin, LandsGroupType type) {
        this.plugin = plugin;
        this.type = type;
    }

    public LandsGroupProvider(Plugin plugin) {
        this(plugin, LandsGroupType.LAND);
    }

    public LandsGroupProvider(LandsGroupType type) {
        this(null, type);
    }

    public LandsGroupProvider() {
        this(null, LandsGroupType.LAND);
    }

    public LandsGroupType getType() {
        return type;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Lands") != null;
    }

    @Override
    public Optional<Throwable> getError() {
        if (error == null) {
            if (type == null) {
                error = Optional.of(new IllegalArgumentException("Invalid land type, must be LAND or NATION"));
            } else {
                error = Optional.empty();
            }
        }
        return error;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        if (type == LandsGroupType.NATION) {
            return new NationGroup(plugin, member);
        } else if (type == LandsGroupType.LAND) {
            return new LandGroup(plugin, member);
        } else {
            return new Group() {
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
        }
    }
}
