package io.github.projectunified.unigroup.parties;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PartiesGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Parties") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new PartiesGroupResponse(member);
    }
}
