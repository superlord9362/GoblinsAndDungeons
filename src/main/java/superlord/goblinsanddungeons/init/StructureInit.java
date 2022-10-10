package superlord.goblinsanddungeons.init;

import java.util.Locale;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.world.structures.LargeGoblinCampStructure;
import superlord.goblinsanddungeons.common.world.structures.LargeGoblinCampStructurePiece;
import superlord.goblinsanddungeons.common.world.structures.MediumGoblinCampStructure;
import superlord.goblinsanddungeons.common.world.structures.MediumGoblinCampStructurePiece;
import superlord.goblinsanddungeons.common.world.structures.RuinedKeepStructure;
import superlord.goblinsanddungeons.common.world.structures.RuinedKeepStructurePiece;
import superlord.goblinsanddungeons.common.world.structures.SmallGoblinCampStructure;
import superlord.goblinsanddungeons.common.world.structures.SmallGoblinCampStructurePiece;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {
	
	public static final StructureFeature<NoneFeatureConfiguration> SMALL_GOBLIN_CAMP = new SmallGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> MEDIUM_GOBLIN_CAMP = new MediumGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> LARGE_GOBLIN_CAMP = new LargeGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final StructureFeature<NoneFeatureConfiguration> RUINED_KEEP = new RuinedKeepStructure(NoneFeatureConfiguration.CODEC);
	public static StructurePieceType SMALL_GOBLIN_CAMP_PIECE;
	public static StructurePieceType MEDIUM_GOBLIN_CAMP_PIECE;
	public static StructurePieceType LARGE_GOBLIN_CAMP_PIECE;
	public static StructurePieceType RUINED_KEEP_PIECE;
	
	static StructurePieceType setPieceId(StructurePieceType.StructureTemplateType type, String name) {
		return Registry.register(Registry.STRUCTURE_PIECE, name.toLowerCase(Locale.ROOT), type);
	}
	
	@SubscribeEvent
	public static void registerFeature(RegistryEvent.Register<StructureFeature<?>> registry) {
		registry.getRegistry().register(SMALL_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:small_goblin_camp"));
		registry.getRegistry().register(MEDIUM_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:medium_goblin_camp"));
		registry.getRegistry().register(LARGE_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:large_goblin_camp"));
		registry.getRegistry().register(RUINED_KEEP.setRegistryName("goblinsanddungeons:ruined_keep"));
	}
	
	public static void init() {
		SMALL_GOBLIN_CAMP_PIECE = setPieceId(SmallGoblinCampStructurePiece.Piece::new, "SGCSP");
		MEDIUM_GOBLIN_CAMP_PIECE = setPieceId(MediumGoblinCampStructurePiece.Piece::new, "MGCSP");
		LARGE_GOBLIN_CAMP_PIECE = setPieceId(LargeGoblinCampStructurePiece.Piece::new, "LGCSP");
		RUINED_KEEP_PIECE = setPieceId(RuinedKeepStructurePiece.Piece::new, "RKSP");
	}
	
	@SuppressWarnings("unused")
	private static String prefix(String path) {
		return "goblinsanddungeons:" + path;
	}
}
