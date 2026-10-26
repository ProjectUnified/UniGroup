package io.github.projectunified.unigroup.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class TownGroup implements Group {
    private final boolean handled;
    private final Town town;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public TownGroup(UUID member) {
        Resident res = TownyAPI.getInstance().getResident(member);
        if (res == null) {
            this.handled = false;
            this.town = null;
        } else {
            this.handled = true;
            this.town = res.getTownOrNull();
        }
    }

    public TownGroup(Town town) {
        this.handled = true;
        this.town = town;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public Town town() {
        return town;
    }

    public Town getTown() {
        return town;
    }

    @Override
    public boolean exists() {
        return town != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = town != null ? town.getMayor().getUUID() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = town != null ? town.getFormattedName() : null;
            nameSet = true;
        }
        return name;
    }
}
