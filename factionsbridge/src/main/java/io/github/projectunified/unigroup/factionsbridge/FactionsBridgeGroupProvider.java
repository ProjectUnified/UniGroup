package io.github.projectunified.unigroup.factionsbridge;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class FactionsBridgeGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FactionsBridge") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new FactionsBridgeGroupResponse(member);
    }
}
