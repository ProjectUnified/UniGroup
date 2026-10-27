package io.github.projectunified.unigroup.husktowns;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class HuskTownsGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HuskTowns") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new HuskTownsGroupResponse(member);
    }
}
