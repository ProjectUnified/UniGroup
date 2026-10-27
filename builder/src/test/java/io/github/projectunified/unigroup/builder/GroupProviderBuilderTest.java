package io.github.projectunified.unigroup.builder;

import io.github.projectunified.unigroup.bentobox.BentoBoxGroupProvider;
import io.github.projectunified.unigroup.betterteams.BetterTeamsGroupProvider;
import io.github.projectunified.unigroup.core.GroupProvider;
import io.github.projectunified.unigroup.core.GroupResponse;
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
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupProviderBuilderTest {
    private PluginManager pluginManager;
    private Plugin plugin;

    @BeforeEach
    void setUp() {
        Server server = mock(Server.class);
        when(server.getLogger()).thenReturn(java.util.logging.Logger.getLogger("test"));
        when(server.getName()).thenReturn("MockServer");
        when(server.getVersion()).thenReturn("1.0");
        when(server.getBukkitVersion()).thenReturn("1.18.2-R0.1-SNAPSHOT");
        pluginManager = mock(PluginManager.class);
        when(server.getPluginManager()).thenReturn(pluginManager);
        Bukkit.setServer(server);

        plugin = mock(Plugin.class);
        when(plugin.getName()).thenReturn("TestPlugin");

        // By default, mock all plugins as present
        Plugin dummyPlugin = mock(Plugin.class);
        when(pluginManager.getPlugin(anyString())).thenReturn(dummyPlugin);
    }

    @AfterEach
    void tearDown() {
        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    @Test
    void testSuperiorSkyblockAliases() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> map1 = Collections.singletonMap("type", "superiorskyblock");
        assertInstanceOf(SuperiorSkyblockGroupProvider.class, builder.build(map1));

        Map<String, Object> map2 = Collections.singletonMap("type", "SuperiorSkyblock2");
        assertInstanceOf(SuperiorSkyblockGroupProvider.class, builder.build(map2));
    }

    @Test
    void testHuskTowns() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "HuskTowns");
        assertInstanceOf(HuskTownsGroupProvider.class, builder.build(map));
    }

    @Test
    void testTownyTypes() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> townMap = new HashMap<>();
        townMap.put("type", "towny");
        townMap.put("towny-type", "TOWN");
        GroupProvider townProvider = builder.build(townMap);
        assertInstanceOf(TownyGroupProvider.class, townProvider);
        assertEquals(TownyGroupType.TOWN, ((TownyGroupProvider) townProvider).getType());

        Map<String, Object> nationMap = new HashMap<>();
        nationMap.put("type", "Towny");
        nationMap.put("towny-type", "nation");
        GroupProvider nationProvider = builder.build(nationMap);
        assertInstanceOf(TownyGroupProvider.class, nationProvider);
        assertEquals(TownyGroupType.NATION, ((TownyGroupProvider) nationProvider).getType());
    }

    @Test
    void testBentoBox() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "BentoBox");
        map.put("gamemode", "bskyblock");
        GroupProvider provider = builder.build(map);
        assertInstanceOf(BentoBoxGroupProvider.class, provider);
        assertEquals("bskyblock", ((BentoBoxGroupProvider) provider).getGamemode());
    }

    @Test
    void testFactionsAliases() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> map1 = Collections.singletonMap("type", "factionsuuid");
        assertInstanceOf(FactionsUUIDGroupProvider.class, builder.build(map1));

        Map<String, Object> map2 = Collections.singletonMap("type", "FACTIONS");
        assertInstanceOf(FactionsUUIDGroupProvider.class, builder.build(map2));
    }

    @Test
    void testKingdomsAliasesAndTypes() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("type", "kingdoms");
        map1.put("kingdoms-type", "KINGDOM");
        GroupProvider p1 = builder.build(map1);
        assertInstanceOf(KingdomsGroupProvider.class, p1);
        assertEquals(KingdomsGroupType.KINGDOM, ((KingdomsGroupProvider) p1).getType());

        Map<String, Object> map2 = new HashMap<>();
        map2.put("type", "KingdomsX");
        map2.put("kingdoms-type", "nation");
        GroupProvider p2 = builder.build(map2);
        assertInstanceOf(KingdomsGroupProvider.class, p2);
        assertEquals(KingdomsGroupType.NATION, ((KingdomsGroupProvider) p2).getType());
    }

    @Test
    void testLandsTypes() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> landMap = new HashMap<>();
        landMap.put("type", "lands");
        landMap.put("lands-type", "LAND");
        GroupProvider p1 = builder.build(landMap);
        assertInstanceOf(LandsGroupProvider.class, p1);
        assertEquals(LandsGroupType.LAND, ((LandsGroupProvider) p1).getType());

        Map<String, Object> nationMap = new HashMap<>();
        nationMap.put("type", "Lands");
        nationMap.put("lands-type", "nation");
        GroupProvider p2 = builder.build(nationMap);
        assertInstanceOf(LandsGroupProvider.class, p2);
        assertEquals(LandsGroupType.NATION, ((LandsGroupProvider) p2).getType());
    }

    @Test
    void testConstructorWithPlugin() {
        Plugin plugin = mock(Plugin.class);
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "lands");
        GroupProvider provider = builder.build(map);
        assertInstanceOf(LandsGroupProvider.class, provider);
        assertSame(plugin, ((LandsGroupProvider) provider).getPlugin());
    }

    @Test
    void testBetterTeams() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "BetterTeams");
        assertInstanceOf(BetterTeamsGroupProvider.class, builder.build(map));
    }

    @Test
    void testParties() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "parties");
        assertInstanceOf(PartiesGroupProvider.class, builder.build(map));
    }

    @Test
    void testMcMMO() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "mcMMO");
        assertInstanceOf(McMMOGroupProvider.class, builder.build(map));
    }

    @Test
    void testSimpleClans() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "SimpleClans");
        assertInstanceOf(SimpleClansGroupProvider.class, builder.build(map));
    }

    @Test
    void testFactionsBridge() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "factionsbridge");
        assertInstanceOf(FactionsBridgeGroupProvider.class, builder.build(map));
    }

    @Test
    void testPlaceholderApi() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);

        Map<String, Object> map = new HashMap<>();
        map.put("type", "papi");
        map.put("owner", "%player_name%");
        map.put("display", "%vault_group%");
        map.put("online", "true");

        GroupProvider provider = builder.build(map);
        assertInstanceOf(PlaceholderGroupProvider.class, provider);
        PlaceholderGroupProvider papiProvider = (PlaceholderGroupProvider) provider;
        assertEquals("%player_name%", papiProvider.getOwnerPlaceholder());
        assertEquals("%vault_group%", papiProvider.getDisplayPlaceholder());
        assertTrue(papiProvider.isOnlineOnly());
    }

    @Test
    void testPluginNotAvailableNotRegistered() {
        // When Towny plugin is null (not available)
        when(pluginManager.getPlugin("Towny")).thenReturn(null);

        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "towny");
        GroupProvider provider = builder.build(map);

        assertInstanceOf(ErrorGroupProvider.class, provider);
        ErrorGroupProvider errorProvider = (ErrorGroupProvider) provider;
        assertTrue(errorProvider.getError().isPresent());
        assertTrue(errorProvider.getError().get().getMessage().contains("Unknown group provider type: towny"));
    }

    @Test
    void testMissingTypeWithOwnerDefaultsToPlaceholder() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("owner", "%player_name%");
        GroupProvider provider = builder.build(map);
        assertInstanceOf(PlaceholderGroupProvider.class, provider);
    }

    @Test
    void testMissingTypeWithoutOwnerReturnsError() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        GroupProvider provider = builder.build(Collections.emptyMap());
        assertInstanceOf(ErrorGroupProvider.class, provider);
        ErrorGroupProvider errorProvider = (ErrorGroupProvider) provider;
        assertTrue(errorProvider.getError().isPresent());
        assertInstanceOf(IllegalArgumentException.class, errorProvider.getError().get());
    }

    @Test
    void testUnknownTypeReturnsError() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = Collections.singletonMap("type", "unknown_plugin_xyz");
        GroupProvider provider = builder.build(map);
        assertInstanceOf(ErrorGroupProvider.class, provider);
        ErrorGroupProvider errorProvider = (ErrorGroupProvider) provider;
        assertTrue(errorProvider.getError().isPresent());
        assertTrue(errorProvider.getError().get().getMessage().contains("Unknown group provider type"));
    }

    @Test
    void testInvalidEnumTypeReturnsError() {
        GroupProviderBuilder builder = new GroupProviderBuilder(plugin);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "towny");
        map.put("towny-type", "INVALID_KIND");
        GroupProvider provider = builder.build(map);
        assertInstanceOf(TownyGroupProvider.class, provider);
        GroupResponse response = provider.byMember(UUID.randomUUID());
        assertNotNull(response.error());
        assertFalse(response.handled());
        assertNull(response.group());
    }

    @Test
    void testErrorGroupProviderReturnsUnhandledGroup() {
        ErrorGroupProvider provider = new ErrorGroupProvider(new RuntimeException("Test error"));
        UUID uuid = UUID.randomUUID();
        GroupResponse response = provider.byMember(uuid);
        assertNotNull(response);
        assertFalse(response.handled());
        assertNotNull(response.error());
        assertNull(response.group());
    }

    @Test
    void testUnhandledGroupOnNullType() {
        TownyGroupProvider townyProvider = new TownyGroupProvider(null);
        GroupResponse townyResponse = townyProvider.byMember(UUID.randomUUID());
        assertNotNull(townyResponse);
        assertFalse(townyResponse.handled());
        assertNotNull(townyResponse.error());
        assertNull(townyResponse.group());

        KingdomsGroupProvider kingdomsProvider = new KingdomsGroupProvider(null);
        GroupResponse kingdomsResponse = kingdomsProvider.byMember(UUID.randomUUID());
        assertNotNull(kingdomsResponse);
        assertFalse(kingdomsResponse.handled());
        assertNotNull(kingdomsResponse.error());
        assertNull(kingdomsResponse.group());

        LandsGroupProvider landsProvider = new LandsGroupProvider(null, null);
        GroupResponse landsResponse = landsProvider.byMember(UUID.randomUUID());
        assertNotNull(landsResponse);
        assertFalse(landsResponse.handled());
        assertNotNull(landsResponse.error());
        assertNull(landsResponse.group());
    }
}
