package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.UUID;

public class NationGroup implements Group {
    private final Nation nation;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public NationGroup(UUID member) {
        KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(member);
        if (kingdomPlayer.hasKingdom()) {
            Kingdom k = kingdomPlayer.getKingdom();
            this.nation = k.hasNation() ? k.getNation() : null;
        } else {
            this.nation = null;
        }
    }

    public NationGroup(Nation nation) {
        this.nation = nation;
    }

    public Nation nation() {
        return nation;
    }

    public Nation getNation() {
        return nation;
    }

    @Override
    public boolean handled() {
        return !exists() || owner() != null;
    }

    @Override
    public boolean exists() {
        return nation != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = nation != null ? nation.getCapital().getKingId() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = nation != null ? nation.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
