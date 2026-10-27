package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.UUID;

public class KingdomsGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;
    private final Throwable error;

    public KingdomsGroupResponse(KingdomsGroupType type, UUID member) {
        if (type == null) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Invalid kingdom type, must be KINGDOM or NATION");
            return;
        }

        KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(member);
        if (kingdomPlayer == null) {
            this.handled = false;
            this.group = null;
            this.error = null;
            return;
        }

        if (!kingdomPlayer.hasKingdom()) {
            this.handled = true;
            this.group = type == KingdomsGroupType.NATION ? new NationGroup(null) : new KingdomGroup(null);
            this.error = null;
            return;
        }

        Kingdom kingdom = kingdomPlayer.getKingdom();
        if (type == KingdomsGroupType.KINGDOM) {
            if (kingdom.getKingId() == null) {
                this.handled = false;
                this.group = null;
                this.error = null;
                return;
            }

            this.handled = true;
            this.error = null;
            this.group = new KingdomGroup(kingdom);
        } else {
            Nation nation = kingdom.getNation();
            if (nation == null) {
                this.handled = true;
                this.group = new NationGroup(null);
                this.error = null;
                return;
            }

            Kingdom capital = nation.getCapital();
            if (capital == null || capital.getKingId() == null) {
                this.handled = false;
                this.group = null;
                this.error = null;
                return;
            }

            this.handled = true;
            this.error = null;
            this.group = new NationGroup(nation);
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
