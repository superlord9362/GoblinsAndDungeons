package superlord.goblinsanddungeons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import superlord.goblinsanddungeons.client.ClientProxy;
import superlord.goblinsanddungeons.client.renderer.ManaGUIRenderer;
import superlord.goblinsanddungeons.common.CommonProxy;
import superlord.goblinsanddungeons.common.compat.QuarkFlagRecipeCondition;
import superlord.goblinsanddungeons.common.compat.RegistryHelper;
import superlord.goblinsanddungeons.common.entity.Beholder;
import superlord.goblinsanddungeons.common.entity.Garch;
import superlord.goblinsanddungeons.common.entity.Gob;
import superlord.goblinsanddungeons.common.entity.GobKing;
import superlord.goblinsanddungeons.common.entity.Gobber;
import superlord.goblinsanddungeons.common.entity.Goblo;
import superlord.goblinsanddungeons.common.entity.Goom;
import superlord.goblinsanddungeons.common.entity.HobGob;
import superlord.goblinsanddungeons.common.entity.Mimic;
import superlord.goblinsanddungeons.common.entity.Ogre;
import superlord.goblinsanddungeons.common.world.GoblinsAndDungeonsFeatures;
import superlord.goblinsanddungeons.config.GDConfigHolder;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.EffectInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.TileEntityInit;
import superlord.goblinsanddungeons.networking.ModMessages;

@Mod(GoblinsAndDungeons.MOD_ID)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID)
public class GoblinsAndDungeons {

	public static final String MOD_ID = "goblinsanddungeons";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);
	private static final String PROTOCOL_VERSION = "1";
	public static final List<Runnable> CALLBACKS = new ArrayList<>();
	public static final SimpleChannel NETWORK_WRAPPER = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation("goblinsanddungeons", "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	public static GoblinsAndDungeons instance;
	@SuppressWarnings("deprecation")
	public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	public GoblinsAndDungeons() {
		instance = this;
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::registerEntityAttributes);
		bus.addListener(this::clientRegistries);
		CraftingHelper.register(new QuarkFlagRecipeCondition.Serializer());
		REGISTRY_HELPER.getDeferredBlockRegister().register(bus);
		REGISTRY_HELPER.getDeferredItemRegister().register(bus);
		BlockInit.REGISTER.register(bus);
		ItemInit.REGISTER.register(bus);
		EntityInit.REGISTER.register(bus);
		EffectInit.EFFECTS.register(bus);
		EffectInit.POTIONS.register(bus);
		TileEntityInit.REGISTER.register(bus);
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, GDConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, GDConfigHolder.SERVER_SPEC);
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		bus.addListener(this::registerClient);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerBiomes(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.BiomeCategory.PLAINS || event.getCategory() == Biome.BiomeCategory.SWAMP || event.getCategory() == Biome.BiomeCategory.TAIGA || event.getCategory() == Biome.BiomeCategory.FOREST) {
			event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityInit.OGRE.get(), GoblinsDungeonsConfig.ogreSpawnWeight, 1, 1));
		}
	}

	public static ResourceLocation location(String name) {
		return new ResourceLocation(MOD_ID, name);
	}

	public void setup(final FMLCommonSetupEvent event) {
		ModMessages.register();
		EffectInit.brewingRecipes();
		SpawnPlacements.register(EntityInit.OGRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
	}

	public void clientRegistries(final FMLClientSetupEvent event) {
		ManaGUIRenderer.registerOverlays();
	}

	private void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityInit.GOB.get(), Gob.createAttributes().build());
		event.put(EntityInit.HOBGOB.get(), HobGob.createAttributes().build());
		event.put(EntityInit.GOBLO.get(), Goblo.createAttributes().build());
		event.put(EntityInit.GARCH.get(), Garch.createAttributes().build());
		event.put(EntityInit.GOOM.get(), Goom.createAttributes().build());
		event.put(EntityInit.GOBBER.get(), Gobber.createAttributes().build());
		event.put(EntityInit.OGRE.get(), Ogre.createAttributes().build());
		event.put(EntityInit.GOB_KING.get(), GobKing.createAttributes().build());
		event.put(EntityInit.MIMIC.get(), Mimic.createAttributes().build());
		event.put(EntityInit.BEHOLDER.get(), Beholder.createAttributes().build());
	}

	public void biomeModification(final BiomeLoadingEvent event) {
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(GoblinsAndDungeonsFeatures.ORE_SCORIA_LOWER);
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(GoblinsAndDungeonsFeatures.ORE_SCORIA_UPPER);
	}


	public final static CreativeModeTab GROUP = new CreativeModeTab("goblinsanddungeons_item_group") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.GOBLIN_CROWN.get());
		}
	};

	public final static CreativeModeTab MAGIC = new CreativeModeTab("goblinsanddungeons_magic_item_group") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.SOUL_BULLET_SPELL_TOME.get());
		}
	};

	public static GoblinsAndDungeons getInstance() {
		return instance;
	}

	private void registerClient(FMLClientSetupEvent event) {
		CALLBACKS.forEach(Runnable::run);
		CALLBACKS.clear();
	}

	public static <MSG> void sendMSGToAll(MSG message) {
		for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
			sendNonLocal(message, player);
		}
	}
	
	public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
        NETWORK_WRAPPER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

}
