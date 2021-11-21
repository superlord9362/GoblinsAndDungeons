package superlord.goblinsanddungeons.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.world.structures.LargeGoblinCampStructure;
import superlord.goblinsanddungeons.world.structures.MediumGoblinCampStructure;
import superlord.goblinsanddungeons.world.structures.RuinedKeepStructure;
import superlord.goblinsanddungeons.world.structures.SmallGoblinCampStructure;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {
	
	public static IStructurePieceType SMALL_GOBLIN_CAMP_PIECE_TYPE = SmallGoblinCampStructure.Piece::new;
	public static IStructurePieceType MEDIUM_GOBLIN_CAMP_PIECE_TYPE = MediumGoblinCampStructure.Piece::new;
	public static IStructurePieceType LARGE_GOBLIN_CAMP_PIECE_TYPE = LargeGoblinCampStructure.Piece::new;
	public static IStructurePieceType RUINED_KEEP_PIECE_TYPE = RuinedKeepStructure.Piece::new;

	public static final DeferredRegister<Structure<?>> REGISTER = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<Structure<NoFeatureConfig>> SMALL_GOBLIN_CAMP = REGISTER.register("small_goblin_camp", () -> (new SmallGoblinCampStructure(NoFeatureConfig.field_236558_a_)));
	public static final RegistryObject<Structure<NoFeatureConfig>> MEDIUM_GOBLIN_CAMP = REGISTER.register("medium_goblin_camp", () -> (new MediumGoblinCampStructure(NoFeatureConfig.field_236558_a_)));
	public static final RegistryObject<Structure<NoFeatureConfig>> LARGE_GOBLIN_CAMP = REGISTER.register("large_goblin_camp", () -> (new LargeGoblinCampStructure(NoFeatureConfig.field_236558_a_)));
	public static final RegistryObject<Structure<NoFeatureConfig>> RUINED_KEEP = REGISTER.register("ruined_keep", () -> (new RuinedKeepStructure(NoFeatureConfig.field_236558_a_)));
	
	public static void setupStructures() {
		setupMapSpacingAndLand(SMALL_GOBLIN_CAMP.get(), new StructureSeparationSettings(GoblinsDungeonsConfig.smallGoblinCampMaxDistance, GoblinsDungeonsConfig.smallGoblinCampMinDistance, 2538959), true);
		setupMapSpacingAndLand(MEDIUM_GOBLIN_CAMP.get(), new StructureSeparationSettings(GoblinsDungeonsConfig.mediumGoblinCampMaxDistance, GoblinsDungeonsConfig.mediumGoblinCampMinDistance, 2895726), true);
		setupMapSpacingAndLand(LARGE_GOBLIN_CAMP.get(), new StructureSeparationSettings(GoblinsDungeonsConfig.largeGoblinCampMaxDistance, GoblinsDungeonsConfig.largeGoblinCampMinDistance, 2716364), true);
		setupMapSpacingAndLand(RUINED_KEEP.get(), new StructureSeparationSettings(GoblinsDungeonsConfig.ruinedKeepMaxDistance, GoblinsDungeonsConfig.ruinedKeepMinDistance, 2827153), true);
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
		}

		DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();
	}

	public static void registerStructurePieces() {
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "small_goblin_camp"), SMALL_GOBLIN_CAMP_PIECE_TYPE);
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "medium_goblin_camp"), MEDIUM_GOBLIN_CAMP_PIECE_TYPE);
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "large_goblin_camp"), LARGE_GOBLIN_CAMP_PIECE_TYPE);
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "ruined_keep"), RUINED_KEEP_PIECE_TYPE);
	}
}
