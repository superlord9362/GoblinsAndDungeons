package superlord.goblinsanddungeons.common.world.structures;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class RuinedKeepStructure  extends StructureFeature<NoneFeatureConfiguration> {
	public RuinedKeepStructure(Codec<NoneFeatureConfiguration> p_i51440_1_) {
		super(p_i51440_1_, PieceGeneratorSupplier.simple(RuinedKeepStructure::checkLocation, RuinedKeepStructure::generatePieces));
	}

	private static void generatePieces(StructurePiecesBuilder p_197233_, PieceGenerator.Context<NoneFeatureConfiguration> context) {
        BlockPos blockpos1 = context.chunkPos().getMiddleBlockPosition(0);
        int topLandY = context.chunkGenerator().getBaseHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor());
		BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), topLandY, context.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(context.random());
		RuinedKeepStructurePiece.addStructure(context.structureManager(), blockpos, rotation, p_197233_, context.random());
	}

	private static boolean checkLocation(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> p_197134_) {
		int i = p_197134_.chunkPos().x >> 4;
		int j = p_197134_.chunkPos().z >> 4;
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
		worldgenrandom.setSeed((long) (i ^ j << 4) ^ p_197134_.seed());
		worldgenrandom.nextInt();

		return p_197134_.validBiomeOnTop(Heightmap.Types.OCEAN_FLOOR_WG);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

}