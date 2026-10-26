package io.github.projectunified.unigroup.superiorskyblock;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import io.github.projectunified.unigroup.core.Group;

import java.util.Optional;
import java.util.UUID;

public class SuperiorSkyblockGroup implements Group {
    private final Island island;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public SuperiorSkyblockGroup(UUID member) {
        this.island = SuperiorSkyblockAPI.getPlayer(member).getIsland();
    }

    public SuperiorSkyblockGroup(Island island) {
        this.island = island;
    }

    public Island island() {
        return island;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public boolean exists() {
        return island != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = island != null ? island.getOwner().getUniqueId() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = island != null ? Optional.of(island.getName()).filter(s -> !s.isEmpty()).orElseGet(() -> island.getOwner().getName()) : null;
            nameSet = true;
        }
        return name;
    }
}
