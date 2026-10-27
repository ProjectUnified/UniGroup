package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import org.kingdoms.constants.group.Kingdom;

import java.util.UUID;

public class KingdomGroup implements Group {
    private final Kingdom kingdom;

    public KingdomGroup(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom kingdom() {
        return kingdom;
    }

    @Override
    public boolean exists() {
        return kingdom != null;
    }

    @Override
    public UUID owner() {
        return kingdom != null ? kingdom.getKingId() : null;
    }

    @Override
    public String name() {
        return kingdom != null ? kingdom.getName() : null;
    }
}
