package io.github.projectunified.unigroup.towny;

import com.palmergames.bukkit.towny.object.Nation;
import io.github.projectunified.unigroup.core.Group;

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
        return nation != null ? nation.getKing().getUUID() : null;
    }

    @Override
    public String name() {
        return nation != null ? nation.getFormattedName() : null;
    }
}
