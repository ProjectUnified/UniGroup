package io.github.projectunified.unigroup.towny;

import com.palmergames.bukkit.towny.object.Town;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class TownGroup implements Group {
    private final Town town;

    public TownGroup(Town town) {
        this.town = town;
    }

    public Town town() {
        return town;
    }

    @Override
    public boolean exists() {
        return town != null;
    }

    @Override
    public UUID owner() {
        return town != null ? town.getMayor().getUUID() : null;
    }

    @Override
    public String name() {
        return town != null ? town.getFormattedName() : null;
    }
}
