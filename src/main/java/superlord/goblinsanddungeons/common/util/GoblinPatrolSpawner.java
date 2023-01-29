package superlord.goblinsanddungeons.common.util;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import superlord.goblinsanddungeons.common.entity.Gob;
import superlord.goblinsanddungeons.init.EntityInit;

public class GoblinPatrolSpawner implements CustomSpawner {
	private int field_222698_b;

	@SuppressWarnings("deprecation")
	@Override
	public int tick(ServerLevel world, boolean p_230253_2_, boolean p_230253_3_) {
		if (!p_230253_2_) {
			return 0;
		} else if (!world.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING)) {
			return 0;
		} else {
			Random random = world.random;
			--this.field_222698_b;
			if (this.field_222698_b > 0) {
				return 0;
			} else {
				this.field_222698_b += 12000 + random.nextInt(1200);
				long i = world.getDayTime() / 24000L;
				if (i >= 5L && world.isDay()) {
					if (random.nextInt(5) != 0) {
						return 0;
					} else {
						int j = world.players().size();
						if (j < 1) {
							return 0;
						} else {
							Player playerentity = world.players().get(random.nextInt(j));
							if (playerentity.isSpectator()) {
								return 0;
							} else if (world.isCloseToVillage(playerentity.blockPosition(), 2)) {
								return 0;
							} else {
								int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
								int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
								BlockPos.MutableBlockPos blockpos$mutable = playerentity.blockPosition().mutable().move(k, 0, l);
								if (!world.hasChunksAt(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10)) {
									return 0;
								} else {
									Holder<Biome> biome = world.getBiome(blockpos$mutable);
									Biome.BiomeCategory biome$category = Biome.getBiomeCategory(biome);
									if (biome$category == Biome.BiomeCategory.MUSHROOM) {
										return 0;
									} else {
										int i1 = 0;
										int j1 = (int)Math.ceil((double)world.getCurrentDifficultyAt(blockpos$mutable).getEffectiveDifficulty()) + 1;

										for(int k1 = 0; k1 < j1; ++k1) {
											++i1;
											blockpos$mutable.setY(world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY());
											if (k1 == 0) {
												if (!this.spawnGoblins(world, blockpos$mutable, random, true)) {
													break;
												}
											} else {
												this.spawnGoblins(world, blockpos$mutable, random, false);
											}

											blockpos$mutable.setX(blockpos$mutable.getX() + random.nextInt(5) - random.nextInt(5));
											blockpos$mutable.setZ(blockpos$mutable.getZ() + random.nextInt(5) - random.nextInt(5));
										}

										return i1;
									}
								}
							}
						}
					}
				} else {
					return 0;
				}
			}
		}
	}

	private boolean spawnGoblins(ServerLevel worldIn, BlockPos pos, Random random, boolean p_222695_4_) {
		BlockState blockstate = worldIn.getBlockState(pos);
		if (!NaturalSpawner.isValidEmptySpawnBlock(worldIn, pos, blockstate, blockstate.getFluidState(), EntityInit.GOB.get())) {
			return false;
		} else if (!Monster.checkAnyLightMonsterSpawnRules(EntityInit.GOB.get(), worldIn, MobSpawnType.PATROL, pos, random)) {
			return false;
		} else {
			Gob gobentity = EntityInit.GOB.get().create(worldIn);
			if (gobentity != null) {
				gobentity.setPos((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
				gobentity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(pos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
				worldIn.addFreshEntityWithPassengers(gobentity);
				return true;
			} else {
				return false;
			}
		}
	}

}
