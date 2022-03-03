package superlord.goblinsanddungeons.world.structures;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.LootTableInit;
import superlord.goblinsanddungeons.init.StructureInit;

public class SmallGoblinCampStructurePiece {

	private static final ResourceLocation smallGoblinCamp_Template = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "small_goblin_camp");

	private static final Map<ResourceLocation, BlockPos> structurePos = ImmutableMap.of(smallGoblinCamp_Template, BlockPos.ZERO);

	static final BlockPos PIVOT = new BlockPos(9, 0, 9);

	public static void addStructure(StructureManager p_162435_, BlockPos p_162436_, Rotation p_162437_, StructurePieceAccessor p_162438_, Random p_162439_) {
		p_162438_.addPiece(new Piece(p_162435_, smallGoblinCamp_Template, p_162436_, p_162437_, 0));
	}

	public static class Piece extends TemplateStructurePiece {
		public Piece(StructureManager p_71244_, ResourceLocation p_71245_, BlockPos p_71246_, Rotation p_71247_, int p_71248_) {
			super(StructureInit.SMALL_GOBLIN_CAMP_STRUCTURE_PIECE, 0, p_71244_, p_71245_, p_71245_.toString(), makeSettings(p_71247_), makePosition(p_71245_, p_71246_, p_71248_));
		}

		public Piece(StructureManager p_162441_, CompoundTag p_162442_) {
			super(StructureInit.SMALL_GOBLIN_CAMP_STRUCTURE_PIECE, p_162442_, p_162441_, (p_162451_) -> {
				return makeSettings(Rotation.valueOf(p_162442_.getString("Rot")));
			});
		}

		private static StructurePlaceSettings makeSettings(Rotation p_163156_) {
			BlockIgnoreProcessor blockignoreprocessor = BlockIgnoreProcessor.STRUCTURE_BLOCK;

			StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setRotation(p_163156_).setMirror(Mirror.NONE).setRotationPivot(PIVOT).addProcessor(blockignoreprocessor).addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE.getName()));


			return structureplacesettings;
		}

		private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
			return p_162454_.offset(structurePos.get(p_162453_)).below(p_162455_);
		}

		protected void addAdditionalSaveData(StructurePieceSerializationContext p_162444_, CompoundTag p_162445_) {
			super.addAdditionalSaveData(p_162444_, p_162445_);
			p_162445_.putString("Rot", this.placeSettings.getRotation().name());
		}

		public void postProcess(WorldGenLevel worldIn, StructureFeatureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, BoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
			//ResourceLocation var8 = new ResourceLocation(this.templateName);
			//BlockPos blockPos = (BlockPos) structurePos.get(var8);

			BlockPos blockpos1 = this.templatePosition.offset(this.placeSettings.getRotationPivot());
			int i = worldIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.offset(0, i - 90 - 2, 0);
			super.postProcess(worldIn, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
			this.templatePosition = blockpos2;
		}	

        @Override
        protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor world, Random rand, BoundingBox sbb) {
            if ("chest".equals(function)) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                BlockEntity tileentity = world.getBlockEntity(pos.below());
                if (tileentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) tileentity).setLootTable(LootTableInit.CAMP_LOOT_TABLE, rand.nextLong());
                }
            }
            if ("gob".equals(function)) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
				GobEntity entity = EntityInit.GOB.get().create(world.getLevel());
				if (entity != null) {
					entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					entity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
					world.addFreshEntity(entity);
				}
			}
			if ("garch".equals(function)) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
				GarchEntity entity = EntityInit.GARCH.get().create(world.getLevel());
				if (entity != null) {
					entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					entity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
					world.addFreshEntity(entity);
				}
			}
        }
    }
}