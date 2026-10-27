package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.Group;
import me.angeschossen.lands.api.land.Land;

import java.util.UUID;

public class LandGroup implements Group {
    private final Land land;

    public LandGroup(Land land) {
        this.land = land;
    }

    public Land land() {
        return land;
    }

    @Override
    public boolean exists() {
        return land != null;
    }

    @Override
    public UUID owner() {
        return land != null ? land.getOwnerUID() : null;
    }

    @Override
    public String name() {
        return land != null ? land.getColorName() : null;
    }
}
