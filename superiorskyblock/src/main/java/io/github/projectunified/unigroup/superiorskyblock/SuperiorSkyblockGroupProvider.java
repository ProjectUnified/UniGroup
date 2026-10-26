package io.github.projectunified.unigroup.superiorskyblock;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SuperiorSkyblockGroupProvider implements GroupProvider {

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new SuperiorSkyblockGroup(member);
    }
}
