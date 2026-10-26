package io.github.projectunified.unigroup.bentobox;

import io.github.projectunified.unigroup.core.Group;
import org.bukkit.Bukkit;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.GameModeAddon;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;
import java.util.UUID;

public class BentoBoxGroup implements Group {
    private final boolean handled;
    private final Island island;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public BentoBoxGroup(UUID member, String gamemode) {
        GameModeAddon gameModeAddon = Optional.ofNullable(gamemode)
                .flatMap(BentoBox.getInstance().getAddonsManager()::getAddonByName)
                .filter(GameModeAddon.class::isInstance)
                .map(GameModeAddon.class::cast)
                .orElse(null);
        if (gameModeAddon == null) {
            this.handled = false;
            this.island = null;
        } else {
            this.handled = true;
            this.island = BentoBox.getInstance().getIslandsManager().getIsland(gameModeAddon.getOverWorld(), member);
        }
    }

    public BentoBoxGroup(Island island) {
        this.handled = true;
        this.island = island;
    }

    public static Optional<Throwable> checkError(String gamemode) {
        GameModeAddon gameModeAddon = Optional.ofNullable(gamemode)
                .flatMap(BentoBox.getInstance().getAddonsManager()::getAddonByName)
                .filter(GameModeAddon.class::isInstance)
                .map(GameModeAddon.class::cast)
                .orElse(null);
        if (gameModeAddon == null) {
            return Optional.of(new IllegalArgumentException("Game mode addon is not specified or not initialized"));
        }
        return Optional.empty();
    }

    public Island island() {
        return island;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    @Override
    public boolean exists() {
        return island != null && owner() != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = island != null ? island.getOwner() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = island != null ? Optional.ofNullable(island.getName()).orElseGet(() -> Bukkit.getOfflinePlayer(owner()).getName()) : null;
            nameSet = true;
        }
        return name;
    }
}
