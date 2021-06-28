package superlord.goblinsanddungeons.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class UrnBlock extends FallingBlock implements IWaterLoggable {

	public static final BooleanProperty EXPLODING = BooleanProperty.create("exploding");
	public static final BooleanProperty HAS_WATER = BooleanProperty.create("water");
	public static final BooleanProperty HAS_LAVA = BooleanProperty.create("lava");
	//public static final BooleanProperty HAS_ITEM = BooleanProperty.create("item");
	//public static final IntegerProperty COUNT = IntegerProperty.create("count", 0, 64);
	//public static final IntegerProperty ITEM_NAME = IntegerProperty.create("name", 0, 10000);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public UrnBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(EXPLODING, false).with(HAS_WATER, false).with(HAS_LAVA, false).with(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER;
		return super.getStateForPlacement(context).with(EXPLODING, false).with(HAS_WATER, false).with(HAS_LAVA, false).with(WATERLOGGED, Boolean.valueOf(flag));
	}

	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return !state.getCollisionShape(worldIn, pos).project(Direction.UP).isEmpty() || state.isSolidSide(worldIn, pos, Direction.UP);
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!stateIn.isValidPosition(worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		} else {
			if (stateIn.get(WATERLOGGED)) {
				worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			}

			return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	protected void onStartFalling(FallingBlockEntity fallingEntity) {
		fallingEntity.setHurtEntities(true);
	}

	public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
		if (!fallingBlock.isSilent()) {
			worldIn.playEvent(1031, pos, 0);
		}

	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		return false;
	}

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(EXPLODING, HAS_WATER, HAS_LAVA, WATERLOGGED);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (this.hasTnT(state) || this.hasLava(state) || this.hasWater(state)/** || state.get(HAS_ITEM)*/) {
			return ActionResultType.CONSUME;
		} else {
			ItemStack stack = player.getHeldItem(handIn);
			Item item = stack.getItem();
			if(item == Blocks.TNT.asItem()) {
				worldIn.setBlockState(pos, state.with(EXPLODING, Boolean.valueOf(true)));
				this.setDefaultState(this.stateContainer.getBaseState().with(EXPLODING, true));
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
				return ActionResultType.func_233537_a_(worldIn.isRemote);
			}
			if (item == Items.WATER_BUCKET) {
				worldIn.setBlockState(pos, state.with(HAS_WATER, Boolean.valueOf(true)));
				player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
					ItemStack bucket = new ItemStack(Items.BUCKET);
					player.addItemStackToInventory(bucket);
				}
				return ActionResultType.func_233537_a_(worldIn.isRemote);
			}
			if (item == Items.LAVA_BUCKET) {
				worldIn.setBlockState(pos, state.with(HAS_LAVA, Boolean.valueOf(true)));
				player.playSound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 1.0F, 1.0F);
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
					ItemStack bucket = new ItemStack(Items.BUCKET);
					player.addItemStackToInventory(bucket);
				}
				return ActionResultType.func_233537_a_(worldIn.isRemote);
			} /**else {
				player.playSound(SoundEvents.BLOCK_COMPOSTER_FILL, 1.0F, 1.0F);
				worldIn.setBlockState(pos, state.with(HAS_ITEM, Boolean.valueOf(true)).with(COUNT, stack.getCount()).with(ITEM_NAME, Item.getIdFromItem(item)));

				if (!player.abilities.isCreativeMode) {
					stack.shrink(stack.getCount());
				}
			}*/
			return ActionResultType.CONSUME;
		}
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		player.addStat(Stats.BLOCK_MINED.get(this));
		player.addExhaustion(0.005F);
		if(this.hasTnT(state)) {
			explode((World)worldIn, pos);
		}
		if (this.hasWater(state)) {
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
		} 
		if (this.hasLava(state)) {
			worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState());
		}
		spawnDrops(state, worldIn, pos, te, player, stack);
	}


	public boolean hasTnT(BlockState state) {
		return state.get(EXPLODING);
	}

	public boolean hasWater(BlockState state) {
		return state.get(HAS_WATER);
	}

	public boolean hasLava(BlockState state) {
		return state.get(HAS_LAVA);
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	public static void explode(World world, BlockPos worldIn) {
		explode(world, worldIn, (LivingEntity)null);
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	private static void explode(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isRemote) {
			TNTEntity tntentity = new TNTEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, entityIn);
			worldIn.addEntity(tntentity);
			worldIn.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

}
