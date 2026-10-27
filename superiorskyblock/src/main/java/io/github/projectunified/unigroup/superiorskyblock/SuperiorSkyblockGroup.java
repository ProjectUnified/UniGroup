package io.github.projectunified.unigroup.superiorskyblock;

import com.bgsoftware.superiorskyblock.api.island.Island;
import io.github.projectunified.unigroup.core.Group;

import java.util.Optional;
import java.util.UUID;

public class SuperiorSkyblockGroup implements Group {
    private final Island island;

    public SuperiorSkyblockGroup(Island island) {
        this.island = island;
    }

    public Island island() {
        return island;
    }

    @Override
    public boolean exists() {
        return island != null;
    }

    @Override
    public UUID owner() {
        return island != null ? island.getOwner().getUniqueId() : null;
    }

    @Override
    public String name() {
        return island != null ? Optional.of(island.getName()).filter(s -> !s.isEmpty()).orElseGet(() -> island.getOwner().getName()) : null;
    }
}
