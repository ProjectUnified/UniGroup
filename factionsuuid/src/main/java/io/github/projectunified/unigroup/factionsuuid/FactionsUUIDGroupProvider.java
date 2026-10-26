package io.github.projectunified.unigroup.factionsuuid;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class FactionsUUIDGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FactionsUUID") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new FactionsUUIDGroup(member);
    }
}
