package io.github.projectunified.unigroup.parties;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.Party;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;

import java.util.UUID;

public class PartiesGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Throwable error;
    private final Group group;

    public PartiesGroupResponse(UUID member) {
        boolean handled;
        Throwable error = null;
        Group group = null;
        try {
            Party party = Parties.getApi().getPartyOfPlayer(member);
            handled = true;
            group = new PartiesGroup(party);
        } catch (Throwable t) {
            handled = false;
            error = t;
        }
        this.handled = handled;
        this.error = error;
        this.group = group;
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
