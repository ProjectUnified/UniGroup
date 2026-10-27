package io.github.projectunified.unigroup.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.UUID;

public class TownyGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;
    private final Throwable error;

    public TownyGroupResponse(TownyGroupType type, UUID member) {
        if (type == null) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Invalid towny type, must be TOWN or NATION");
            return;
        }

        Resident resident = TownyAPI.getInstance().getResident(member);
        if (resident == null) {
            this.handled = false;
            this.group = null;
            this.error = null;
            return;
        }

        this.handled = true;
        this.error = null;
        if (type == TownyGroupType.NATION) {
            Nation nation = resident.getNationOrNull();
            this.group = new NationGroup(nation);
        } else {
            Town town = resident.getTownOrNull();
            this.group = new TownGroup(town);
        }
    }

    @Override
    public Throwable error() {
        return error;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    @Override
    public Group group() {
        return group;
    }
}
