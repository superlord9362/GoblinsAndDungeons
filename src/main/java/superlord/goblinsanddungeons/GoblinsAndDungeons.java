package superlord.goblinsanddungeons;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
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
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import superlord.goblinsanddungeons.client.ClientProxy;
import superlord.goblinsanddungeons.client.renderer.ManaGUIRenderer;
import superlord.goblinsanddungeons.common.CommonProxy;
import superlord.goblinsanddungeons.compat.QuarkFlagRecipeCondition;
import superlord.goblinsanddungeons.compat.RegistryHelper;
import superlord.goblinsanddungeons.config.GDConfigHolder;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobKingEntity;
import superlord.goblinsanddungeons.entity.GobberEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.entity.GoomEntity;
import superlord.goblinsanddungeons.entity.HobGobEntity;
import superlord.goblinsanddungeons.entity.MimicEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.EffectInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.PacketInit;
import superlord.goblinsanddungeons.init.StructureInit;
import superlord.goblinsanddungeons.init.TileEntityInit;
import superlord.goblinsanddungeons.world.GoblinsAndDungeonsFeatures;

@Mod(GoblinsAndDungeons.MOD_ID)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID)
public class GoblinsAndDungeons {

	public static final String MOD_ID = "goblinsanddungeons";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);
	private static final String PROTOCOL_VERSION = "1";
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
		PacketInit.registerPackets();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, GDConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, GDConfigHolder.SERVER_SPEC);
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
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

	private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
		structureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
		HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = structureToMultiMap.get(configuredStructureFeature.feature);
		if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
			
		} else {
			configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
		}
	}

	public void setup(final FMLCommonSetupEvent event) {
		EffectInit.brewingRecipes();
		SpawnPlacements.register(EntityInit.OGRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
	}
	
	public void clientRegistries(final FMLClientSetupEvent event) {
		ManaGUIRenderer.registerOverlays();
	}

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
    	event.put(EntityInit.GOB.get(), GobEntity.createAttributes().build());
		event.put(EntityInit.HOBGOB.get(), HobGobEntity.createAttributes().build());
		event.put(EntityInit.GOBLO.get(), GobloEntity.createAttributes().build());
		event.put(EntityInit.GARCH.get(), GarchEntity.createAttributes().build());
		event.put(EntityInit.GOOM.get(), GoomEntity.createAttributes().build());
		event.put(EntityInit.GOBBER.get(), GobberEntity.createAttributes().build());
		event.put(EntityInit.OGRE.get(), OgreEntity.createAttributes().build());
		event.put(EntityInit.GOB_KING.get(), GobKingEntity.createAttributes().build());
		event .put(EntityInit.MIMIC.get(), MimicEntity.createAttributes().build());
	}

	public void biomeModification(final BiomeLoadingEvent event) {
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> GoblinsAndDungeonsFeatures.ORE_SCORIA_LOWER);
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> GoblinsAndDungeonsFeatures.ORE_SCORIA_UPPER);
	}

	private static Method GETCODEC_METHOD;
	@SuppressWarnings("unchecked")
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel serverLevel) {
			ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
			if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			StructureSettings worldStructureConfig = chunkGenerator.getSettings();

			HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap = new HashMap<>();

			for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {

				Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
				if (biomeCategory == Biome.BiomeCategory.PLAINS || biomeCategory == Biome.BiomeCategory.FOREST || biomeCategory == Biome.BiomeCategory.TAIGA) {
					if (BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.OVERWORLD) && (BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.PLAINS)) && !BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.UNDERGROUND) && !BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.RIVER) && !BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.MOUNTAIN) && !BiomeDictionary.hasType(biomeEntry.getKey(), BiomeDictionary.Type.HILLS)) {
						associateBiomeToConfiguredStructure(structureToMultiMap, StructureInit.SMALL_GOBLIN_CAMP_FEATURE, biomeEntry.getKey());
						associateBiomeToConfiguredStructure(structureToMultiMap, StructureInit.MEDIUM_GOBLIN_CAMP_FEATURE, biomeEntry.getKey());
						associateBiomeToConfiguredStructure(structureToMultiMap, StructureInit.LARGE_GOBLIN_CAMP_FEATURE, biomeEntry.getKey());
						associateBiomeToConfiguredStructure(structureToMultiMap, StructureInit.RUINED_KEEP_FEATURE, biomeEntry.getKey());
					}
				}
			}
			ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
			worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !structureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

			structureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

			worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();


			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
			} catch (Exception e) {
			}

			if (chunkGenerator instanceof FlatLevelSource &&
					serverLevel.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
			tempMap.putIfAbsent(StructureInit.SMALL_GOBLIN_CAMP, StructureSettings.DEFAULTS.get(StructureInit.SMALL_GOBLIN_CAMP));
			tempMap.putIfAbsent(StructureInit.MEDIUM_GOBLIN_CAMP, StructureSettings.DEFAULTS.get(StructureInit.MEDIUM_GOBLIN_CAMP));
			tempMap.putIfAbsent(StructureInit.LARGE_GOBLIN_CAMP, StructureSettings.DEFAULTS.get(StructureInit.LARGE_GOBLIN_CAMP));
			tempMap.putIfAbsent(StructureInit.RUINED_KEEP, StructureSettings.DEFAULTS.get(StructureInit.RUINED_KEEP));
			worldStructureConfig.structureConfig = tempMap;
		}
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

}
