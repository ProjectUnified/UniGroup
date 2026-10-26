package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.UUID;

public class KingdomGroup implements Group {
    private final Kingdom kingdom;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public KingdomGroup(UUID member) {
        KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(member);
        this.kingdom = kingdomPlayer.hasKingdom() ? kingdomPlayer.getKingdom() : null;
    }

    public KingdomGroup(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom kingdom() {
        return kingdom;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    @Override
    public boolean handled() {
        return !exists() || owner() != null;
    }

    @Override
    public boolean exists() {
        return kingdom != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = kingdom != null ? kingdom.getKingId() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = kingdom != null ? kingdom.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
