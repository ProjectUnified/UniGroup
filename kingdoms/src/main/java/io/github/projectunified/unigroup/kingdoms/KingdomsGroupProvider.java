package io.github.projectunified.unigroup.kingdoms;

import io.github.projectunified.unigroup.core.Group;
import io.github.projectunified.unigroup.core.GroupProvider;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class KingdomsGroupProvider implements GroupProvider {
    private final KingdomsGroupType type;
    private Optional<Throwable> error;

    public KingdomsGroupProvider(KingdomsGroupType type) {
        this.type = type;
    }

    public KingdomsGroupProvider() {
        this(KingdomsGroupType.KINGDOM);
    }

    public KingdomsGroupType getType() {
        return type;
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Kingdoms") != null;
    }

    @Override
    public Optional<Throwable> getError() {
        if (error == null) {
            if (type == null) {
                error = Optional.of(new IllegalArgumentException("Invalid kingdom type, must be KINGDOM or NATION"));
            } else {
                error = Optional.empty();
            }
        }
        return error;
    }

    @Override
    public Group getGroupByMember(UUID member) {
        if (type == KingdomsGroupType.NATION) {
            return new NationGroup(member);
        } else if (type == KingdomsGroupType.KINGDOM) {
            return new KingdomGroup(member);
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
