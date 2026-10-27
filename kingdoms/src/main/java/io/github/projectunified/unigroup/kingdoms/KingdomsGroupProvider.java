package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class KingdomsGroupProvider implements GroupProvider {
    private final KingdomsGroupType type;

    public KingdomsGroupProvider(KingdomsGroupType type) {
        this.type = type;
    }

    public KingdomsGroupProvider() {
        this(KingdomsGroupType.KINGDOM);
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Kingdoms") != null;
    }

    public KingdomsGroupType getType() {
        return type;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new KingdomsGroupResponse(type, member);
    }
}
