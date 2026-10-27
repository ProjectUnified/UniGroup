package io.github.projectunified.unigroup.mcmmo;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class McMMOGroupProvider implements GroupProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("mcMMO") != null;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new McMMOGroupResponse(member);
    }
}
