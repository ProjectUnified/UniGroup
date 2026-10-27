package io.github.projectunified.unigroup.factionsbridge;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class FactionsBridgeGroup implements Group {
    private final Faction faction;

    public FactionsBridgeGroup(Faction faction) {
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
            FPlayer leader = faction.getLeader();
            if (leader != null) {
                return leader.getUniqueId();
            }
        }
        return null;
    }

    @Override
    public String name() {
        return faction != null ? faction.getName() : null;
    }
}
