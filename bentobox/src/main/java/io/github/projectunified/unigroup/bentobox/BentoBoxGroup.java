package io.github.projectunified.unigroup.bentobox;

import io.github.projectunified.unigroup.core.Group;
import org.bukkit.Bukkit;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;
import java.util.UUID;

public class BentoBoxGroup implements Group {
    private final Island island;

    public BentoBoxGroup(Island island) {
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
        return island != null ? island.getOwner() : null;
    }

    @Override
    public String name() {
        if (island == null) {
            return null;
        }
        UUID ownerId = owner();
        return Optional.ofNullable(island.getName()).orElseGet(() -> ownerId != null ? Bukkit.getOfflinePlayer(ownerId).getName() : null);
    }
}
