package io.github.projectunified.unigroup.mcmmo;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class McMMOGroupProvider implements GroupProvider {

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("mcMMO") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new McMMOGroup(member);
    }
}
