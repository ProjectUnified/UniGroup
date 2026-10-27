package io.github.projectunified.unigroup.factionsuuid;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.Faction;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class FactionsUUIDGroup implements Group {
    private final Faction faction;

    public FactionsUUIDGroup(Faction faction) {
        this.faction = faction;
    }

    public Faction faction() {
        return faction;
    }

    @Override
    public boolean exists() {
        return faction != null;
    }

    @Override
    public UUID owner() {
        if (faction != null) {
            FPlayer admin = faction.admin();
            if (admin != null) {
                return admin.uniqueId();
            }
        }
        return null;
    }

    @Override
    public String name() {
        return faction != null ? faction.tag() : null;
    }
}
