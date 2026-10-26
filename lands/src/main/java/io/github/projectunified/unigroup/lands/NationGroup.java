package io.github.projectunified.unigroup.lands;

import io.github.projectunified.unigroup.core.Group;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.nation.Nation;
import me.angeschossen.lands.api.player.LandPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class NationGroup implements Group {
    private final boolean handled;
    private final Nation nation;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public NationGroup(Plugin plugin, UUID member) {
        LandsIntegration api = LandsIntegration.of(plugin != null ? plugin : Bukkit.getPluginManager().getPlugin("Lands"));
        LandPlayer landPlayer = api.getLandPlayer(member);
        if (landPlayer == null) {
            this.handled = false;
            this.nation = null;
        } else {
            this.handled = true;
            Land owningLand = landPlayer.getOwningLand();
            this.nation = owningLand != null ? owningLand.getNation() : null;
        }
    }

    public NationGroup(Nation nation) {
        this.handled = true;
        this.nation = nation;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public boolean isHandled() {
        return handled;
    }

    public Nation nation() {
        return nation;
    }

    public Nation getNation() {
        return nation;
    }

    @Override
    public boolean exists() {
        return nation != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = nation != null ? nation.getCapital().getOwnerUID() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = nation != null ? nation.getColorName() : null;
            nameSet = true;
        }
        return name;
    }
}
