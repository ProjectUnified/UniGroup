package io.github.projectunified.unigroup.factionsuuid;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class FactionsUUIDGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Factions") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new FactionsUUIDGroupResponse(member);
    }
}
