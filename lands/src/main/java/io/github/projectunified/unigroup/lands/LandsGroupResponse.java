package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.nation.Nation;
import me.angeschossen.lands.api.player.LandPlayer;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class LandsGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;
    private final Throwable error;

    public LandsGroupResponse(Plugin plugin, LandsGroupType type, UUID member) {
        if (type == null) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Invalid land type, must be LAND or NATION");
            return;
        }

        LandPlayer landPlayer;
        try {
            LandsIntegration api = LandsIntegration.of(plugin);
            landPlayer = api.getLandPlayer(member);
        } catch (Throwable e) {
            this.handled = false;
            this.group = null;
            this.error = e;
            return;
        }

        if (landPlayer == null) {
            this.handled = false;
            this.group = null;
            this.error = null;
            return;
        }

        Land land = landPlayer.getOwningLand();
        if (land == null) {
            this.handled = true;
            this.group = type == LandsGroupType.NATION ? new NationGroup(null) : new LandGroup(null);
            this.error = null;
            return;
        }

        this.handled = true;
        this.error = null;
        if (type == LandsGroupType.LAND) {
            this.group = new LandGroup(land);
        } else {
            Nation nation = land.getNation();
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
