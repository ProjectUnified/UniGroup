package io.github.projectunified.unigroup.placeholderapi;

import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class PlaceholderGroup implements Group {
    private final UUID owner;
    private final String name;

    public PlaceholderGroup(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    @Override
    public boolean exists() {
        return owner != null;
    }

    @Override
    public UUID owner() {
        return owner;
    }

    @Override
    public String name() {
        return name;
    }
}
