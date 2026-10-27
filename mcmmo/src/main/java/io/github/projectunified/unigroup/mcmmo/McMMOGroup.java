package io.github.projectunified.unigroup.mcmmo;

import com.gmail.nossr50.datatypes.party.Party;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class McMMOGroup implements Group {
    private final Party party;

    public McMMOGroup(Party party) {
        this.party = party;
    }

    public Party party() {
        return party;
    }

    @Override
    public boolean exists() {
        return party != null;
    }

    @Override
    public UUID owner() {
        return party != null && party.getLeader() != null ? party.getLeader().getUniqueId() : null;
    }

    @Override
    public String name() {
        return party != null ? party.getName() : null;
    }
}
