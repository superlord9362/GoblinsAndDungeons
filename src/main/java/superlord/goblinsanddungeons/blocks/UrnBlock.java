package superlord.goblinsanddungeons.blocks;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class UrnBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty EXPLODING = BooleanProperty.create("exploding");
	public static final BooleanProperty HAS_WATER = BooleanProperty.create("water");
	public static final BooleanProperty HAS_LAVA = BooleanProperty.create("lava");
	public static final BooleanProperty HAS_POTION = BooleanProperty.create("potion");
	public static final IntegerProperty POTION_TYPE = IntegerProperty.create("type", 0, 199);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public UrnBlock(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(EXPLODING, false).setValue(HAS_WATER, false).setValue(HAS_LAVA, false).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(HAS_POTION, false));
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor levelaccessor = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return super.getStateForPlacement(context).setValue(EXPLODING, false).setValue(HAS_WATER, false).setValue(HAS_LAVA, false).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(HAS_POTION, false);
	}


	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!stateIn.canSurvive(worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (stateIn.getValue(WATERLOGGED)) {
				worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			}
			return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState p_154235_) {
		return p_154235_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_154235_);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EXPLODING, HAS_WATER, HAS_LAVA, WATERLOGGED, HAS_POTION, POTION_TYPE);
	}

	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (this.hasTnT(state) || this.hasLava(state) || this.hasWater(state) || this.hasPotion(state)) {
			return InteractionResult.CONSUME;
		} else {
			ItemStack stack = player.getItemInHand(handIn);
			Item item = stack.getItem();
			if(item == Blocks.TNT.asItem()) {
				worldIn.setBlock(pos, state.setValue(EXPLODING, Boolean.valueOf(true)), 0);
				if (!player.isCreative()) {
					stack.shrink(1);
				}
				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
			if (item == Items.WATER_BUCKET) {
				worldIn.setBlock(pos, state.setValue(HAS_WATER, Boolean.valueOf(true)), 0);
				player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
				if (!player.isCreative()) {
					stack.shrink(1);
					ItemStack bucket = new ItemStack(Items.BUCKET);
					player.addItem(bucket);
				}
				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
			if (item == Items.LAVA_BUCKET) {
				worldIn.setBlock(pos, state.setValue(HAS_LAVA, Boolean.valueOf(true)), 0);
				player.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1.0F, 1.0F);
				if (!player.isCreative()) {
					stack.shrink(1);
					ItemStack bucket = new ItemStack(Items.BUCKET);
					player.addItem(bucket);
				}
				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
			if (item == Items.POTION) {
				worldIn.setBlock(pos, state.setValue(HAS_POTION, Boolean.valueOf(true)).setValue(POTION_TYPE, MobEffect.getId(PotionUtils.getMobEffects(stack).get(0).getEffect())), 0);

				if (!player.isCreative()) {
					stack.shrink(1);
					ItemStack bucket = new ItemStack(Items.GLASS_BOTTLE);
					player.addItem(bucket);
				}
				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
		player.awardStat(Stats.BLOCK_MINED.get(this));
		player.causeFoodExhaustion(0.005F);
		if(this.hasTnT(state)) {
			explode((Level)worldIn, pos);
		}
		if (this.hasWater(state)) {
			worldIn.setBlock(pos, Blocks.WATER.defaultBlockState(), 0);
		} 
		if (this.hasLava(state)) {
			worldIn.setBlock(pos, Blocks.LAVA.defaultBlockState(), 0);
		}
		if (this.hasPotion(state)) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(worldIn, pos.getX(), pos.getY(), pos.getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
			areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffect.byId(getPotionType(state)), 300));
			player.addEffect(new MobEffectInstance(MobEffect.byId(getPotionType(state))));
			worldIn.addFreshEntity(areaeffectcloudentity);
		}
		dropResources(state, worldIn, pos, te, player, stack);
	}


	public boolean hasTnT(BlockState state) {
		return state.getValue(EXPLODING);
	}

	public boolean hasWater(BlockState state) {
		return state.getValue(HAS_WATER);
	}

	public boolean hasLava(BlockState state) {
		return state.getValue(HAS_LAVA);
	}

	public boolean hasPotion(BlockState state) {
		return state.getValue(HAS_POTION);
	}

	public int getPotionType(BlockState state) {
		return state.getValue(POTION_TYPE);
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	public static void explode(Level world, BlockPos worldIn) {
		explode(world, worldIn, (LivingEntity)null);
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	private static void explode(Level worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isClientSide()) {
			PrimedTnt tntentity = new PrimedTnt(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, entityIn);
			worldIn.addFreshEntity(tntentity);
			worldIn.playSound((Player)null, tntentity.getBlockX(), tntentity.getBlockY(), tntentity.getBlockZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}


}
