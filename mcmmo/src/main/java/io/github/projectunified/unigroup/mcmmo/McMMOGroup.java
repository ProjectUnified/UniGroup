package io.github.projectunified.unigroup.mcmmo;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;
import io.github.projectunified.unigroup.core.Group;
import org.bukkit.Bukkit;

import java.util.UUID;

public class McMMOGroup implements Group {
    private final boolean handled;
    private final Party party;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public McMMOGroup(UUID member) {
        McMMOPlayer mcMMOPlayer = UserManager.getOfflinePlayer(Bukkit.getOfflinePlayer(member));
        if (mcMMOPlayer == null) {
            this.handled = false;
            this.party = null;
        } else {
            this.handled = true;
            this.party = mcMMOPlayer.getParty();
        }
    }

    public McMMOGroup(Party party) {
        this.handled = true;
        this.party = party;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    public boolean isHandled() {
        return handled;
    }

    public Party party() {
        return party;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public boolean exists() {
        return party != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = party != null ? party.getLeader().getUniqueId() : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = party != null ? party.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
