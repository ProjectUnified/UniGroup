package io.github.projectunified.unigroup.husktowns;

import io.github.projectunified.unigroup.core.Group;
import net.william278.husktowns.town.Town;

import java.util.UUID;

public class HuskTownsGroup implements Group {
    private final Town town;

    public HuskTownsGroup(Town town) {
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
        return town != null ? town.getMayor() : null;
    }

    @Override
    public String name() {
        return town != null ? town.getName() : null;
    }
}
