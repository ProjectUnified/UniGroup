package io.github.projectunified.unigroup.superiorskyblock;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.UUID;

public class SuperiorSkyblockGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Throwable error;
    private final Group group;

    public SuperiorSkyblockGroupResponse(UUID member) {
        boolean handled;
        Throwable error = null;
        Group group = null;
        try {
            SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(member);
            Island island = player != null ? player.getIsland() : null;
            handled = true;
            group = new SuperiorSkyblockGroup(island);
        } catch (Throwable throwable) {
            handled = false;
            error = throwable;
        }
        this.handled = handled;
        this.group = group;
        this.error = error;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    @Override
    public Throwable error() {
        return error;
    }

    @Override
    public Group group() {
        return group;
    }
}
