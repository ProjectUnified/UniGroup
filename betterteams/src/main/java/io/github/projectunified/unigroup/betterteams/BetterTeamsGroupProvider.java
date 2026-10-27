package io.github.projectunified.unigroup.betterteams;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BetterTeamsGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BetterTeams") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new BetterTeamsGroupResponse(member);
    }
}
