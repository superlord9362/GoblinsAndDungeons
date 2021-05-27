package superlord.goblinsanddungeons;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobKingEntity;
import superlord.goblinsanddungeons.entity.GobberEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.entity.GoomEntity;
import superlord.goblinsanddungeons.entity.HobGobEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.ConfiguredStructureInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.StructureInit;
import superlord.goblinsanddungeons.init.TileEntityInit;
import superlord.goblinsanddungeons.world.GoblinsAndDungeonsFeatures;

@Mod(GoblinsAndDungeons.MOD_ID)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID)
public class GoblinsAndDungeons {
	
	public static final String MOD_ID = "goblinsanddungeons";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public GoblinsAndDungeons() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);
        bus.addListener(this::setup);

		ItemInit.REGISTER.register(bus);
		BlockInit.REGISTER.register(bus);
		TileEntityInit.REGISTER.register(bus);
		EntityInit.REGISTER.register(bus);
		StructureInit.REGISTER.register(bus);
		
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}
	
	private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
	}
	
	public void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StructureInit.setupStructures();
			ConfiguredStructureInit.registerConfiguredStructures();
			
			WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
				Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();
				if (structureMap instanceof ImmutableMap) {
					Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
					tempMap.put(StructureInit.SMALL_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.SMALL_GOBLIN_CAMP.get()));
					tempMap.put(StructureInit.MEDIUM_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.MEDIUM_GOBLIN_CAMP.get()));
					tempMap.put(StructureInit.LARGE_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.LARGE_GOBLIN_CAMP.get()));
					tempMap.put(StructureInit.RUINED_KEEP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.RUINED_KEEP.get()));
					settings.getValue().getStructures().field_236193_d_ = tempMap;
				} else {
					structureMap.put(StructureInit.SMALL_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.SMALL_GOBLIN_CAMP.get()));
					structureMap.put(StructureInit.MEDIUM_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.MEDIUM_GOBLIN_CAMP.get()));
					structureMap.put(StructureInit.LARGE_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.LARGE_GOBLIN_CAMP.get()));
					structureMap.put(StructureInit.RUINED_KEEP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.RUINED_KEEP.get()));
				}
			});
		});
	}
	
	private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(EntityInit.GOB.get(), GobEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.HOBGOB.get(), HobGobEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOBLO.get(), GobloEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GARCH.get(), GarchEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOOM.get(), GoomEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOBBER.get(), GobberEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.OGRE.get(), OgreEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOB_KING.get(), GobKingEntity.createAttributes().create());
    }
	
	public void biomeModification(final BiomeLoadingEvent event) {
		if(event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.TAIGA) {
			event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.CONFIGURED_SMALL_GOBLIN_CAMP);
			event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.CONFIGURED_MEDIUM_GOBLIN_CAMP);
			event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.CONFIGURED_LARGE_GOBLIN_CAMP);
			event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.CONFIGURED_RUINED_KEEP);
		}
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> GoblinsAndDungeonsFeatures.ORE_SCORIA);
	}
	
	private static Method GETCODEC_METHOD;
	@SuppressWarnings({ "unchecked", "resource" })
	private void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverworld = (ServerWorld)event.getWorld();
			try {
				if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "getCodec");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverworld.getChunkProvider().generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
			} catch(Exception e) {
				GoblinsAndDungeons.LOGGER.error("Was unable to check if " + serverworld.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator.");
			}
			if (serverworld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverworld.getDimensionKey().equals(World.OVERWORLD)) {
				return;
			}
			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverworld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
			tempMap.putIfAbsent(StructureInit.SMALL_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.SMALL_GOBLIN_CAMP.get()));
			tempMap.putIfAbsent(StructureInit.MEDIUM_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.MEDIUM_GOBLIN_CAMP.get()));
			tempMap.putIfAbsent(StructureInit.LARGE_GOBLIN_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.LARGE_GOBLIN_CAMP.get()));
			tempMap.putIfAbsent(StructureInit.RUINED_KEEP.get(), DimensionStructuresSettings.field_236191_b_.get(StructureInit.RUINED_KEEP.get()));
			serverworld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
		}
	}
	
	public final static ItemGroup GROUP = new ItemGroup("goblinsanddungeons_item_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.GOBLIN_CROWN.get());
        }
    };

}
