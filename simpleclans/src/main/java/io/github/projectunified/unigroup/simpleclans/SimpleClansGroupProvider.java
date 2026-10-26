package io.github.projectunified.unigroup.simpleclans;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SimpleClansGroupProvider implements GroupProvider {

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("SimpleClans") != null;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new SimpleClansGroup(member);
    }
}
