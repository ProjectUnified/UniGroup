package io.github.projectunified.unigroup.factionsuuid;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.Faction;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.UUID;

public class FactionsUUIDGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;

    public FactionsUUIDGroupResponse(UUID member) {
        FPlayer fPlayer = FPlayers.fPlayers().get(member);

        if (!fPlayer.hasFaction()) {
            this.handled = true;
            this.group = new FactionsUUIDGroup(null);
            return;
        }

        Faction faction = fPlayer.faction();
        FPlayer factionLeader = faction.admin();
        if (factionLeader == null) {
            this.handled = false;
            this.group = null;
            return;
        }

        this.handled = true;
        this.group = new FactionsUUIDGroup(faction);
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
