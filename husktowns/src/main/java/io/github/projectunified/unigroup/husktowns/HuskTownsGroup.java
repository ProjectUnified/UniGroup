package io.github.projectunified.unigroup.husktowns;

import io.github.projectunified.unigroup.core.Group;
import net.william278.husktowns.api.HuskTownsAPI;
import net.william278.husktowns.town.Town;

import java.util.UUID;

public class HuskTownsGroup implements Group {
    private final boolean handled;
    private final Town town;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public HuskTownsGroup(UUID member) {
        HuskTownsAPI api = HuskTownsAPI.getInstance();
        if (!api.isLoaded()) {
            this.handled = false;
            this.town = null;
        } else {
            this.handled = true;
            this.town = api.getTowns().stream()
                    .filter(t -> t.getMembers().containsKey(member))
                    .findFirst()
                    .orElse(null);
        }
    }

    public HuskTownsGroup(Town town) {
        this.handled = true;
        this.town = town;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public Town town() {
        return town;
    }

    public Town getTown() {
        return town;
    }

    @Override
    public boolean exists() {
        return town != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = town != null ? town.getMayor() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = town != null ? town.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
