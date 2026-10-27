package io.github.projectunified.unigroup.builder;

import io.github.projectunified.unigroup.bentobox.BentoBoxGroupProvider;
import io.github.projectunified.unigroup.betterteams.BetterTeamsGroupProvider;
import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.factionsbridge.FactionsBridgeGroupProvider;
import io.github.projectunified.unigroup.factionsuuid.FactionsUUIDGroupProvider;
import io.github.projectunified.unigroup.husktowns.HuskTownsGroupProvider;
import io.github.projectunified.unigroup.kingdoms.KingdomsGroupProvider;
import io.github.projectunified.unigroup.kingdoms.KingdomsGroupType;
import io.github.projectunified.unigroup.lands.LandsGroupProvider;
import io.github.projectunified.unigroup.lands.LandsGroupType;
import io.github.projectunified.unigroup.mcmmo.McMMOGroupProvider;
import io.github.projectunified.unigroup.parties.PartiesGroupProvider;
import io.github.projectunified.unigroup.placeholderapi.PlaceholderGroupProvider;
import io.github.projectunified.unigroup.simpleclans.SimpleClansGroupProvider;
import io.github.projectunified.unigroup.superiorskyblock.SuperiorSkyblockGroupProvider;
import io.github.projectunified.unigroup.towny.TownyGroupProvider;
import io.github.projectunified.unigroup.towny.TownyGroupType;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * A builder that creates {@link GroupProvider} instances from configuration settings maps.
 */
public class GroupProviderBuilder {
    private final Plugin plugin;
    private final Map<String, GroupProviderEntry> registered = new HashMap<>();

    /**
     * Create a new GroupProviderBuilder with the specified hosting plugin.
     *
     * @param plugin the plugin instance
     */
    public GroupProviderBuilder(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin cannot be null");
        registerDefaultProviders();
    }

    private void register(BooleanSupplier available, Function<Map<String, Object>, GroupProvider> creator, String... types) {
        for (String type : types) {
            registered.put(type.toLowerCase(Locale.ROOT), new GroupProviderEntry(available, creator));
        }
    }

    private void registerDefaultProviders() {
        register(SuperiorSkyblockGroupProvider::isAvailable, map -> new SuperiorSkyblockGroupProvider(), "superiorskyblock", "superiorskyblock2");
        register(HuskTownsGroupProvider::isAvailable, map -> new HuskTownsGroupProvider(), "husktowns");
        register(
                TownyGroupProvider::isAvailable,
                map -> {
                    TownyGroupType type = TownyGroupType.TOWN;
                    Object rawType = map.get("towny-type");
                    if (rawType != null) {
                        try {
                            type = TownyGroupType.valueOf(rawType.toString().trim().toUpperCase(Locale.ROOT));
                        } catch (IllegalArgumentException e) {
                            type = null;
                        }
                    }
                    return new TownyGroupProvider(type);
                },
                "towny"
        );
        register(
                BentoBoxGroupProvider::isAvailable,
                map -> {
                    String gamemode = Optional.ofNullable(map.get("gamemode"))
                            .map(Objects::toString)
                            .orElse(null);
                    return new BentoBoxGroupProvider(gamemode);
                },
                "bentobox"
        );
        register(FactionsUUIDGroupProvider::isAvailable, map -> new FactionsUUIDGroupProvider(), "factionsuuid", "factions");
        register(
                KingdomsGroupProvider::isAvailable,
                map -> {
                    KingdomsGroupType type = KingdomsGroupType.KINGDOM;
                    Object rawType = map.get("kingdoms-type");
                    if (rawType != null) {
                        try {
                            type = KingdomsGroupType.valueOf(rawType.toString().trim().toUpperCase(Locale.ROOT));
                        } catch (IllegalArgumentException e) {
                            type = null;
                        }
                    }
                    return new KingdomsGroupProvider(type);
                },
                "kingdoms", "kingdomsx"
        );
        register(
                LandsGroupProvider::isAvailable,
                map -> {
                    LandsGroupType type = LandsGroupType.LAND;
                    Object rawType = map.get("lands-type");
                    if (rawType != null) {
                        try {
                            type = LandsGroupType.valueOf(rawType.toString().trim().toUpperCase(Locale.ROOT));
                        } catch (IllegalArgumentException e) {
                            type = null;
                        }
                    }
                    return new LandsGroupProvider(plugin, type);
                },
                "lands"
        );
        register(BetterTeamsGroupProvider::isAvailable, map -> new BetterTeamsGroupProvider(), "betterteams");
        register(PartiesGroupProvider::isAvailable, map -> new PartiesGroupProvider(), "parties");
        register(McMMOGroupProvider::isAvailable, map -> new McMMOGroupProvider(), "mcmmo");
        register(SimpleClansGroupProvider::isAvailable, map -> new SimpleClansGroupProvider(), "simpleclans");
        register(FactionsBridgeGroupProvider::isAvailable, map -> new FactionsBridgeGroupProvider(), "factionsbridge");
        register(
                PlaceholderGroupProvider::isAvailable,
                map -> {
                    String owner = Optional.ofNullable(map.get("owner")).map(Object::toString).orElse("");
                    String display = Optional.ofNullable(map.get("display")).map(Object::toString).orElse("");
                    boolean online = Optional.ofNullable(map.get("online")).map(Object::toString).map(Boolean::parseBoolean).orElse(false);
                    return new PlaceholderGroupProvider(owner, display, online);
                },
                "placeholder", "placeholderapi", "papi"
        );
    }

    /**
     * Build a {@link GroupProvider} from the given settings map.
     *
     * @param map the configuration map
     * @return the created group provider, or an {@link ErrorGroupProvider} on error
     */
    public GroupProvider build(Map<String, Object> map) {
        if (map == null) {
            return new ErrorGroupProvider(new IllegalArgumentException("Settings map cannot be null"));
        }

        Object rawType = map.get("type");
        String type;
        if (rawType != null) {
            type = rawType.toString().trim().toLowerCase(Locale.ROOT);
        } else if (map.containsKey("owner")) {
            type = "placeholder";
        } else {
            return new ErrorGroupProvider(new IllegalArgumentException("Provider type is not specified"));
        }

        GroupProviderEntry entry = registered.get(type);
        if (entry == null) {
            return new ErrorGroupProvider(new IllegalArgumentException("Unknown group provider type: " + type));
        }

        if (!entry.available.getAsBoolean()) {
            return new ErrorGroupProvider(new IllegalArgumentException("Group provider is not available: " + type));
        }

        try {
            return entry.creator.apply(map);
        } catch (Throwable t) {
            return new ErrorGroupProvider(t);
        }
    }

    private static final class GroupProviderEntry {
        final BooleanSupplier available;
        final Function<Map<String, Object>, GroupProvider> creator;

        private GroupProviderEntry(BooleanSupplier available, Function<Map<String, Object>, GroupProvider> creator) {
            this.available = available;
            this.creator = creator;
        }
    }
}
