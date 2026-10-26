package io.github.projectunified.unigroup.simpleclans;

import io.github.projectunified.unigroup.core.Group;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import java.util.UUID;

public class SimpleClansGroup implements Group {
    private final Clan clan;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public SimpleClansGroup(UUID member) {
        this.clan = SimpleClans.getInstance().getClanManager().getClanByPlayerUniqueId(member);
    }

    public SimpleClansGroup(Clan clan) {
        this.clan = clan;
    }

    public Clan clan() {
        return clan;
    }

    public Clan getClan() {
        return clan;
    }

    @Override
    public boolean exists() {
        return clan != null && owner() != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = clan != null ? clan.getLeaders().stream()
                    .map(ClanPlayer::getUniqueId)
                    .findFirst()
                    .orElse(null) : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = clan != null ? clan.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
