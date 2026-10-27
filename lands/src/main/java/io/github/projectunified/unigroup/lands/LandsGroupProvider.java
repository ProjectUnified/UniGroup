package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class LandsGroupProvider implements GroupProvider {
    private final Plugin plugin;
    private final LandsGroupType type;

    public LandsGroupProvider(Plugin plugin, LandsGroupType type) {
        this.plugin = plugin;
        this.type = type;
    }

    public LandsGroupProvider(Plugin plugin) {
        this(plugin, LandsGroupType.LAND);
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Lands") != null;
    }

    public LandsGroupType getType() {
        return type;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new LandsGroupResponse(plugin, type, member);
    }
}
