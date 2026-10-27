package io.github.projectunified.unigroup.factionsbridge;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.UUID;

public class FactionsBridgeGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;

    public FactionsBridgeGroupResponse(UUID member) {
        FactionsAPI api = FactionsBridge.getFactionsAPI();
        FPlayer fPlayer = api != null ? api.getFPlayer(member) : null;
        if (fPlayer == null) {
            this.handled = false;
            this.group = null;
            return;
        }

        Faction faction = fPlayer.getFaction();
        if (faction == null) {
            this.handled = true;
            this.group = new FactionsBridgeGroup(null);
            return;
        }

        FPlayer factionLeader = faction.getLeader();
        if (factionLeader == null) {
            this.handled = false;
            this.group = null;
            return;
        }

        this.handled = true;
        this.group = new FactionsBridgeGroup(faction);
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
