package io.github.projectunified.unigroup.parties;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.Party;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class PartiesGroup implements Group {
    private final Party party;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public PartiesGroup(UUID member) {
        this.party = Parties.getApi().getPartyOfPlayer(member);
    }

    public PartiesGroup(Party party) {
        this.party = party;
    }

    public Party party() {
        return party;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public boolean exists() {
        return party != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = party != null ? party.getLeader() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = party != null ? party.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
