package io.github.projectunified.unigroup.betterteams;

import com.booksaw.betterTeams.Team;
import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BetterTeamsGroupResponse implements GroupResponse {
    private final Group group;

    public BetterTeamsGroupResponse(UUID member) {
        Team team = Team.getTeam(Bukkit.getOfflinePlayer(member));
        this.group = new BetterTeamsGroup(team);
    }

    @Override
    public Group group() {
        return group;
    }
}
