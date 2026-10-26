package io.github.projectunified.unigroup.betterteams;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BetterTeamsGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BetterTeams") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new BetterTeamsGroup(member);
    }
}
