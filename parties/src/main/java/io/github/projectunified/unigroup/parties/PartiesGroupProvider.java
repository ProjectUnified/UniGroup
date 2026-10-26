package io.github.projectunified.unigroup.parties;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PartiesGroupProvider implements GroupProvider {

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Parties") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new PartiesGroup(member);
    }
}
