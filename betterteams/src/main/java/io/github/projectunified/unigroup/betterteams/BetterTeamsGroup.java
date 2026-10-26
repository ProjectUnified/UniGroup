package io.github.projectunified.unigroup.betterteams;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import io.github.projectunified.unigroup.core.Group;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BetterTeamsGroup implements Group {
    private final Team team;
    private UUID owner;
    private String name;
    private boolean ownerSet;
    private boolean nameSet;

    public BetterTeamsGroup(UUID member) {
        this.team = Team.getTeam(Bukkit.getOfflinePlayer(member));
    }

    public BetterTeamsGroup(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean exists() {
        return team != null && owner() != null;
    }

    @Override
    public UUID owner() {
        if (!ownerSet) {
            owner = team != null
                    ? team.getRank(PlayerRank.OWNER).stream()
                    .map(TeamPlayer::getPlayerUUID)
                    .sorted()
                    .findFirst()
                    .orElse(null)
                    : null;
            ownerSet = true;
        }
        return owner;
    }

    @Override
    public String name() {
        if (!nameSet) {
            name = team != null ? team.getName() : null;
            nameSet = true;
        }
        return name;
    }
}
