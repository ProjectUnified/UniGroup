package io.github.projectunified.unigroup.towny;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class TownyGroupProvider implements GroupProvider {
    private final TownyGroupType type;
    private Optional<Throwable> error;

    public TownyGroupProvider(TownyGroupType type) {
        this.type = type;
    }

    public TownyGroupProvider() {
        this(TownyGroupType.TOWN);
    }

    public TownyGroupType getType() {
        return type;
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    @Override
    public Optional<Throwable> getError() {
        if (error == null) {
            if (type == null) {
                error = Optional.of(new IllegalArgumentException("Invalid towny type, must be TOWN or NATION"));
            } else {
                error = Optional.empty();
            }
        }
        return error;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        if (type == TownyGroupType.NATION) {
            return new NationGroup(member);
        } else if (type == TownyGroupType.TOWN) {
            return new TownGroup(member);
        } else {
            return new Group() {
                @Override
                public boolean handled() {
                    return false;
                }

                @Override
                public boolean exists() {
                    return false;
                }

                @Override
                public UUID owner() {
                    return null;
                }

                @Override
                public String name() {
                    return null;
                }
            };
        }
    }
}
