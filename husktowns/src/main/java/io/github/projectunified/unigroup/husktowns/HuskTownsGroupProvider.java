package io.github.projectunified.unigroup.husktowns;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class HuskTownsGroupProvider implements GroupProvider {

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HuskTowns") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new HuskTownsGroup(member);
    }
}
