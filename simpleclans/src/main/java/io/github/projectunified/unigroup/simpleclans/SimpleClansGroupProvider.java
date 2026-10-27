package io.github.projectunified.unigroup.simpleclans;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SimpleClansGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("SimpleClans") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new SimpleClansGroupResponse(member);
    }
}
