package io.github.projectunified.unigroup.factionsbridge;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class FactionsBridgeGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FactionsBridge") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new FactionsBridgeGroup(member);
    }
}
