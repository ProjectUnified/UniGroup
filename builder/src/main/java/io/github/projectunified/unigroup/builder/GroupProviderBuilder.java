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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A builder that creates {@link GroupProvider} instances from configuration settings maps.
 */
public class GroupProviderBuilder {
    private final Plugin plugin;
    private final Map<String, Function<Map<String, Object>, GroupProvider>> registered = new HashMap<>();

    /**
     * Create a new GroupProviderBuilder with the specified hosting plugin.
     *
     * @param plugin the plugin instance, or null if standalone
     */
    public GroupProviderBuilder(Plugin plugin) {
        this.plugin = plugin;
        registerDefaultProviders();
    }

    /**
     * Create a new GroupProviderBuilder without a plugin instance.
     */
    public GroupProviderBuilder() {
        this(null);
    }

    private void register(Function<Map<String, Object>, GroupProvider> function, String... types) {
        for (String type : types) {
            registered.put(type.toLowerCase(Locale.ROOT), function);
        }
    }

    private void registerDefaultProviders() {
        // SuperiorSkyblock
        if (SuperiorSkyblockGroupProvider.isAvailable()) {
            register(map -> new SuperiorSkyblockGroupProvider(), "superiorskyblock", "superiorskyblock2");
        }

        // HuskTowns
        if (HuskTownsGroupProvider.isAvailable()) {
            register(map -> new HuskTownsGroupProvider(), "husktowns");
        }

        // Towny
        if (TownyGroupProvider.isAvailable()) {
            register(map -> {
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
            }, "towny");
        }

        // BentoBox
        if (BentoBoxGroupProvider.isAvailable()) {
            register(map -> {
                String gamemode = Optional.ofNullable(map.get("gamemode"))
                        .map(Objects::toString)
                        .orElse(null);
                return new BentoBoxGroupProvider(gamemode);
            }, "bentobox");
        }

        // FactionsUUID / Factions
        if (FactionsUUIDGroupProvider.isAvailable()) {
            register(map -> new FactionsUUIDGroupProvider(), "factionsuuid", "factions");
        }

        // Kingdoms / KingdomsX
        if (KingdomsGroupProvider.isAvailable()) {
            register(map -> {
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
            }, "kingdoms", "kingdomsx");
        }

        // Lands
        if (LandsGroupProvider.isAvailable()) {
            register(map -> {
                LandsGroupType type = LandsGroupType.LAND;
                Object rawType = map.get("lands-type");
                if (rawType != null) {
                    try {
                        type = LandsGroupType.valueOf(rawType.toString().trim().toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException e) {
                        type = null;
                    }
                }
                return plugin != null ? new LandsGroupProvider(plugin, type) : new LandsGroupProvider(type);
            }, "lands");
        }

        // BetterTeams
        if (BetterTeamsGroupProvider.isAvailable()) {
            register(map -> new BetterTeamsGroupProvider(), "betterteams");
        }

        // Parties
        if (PartiesGroupProvider.isAvailable()) {
            register(map -> new PartiesGroupProvider(), "parties");
        }

        // mcMMO
        if (McMMOGroupProvider.isAvailable()) {
            register(map -> new McMMOGroupProvider(), "mcmmo");
        }

        // SimpleClans
        if (SimpleClansGroupProvider.isAvailable()) {
            register(map -> new SimpleClansGroupProvider(), "simpleclans");
        }

        // FactionsBridge
        if (FactionsBridgeGroupProvider.isAvailable()) {
            register(map -> new FactionsBridgeGroupProvider(), "factionsbridge");
        }

        // PlaceholderAPI
        if (PlaceholderGroupProvider.isAvailable()) {
            register(map -> {
                String owner = Optional.ofNullable(map.get("owner")).map(Object::toString).orElse("");
                String display = Optional.ofNullable(map.get("display")).map(Object::toString).orElse("");
                boolean online = Optional.ofNullable(map.get("online")).map(Object::toString).map(Boolean::parseBoolean).orElse(false);
                return new PlaceholderGroupProvider(owner, display, online);
            }, "placeholder", "placeholderapi", "papi");
        }
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

        Function<Map<String, Object>, GroupProvider> creator = registered.get(type);
        if (creator == null) {
            return new ErrorGroupProvider(new IllegalArgumentException("Unknown group provider type: " + type));
        }

        try {
            return creator.apply(map);
        } catch (Throwable t) {
            return new ErrorGroupProvider(t);
        }
    }
}
