package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.Group;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.player.LandPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class LandGroup implements Group {
    private final boolean handled;
    private final Land land;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public LandGroup(Plugin plugin, UUID member) {
        LandsIntegration api = LandsIntegration.of(plugin != null ? plugin : Bukkit.getPluginManager().getPlugin("Lands"));
        LandPlayer landPlayer = api.getLandPlayer(member);
        if (landPlayer == null) {
            this.handled = false;
            this.land = null;
        } else {
            this.handled = true;
            this.land = landPlayer.getOwningLand();
        }
    }

    public LandGroup(Land land) {
        this.handled = true;
        this.land = land;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public boolean isHandled() {
        return handled;
    }

    public Land land() {
        return land;
    }

    public Land getLand() {
        return land;
    }

    @Override
    public boolean exists() {
        return land != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = land != null ? land.getOwnerUID() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = land != null ? land.getColorName() : null;
            nameSet = true;
        }
        return name;
    }
}
