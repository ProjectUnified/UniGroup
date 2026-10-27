package io.github.projectunified.unigroup.simpleclans;

import io.github.projectunified.unigroup.core.Group;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

import java.util.UUID;

public class SimpleClansGroup implements Group {
    private final Clan clan;

    public SimpleClansGroup(Clan clan) {
        this.clan = clan;
    }

    public Clan clan() {
        return clan;
    }

    @Override
    public boolean exists() {
        return clan != null;
    }

    @Override
    public UUID owner() {
        return clan != null ? clan.getLeaders().stream().findFirst().map(ClanPlayer::getUniqueId).orElse(null) : null;
    }

    @Override
    public String name() {
        return clan != null ? clan.getName() : null;
    }
}
