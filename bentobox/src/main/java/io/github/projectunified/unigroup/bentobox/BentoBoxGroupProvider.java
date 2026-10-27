package io.github.projectunified.unigroup.bentobox;

import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BentoBoxGroupProvider implements GroupProvider {
    private final String gamemode;

    public BentoBoxGroupProvider(String gamemode) {
        this.gamemode = gamemode;
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BentoBox") != null;
    }

    public String getGamemode() {
        return gamemode;
    }

    @Override
    public GroupResponse byMember(UUID member) {
        return new BentoBoxGroupResponse(gamemode, member);
    }
}
