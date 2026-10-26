package io.github.projectunified.unigroup.factionsuuid;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.Faction;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class FactionsUUIDGroup implements Group {
    private final Faction faction;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public FactionsUUIDGroup(UUID member) {
        FPlayer fPlayer = FPlayers.fPlayers().get(member);
        if (fPlayer.hasFaction()) {
            this.faction = fPlayer.faction();
        } else {
            this.faction = null;
        }
    }

    public FactionsUUIDGroup(Faction faction) {
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
                FPlayer admin = faction.admin();
                if (admin != null) {
                    owner = admin.uniqueId();
                }
            }
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = faction != null ? faction.tag() : null;
            nameSet = true;
        }
        return name;
    }
}
