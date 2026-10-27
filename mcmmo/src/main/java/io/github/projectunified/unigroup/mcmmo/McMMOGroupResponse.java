package io.github.projectunified.unigroup.mcmmo;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.party.PartyLeader;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class McMMOGroupResponse implements GroupResponse {
    private final boolean handled;
    private final Group group;

    public McMMOGroupResponse(UUID member) {
        McMMOPlayer mcMMOPlayer = UserManager.getOfflinePlayer(Bukkit.getOfflinePlayer(member));
        if (mcMMOPlayer == null) {
            this.handled = false;
            this.group = null;
        } else {
            Party party = mcMMOPlayer.getParty();
            if (party == null) {
                this.handled = true;
                this.group = new McMMOGroup(null);
                return;
            }

            PartyLeader leader = party.getLeader();
            if (leader == null) {
                this.handled = false;
                this.group = null;
                return;
            }

            this.handled = true;
            this.group = new McMMOGroup(party);
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
