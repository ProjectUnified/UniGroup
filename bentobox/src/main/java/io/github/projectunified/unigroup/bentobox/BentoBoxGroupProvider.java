package io.github.projectunified.unigroup.bentobox;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class BentoBoxGroupProvider implements GroupProvider {
    private final String gamemode;
    private Optional<Throwable> error;

    public BentoBoxGroupProvider(String gamemode) {
        this.gamemode = gamemode;
    }

    public BentoBoxGroupProvider() {
        this(null);
    }

    public String getGamemode() {
        return gamemode;
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BentoBox") != null;
    }

    @Override
    public Optional<Throwable> getError() {
        if (error == null) {
            error = BentoBoxGroup.checkError(gamemode);
        }
        return error;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        return new BentoBoxGroup(member, gamemode);
    }
}
