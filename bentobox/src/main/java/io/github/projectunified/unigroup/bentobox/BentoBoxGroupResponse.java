package io.github.projectunified.unigroup.bentobox;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.GameModeAddon;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.AddonsManager;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.UUID;

public class BentoBoxGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;
    private final Throwable error;

    public BentoBoxGroupResponse(String gamemode, UUID member) {
        BentoBox bentoBox = BentoBox.getInstance();
        if (bentoBox == null) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Plugin is not initialized");
            return;
        }

        AddonsManager addonsManager = bentoBox.getAddonsManager();
        GameModeAddon gameModeAddon = addonsManager == null || gamemode == null ? null : addonsManager.getAddonByName(gamemode)
                .filter(GameModeAddon.class::isInstance)
                .map(GameModeAddon.class::cast)
                .orElse(null);

        if (gameModeAddon == null) {
            this.handled = false;
            this.group = null;
            this.error = new IllegalArgumentException("Game mode addon is not specified or not initialized");
            return;
        }

        IslandsManager islandsManager = bentoBox.getIslandsManager();
        Island island = islandsManager != null ? islandsManager.getIsland(gameModeAddon.getOverWorld(), member) : null;
        this.handled = true;
        this.error = null;
        this.group = new BentoBoxGroup(island);
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
