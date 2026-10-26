package io.github.projectunified.unigroup.factionsbridge;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class FactionsBridgeGroup implements Group {
    private final Faction faction;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public FactionsBridgeGroup(UUID member) {
        FPlayer fPlayer = FactionsBridge.getFactionsAPI().getFPlayer(member);
        this.faction = fPlayer.getFaction();
    }

    public FactionsBridgeGroup(Faction faction) {
        this.faction = faction;
    }

    public Faction faction() {
        return faction;
    }

    public Faction getFaction() {
        return faction;
    }

    @Override
    public boolean handled() {
        return !exists() || owner() != null;
    }

    @Override
    public boolean exists() {
        return faction != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            if (faction != null) {
                FPlayer leader = faction.getLeader();
                owner = leader != null ? leader.getUniqueId() : null;
            }
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = faction != null ? faction.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
