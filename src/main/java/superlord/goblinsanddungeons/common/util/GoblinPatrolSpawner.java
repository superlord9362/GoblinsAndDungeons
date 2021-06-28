package superlord.goblinsanddungeons.common.util;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.init.EntityInit;

public class GoblinPatrolSpawner implements ISpecialSpawner {
	private int field_222698_b;

	@SuppressWarnings("deprecation")
	@Override
	public int func_230253_a_(ServerWorld world, boolean p_230253_2_, boolean p_230253_3_) {
		if (!p_230253_2_) {
			return 0;
		} else if (!world.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
			return 0;
		} else {
			Random random = world.rand;
			--this.field_222698_b;
			if (this.field_222698_b > 0) {
				return 0;
			} else {
				this.field_222698_b += 12000 + random.nextInt(1200);
				long i = world.getDayTime() / 24000L;
				if (i >= 5L && world.isDaytime()) {
					if (random.nextInt(5) != 0) {
						return 0;
					} else {
						int j = world.getPlayers().size();
						if (j < 1) {
							return 0;
						} else {
							PlayerEntity playerentity = world.getPlayers().get(random.nextInt(j));
							if (playerentity.isSpectator()) {
								return 0;
							} else if (world.func_241119_a_(playerentity.getPosition(), 2)) {
								return 0;
							} else {
								int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
								int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
								BlockPos.Mutable blockpos$mutable = playerentity.getPosition().toMutable().move(k, 0, l);
								if (!world.isAreaLoaded(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10)) {
									return 0;
								} else {
									Biome biome = world.getBiome(blockpos$mutable);
									Biome.Category biome$category = biome.getCategory();
									if (biome$category == Biome.Category.MUSHROOM) {
										return 0;
									} else {
										int i1 = 0;
										int j1 = (int)Math.ceil((double)world.getDifficultyForLocation(blockpos$mutable).getAdditionalDifficulty()) + 1;

										for(int k1 = 0; k1 < j1; ++k1) {
											++i1;
											blockpos$mutable.setY(world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY());
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
	
	private boolean spawnGoblins(ServerWorld worldIn, BlockPos pos, Random random, boolean p_222695_4_) {
	      BlockState blockstate = worldIn.getBlockState(pos);
	      if (!WorldEntitySpawner.func_234968_a_(worldIn, pos, blockstate, blockstate.getFluidState(), EntityInit.GOB.get())) {
	         return false;
	      } else if (!MonsterEntity.canMonsterSpawn(EntityInit.GOB.get(), worldIn, SpawnReason.PATROL, pos, random)) {
	         return false;
	      } else {
	         GobEntity gobentity = EntityInit.GOB.get().create(worldIn);
	         if (gobentity != null) {
	        	 gobentity.setPosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
	        	 gobentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.PATROL, (ILivingEntityData)null, (CompoundNBT)null);
	            worldIn.func_242417_l(gobentity);
	            return true;
	         } else {
	            return false;
	         }
	      }
	   }

}
