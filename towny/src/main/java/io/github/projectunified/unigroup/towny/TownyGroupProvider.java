package io.github.projectunified.unigroup.towny;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TownyGroupProvider implements GroupProvider {
    private final TownyGroupType type;

    public TownyGroupProvider(TownyGroupType type) {
        this.type = type;
    }

    public TownyGroupProvider() {
        this(TownyGroupType.TOWN);
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    public TownyGroupType getType() {
        return type;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new TownyGroupResponse(type, member);
    }
}
