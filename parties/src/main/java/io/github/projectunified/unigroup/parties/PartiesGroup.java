package io.github.projectunified.unigroup.parties;

import com.alessiodp.parties.api.interfaces.Party;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class PartiesGroup implements Group {
    private final Party party;

    public PartiesGroup(Party party) {
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
        return party != null ? party.getLeader() : null;
    }

    @Override
    public String name() {
        return party != null ? party.getName() : null;
    }
}
