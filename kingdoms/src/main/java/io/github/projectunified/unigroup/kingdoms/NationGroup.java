package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import org.kingdoms.constants.group.Nation;

import java.util.UUID;

public class NationGroup implements Group {
    private final Nation nation;

    public NationGroup(Nation nation) {
        this.nation = nation;
    }

    public Nation nation() {
        return nation;
    }

    @Override
    public boolean exists() {
        return nation != null;
    }

    @Override
    public UUID owner() {
        return nation != null && nation.getCapital() != null ? nation.getCapital().getKingId() : null;
    }

    @Override
    public String name() {
        return nation != null ? nation.getName() : null;
    }
}
