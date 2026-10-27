package io.github.projectunified.unigroup.betterteams;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import io.github.projectunified.unigroup.core.Group;

import java.util.UUID;

public class BetterTeamsGroup implements Group {
    private final Team team;

    public BetterTeamsGroup(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    @Override
    public boolean exists() {
        return team != null;
    }

    @Override
    public UUID owner() {
        return team != null ? team.getRank(PlayerRank.OWNER).stream().map(TeamPlayer::getPlayerUUID).sorted().findFirst().orElse(null) : null;
    }

    @Override
    public String name() {
        return team != null ? team.getName() : null;
    }
}
