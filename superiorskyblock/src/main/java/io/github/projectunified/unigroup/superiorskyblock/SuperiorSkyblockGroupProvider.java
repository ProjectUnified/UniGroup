package io.github.projectunified.unigroup.superiorskyblock;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SuperiorSkyblockGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new SuperiorSkyblockGroupResponse(member);
    }
}
