package io.github.projectunified.unigroup.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class NationGroup implements Group {
    private final boolean handled;
    private final Nation nation;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public NationGroup(UUID member) {
        Resident res = TownyAPI.getInstance().getResident(member);
        if (res == null) {
            this.handled = false;
            this.nation = null;
        } else {
            this.handled = true;
            this.nation = res.getNationOrNull();
        }
    }

    public NationGroup(Nation nation) {
        this.handled = true;
        this.nation = nation;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public Nation nation() {
        return nation;
    }

    public Nation getNation() {
        return nation;
    }

    @Override
    public boolean exists() {
        return nation != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = nation != null ? nation.getKing().getUUID() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = nation != null ? nation.getFormattedName() : null;
            nameSet = true;
        }
        return name;
    }
}
