package superlord.goblinsanddungeons.world.structures;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class LargeGoblinCampStructure  extends StructureFeature<NoneFeatureConfiguration> {
	public LargeGoblinCampStructure(Codec<NoneFeatureConfiguration> p_i51440_1_) {
		super(p_i51440_1_, PieceGeneratorSupplier.simple(LargeGoblinCampStructure::checkLocation, LargeGoblinCampStructure::generatePieces));
	}

	private static void generatePieces(StructurePiecesBuilder p_197233_, PieceGenerator.Context<NoneFeatureConfiguration> p_197234_) {
		BlockPos blockpos = new BlockPos(p_197234_.chunkPos().getMinBlockX(), 90, p_197234_.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(p_197234_.random());
		LargeGoblinCampStructurePiece.addStructure(p_197234_.structureManager(), blockpos, rotation, p_197233_, p_197234_.random());
	}

	private static boolean checkLocation(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> p_197134_) {
		int i = p_197134_.chunkPos().x >> 4;
		int j = p_197134_.chunkPos().z >> 4;
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
		worldgenrandom.setSeed((long) (i ^ j << 4) ^ p_197134_.seed());
		worldgenrandom.nextInt();

		return !isNearVillage(p_197134_.chunkGenerator(), p_197134_.seed(), p_197134_.chunkPos()) && p_197134_.validBiomeOnTop(Heightmap.Types.OCEAN_FLOOR_WG);
	}

	private static boolean isNearVillage(ChunkGenerator p_191049_, long p_191050_, ChunkPos p_191051_) {
		StructureFeatureConfiguration structurefeatureconfiguration = p_191049_.getSettings().getConfig(StructureFeature.VILLAGE);
		if (structurefeatureconfiguration == null) {
			return false;
		} else {
			int i = p_191051_.x;
			int j = p_191051_.z;

			for (int k = i - 10; k <= i + 10; ++k) {
				for (int l = j - 10; l <= j + 10; ++l) {
					ChunkPos chunkpos = StructureFeature.VILLAGE.getPotentialFeatureChunk(structurefeatureconfiguration, p_191050_, k, l);
					if (k == chunkpos.x && l == chunkpos.z) {
						return true;
					}
				}
			}

			return false;
		}
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

}
