package superlord.goblinsanddungeons.world.structures;

import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.StructureInit;

public class MediumGoblinCampStructure extends Structure<NoFeatureConfig> {

	public MediumGoblinCampStructure(Codec<NoFeatureConfig> config) {
		super(config);
	}

	public String getStructureName() {
		return GoblinsAndDungeons.MOD_ID + ":medium_goblin_camp";
	}

	protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long long1, SharedSeedRandom randomSeed, int int1, int int2, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {
		int i = int1 >> 4;
		int j = int2 >> 4;
		randomSeed.setSeed((long) (i ^ j << 4) ^ long1);
		randomSeed.nextInt();
		for (int k = int1 - 10; k <= int1 + 10; ++k) {
			for (int l = int2 - 10; l <= int2 + 10; ++l) {
				ChunkPos chunkpos2 = Structure.VILLAGE.getChunkPosForStructure(chunkGenerator.func_235957_b_().func_236197_a_(Structure.VILLAGE), long1, randomSeed, k, l);
				if (k == chunkpos2.x && l == chunkpos2.z) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public GenerationStage.Decoration getDecorationStage() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return MediumGoblinCampStructure.Start::new;
	}

	public static class Start extends StructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			int surfaceY = Math.max(generator.getNoiseHeightMinusOne(x + 12, z + 12, Heightmap.Type.WORLD_SURFACE_WG) - 1, generator.getGroundHeight() - 1);
			BlockPos blockpos = new BlockPos(x, surfaceY, z);
			Piece.start(templateManagerIn, blockpos, rotation, this.components, this.rand);
			this.recalculateStructureSize();
		}
	}

	public static class Piece extends TemplateStructurePiece {
		private ResourceLocation resourceLocation;
		private Rotation rotation;
		private boolean gob1;
		private boolean gob2;
		private boolean gob3;
		private boolean garch;
		private boolean garch2;
		private boolean goblo;

		public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
			super(StructureInit.MEDIUM_GOBLIN_CAMP_PIECE_TYPE, 0);
			this.resourceLocation = resourceLocationIn;
			this.templatePosition = pos;
			this.rotation = rotationIn;
			this.setupPiece(templateManagerIn);
		}

		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(StructureInit.MEDIUM_GOBLIN_CAMP_PIECE_TYPE, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
			this.gob1 = tagCompound.getBoolean("Gob1");
			this.gob2 = tagCompound.getBoolean("Gob2");
			this.gob3 = tagCompound.getBoolean("Gob3");
			this.garch = tagCompound.getBoolean("Garch");
			this.garch2 = tagCompound.getBoolean("Garch2");
			this.goblo = tagCompound.getBoolean("Goblo");
		}

		public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation2, List<StructurePiece> pieceList, Random rand) {
			int x = pos.getX();
			int z = pos.getZ();
			BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation2);
			BlockPos blockPos = rotationOffSet.add(x, pos.getY(), z);
			pieceList.add(new Piece(templateManager, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "medium_goblin_camp"), blockPos, rotation2));
		}

		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementSettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			this.setup(template, this.templatePosition, placementSettings);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
			tagCompound.putBoolean("Gob1", this.gob1);
			tagCompound.putBoolean("Gob2", this.gob2);
			tagCompound.putBoolean("Gob3", this.gob3);
			tagCompound.putBoolean("Garch", this.garch);
			tagCompound.putBoolean("Garch2", this.garch2);
			tagCompound.putBoolean("Goblo", this.goblo);
		}

		@Override
		public boolean func_230383_a_(ISeedReader seedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos, BlockPos pos) {
			PlacementSettings placementSettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
			BlockPos blockpos = BlockPos.ZERO;
			this.templatePosition.add(Template.transformedBlockPos(placementSettings, new BlockPos(-blockpos.getX(), 0, -blockpos.getZ())));
			if (!this.gob1) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.gob1 = true;
					GobEntity gobentity = EntityInit.GOB.get().create(seedReader.getWorld());
					gobentity.enablePersistence();
					gobentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					gobentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(gobentity);
				}
			}
			if (!this.gob2) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.gob2 = true;
					GobEntity gobentity = EntityInit.GOB.get().create(seedReader.getWorld());
					gobentity.enablePersistence();
					gobentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					gobentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(gobentity);
				}
			}
			if (!this.gob3) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.gob3 = true;
					GobEntity gobentity = EntityInit.GOB.get().create(seedReader.getWorld());
					gobentity.enablePersistence();
					gobentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					gobentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(gobentity);
				}
			}
			if (!this.garch) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.garch = true;
					GarchEntity garchentity = EntityInit.GARCH.get().create(seedReader.getWorld());
					garchentity.enablePersistence();
					garchentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					garchentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(garchentity);
				}
			}
			if (!this.garch2) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.garch2 = true;
					GarchEntity garchentity = EntityInit.GARCH.get().create(seedReader.getWorld());
					garchentity.enablePersistence();
					garchentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					garchentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(garchentity);
				}
			}
			if (!this.goblo) {
				int l = this.getXWithOffset(2, 5);
				int k = this.getZWithOffset(2, 5);
				if (structureBoundingBoxIn.isVecInside(new BlockPos(l, pos.getY(), k))) {
					this.goblo = true;
					GobloEntity garchentity = EntityInit.GOBLO.get().create(seedReader.getWorld());
					garchentity.enablePersistence();
					garchentity.setLocationAndAngles((double)l + 0.5D, (double)pos.getY(), (double)k + 0.5D, 0.0F, 0.0F);
					garchentity.onInitialSpawn(seedReader, seedReader.getDifficultyForLocation(new BlockPos(l, pos.getY(), k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
					seedReader.func_242417_l(garchentity);
				}
			}
			return super.func_230383_a_(seedReader, structureManager, chunkGenerator, random, structureBoundingBoxIn, chunkPos, pos);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld world, Random rand, MutableBoundingBox sbb) {
			if ("chest".equals(function)) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				TileEntity tileEntity = world.getTileEntity(pos.down());
				if (tileEntity instanceof ChestTileEntity) {
					((ChestTileEntity) tileEntity).setLootTable(LootTables.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
				}
			}
		}
	}
}
