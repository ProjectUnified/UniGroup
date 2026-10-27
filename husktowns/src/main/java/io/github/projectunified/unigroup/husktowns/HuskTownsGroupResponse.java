package io.github.projectunified.unigroup.husktowns;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import net.william278.husktowns.api.HuskTownsAPI;
import net.william278.husktowns.town.Town;

import java.util.UUID;

public class HuskTownsGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;

    public HuskTownsGroupResponse(UUID member) {
        boolean handled = false;
        Group group = null;
        try {
            HuskTownsAPI api = HuskTownsAPI.getInstance();
            if (api.isLoaded()) {
                handled = true;
                Town town = api.getTowns().stream()
                        .filter(t -> t.getMembers().containsKey(member))
                        .findFirst()
                        .orElse(null);
                group = new HuskTownsGroup(town);
            }
        } catch (HuskTownsAPI.NotRegisteredException e) {
            handled = false;
        } finally {
            this.handled = handled;
            this.group = group;
        }
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
