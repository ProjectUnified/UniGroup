package io.github.projectunified.unigroup.simpleclans;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import java.util.UUID;

public class SimpleClansGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Throwable error;
    private final Group group;

    public SimpleClansGroupResponse(UUID member) {
        SimpleClans sc = SimpleClans.getInstance();
        if (sc == null || sc.getClanManager() == null) {
            this.handled = false;
            this.error = new IllegalStateException("The plugin is not initialized");
            this.group = null;
            return;
        }
        Clan clan = sc.getClanManager().getClanByPlayerUniqueId(member);
        if (clan == null) {
            this.handled = true;
            this.error = null;
            this.group = new SimpleClansGroup(null);
        } else {
            UUID owner = clan.getLeaders().stream().findFirst().map(ClanPlayer::getUniqueId).orElse(null);
            if (owner == null) {
                this.handled = false;
                this.error = null;
                this.group = null;
            } else {
                this.handled = true;
                this.group = new SimpleClansGroup(clan);
                this.error = null;
            }
        }
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
