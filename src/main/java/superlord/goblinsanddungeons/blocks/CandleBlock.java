package superlord.goblinsanddungeons.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CandleBlock extends BushBlock implements IWaterLoggable {

	public static final IntegerProperty CANDLES = BlockStateProperties.PICKLES_1_4;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape ONE_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
	protected static final VoxelShape TWO_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
	protected static final VoxelShape THREE_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
	protected static final VoxelShape FOUR_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
	protected final IParticleData particleData;

	public CandleBlock(AbstractBlock.Properties properties, IParticleData particleData) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, Integer.valueOf(1)).with(WATERLOGGED, Boolean.valueOf(false)));
		this.particleData = particleData;

	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if (blockstate.isIn(this)) {
			return blockstate.with(CANDLES, Integer.valueOf(Math.min(4, blockstate.get(CANDLES) + 1)));
		} else {
			FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
			boolean flag = fluidstate.getFluid() == Fluids.WATER;
			return super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(flag));
		}
	}

	public static boolean isInBadEnvironment(BlockState state) {
		return state.get(WATERLOGGED);
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

	@SuppressWarnings("deprecation")
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		return useContext.getItem().getItem() == this.asItem() && state.get(CANDLES) < 4 ? true : super.isReplaceable(state, useContext);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(CANDLES)) {
		case 1:
		default:
			return ONE_SHAPE;
		case 2:
			return TWO_SHAPE;
		case 3:
			return THREE_SHAPE;
		case 4:
			return FOUR_SHAPE;
		}
	}

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(CANDLES, WATERLOGGED);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double candle1X = (double)pos.getX() + 0.5D;
		double candle1Y = (double)pos.getY() + 0.7D;
		double candle1Z = (double)pos.getZ() + 0.5D;
		//Two Candles
		double smallCandle2X = (double)pos.getX() + 0.65D;
		double smallCandle2Y = (double)pos.getY() + 0.45D;
		double smallCandle2Z = (double)pos.getZ() + 0.35D;
		double largeCandle2X = (double)pos.getX() + 0.3D;
		double largeCandle2Y = (double)pos.getY() + 0.7D;
		double largeCandle2Z = (double)pos.getZ() + 0.675D;
		//Three Candles
		double smallCandle3X = (double)pos.getX() + 0.25D;
		double smallCandle3Y = (double)pos.getY() + 0.45D;
		double smallCandle3Z = (double)pos.getZ() + 0.75D;
		double largeCandle3X = (double)pos.getX() + 0.35D;
		double largeCandle3Y = (double)pos.getY() + 0.7D;
		double largeCandle3Z = (double)pos.getZ() + 0.35D;
		double mediumCandle3X = (double)pos.getX() + 0.65D;
		double mediumCandle3Y = (double)pos.getY() + 0.6D;
		double mediumCandle3Z = (double)pos.getZ() + 0.6D;
		//Four Candles
		double smallCandle4X = (double)pos.getX() + 0.7D;
		double smallCandle4Y = (double)pos.getY() + 0.45D;
		double smallCandle4Z = (double)pos.getZ() + 0.275D;
		double largeCandle4X = (double)pos.getX() + 0.3D;
		double largeCandle4Y = (double)pos.getY() + 0.7D;
		double largeCandle4Z = (double)pos.getZ() + 0.3D;
		double mediumCandle4X = (double)pos.getX() + 0.3D;
		double mediumCandle4Y = (double)pos.getY() + 0.6D;
		double mediumCandle4Z = (double)pos.getZ() + 0.725D;
		double giantCandle4X = (double)pos.getX() + 0.7D;
		double giantCandle4Y = (double)pos.getY() + 0.8D;
		double giantCandle4Z = (double)pos.getZ() + 0.725D;
		if (stateIn.get(CANDLES) == 1 && !stateIn.get(WATERLOGGED)) {
			worldIn.addParticle(ParticleTypes.SMOKE, candle1X, candle1Y, candle1Y, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, candle1X, candle1Y, candle1Z, 0.0D, 0.0D, 0.0D);
		}
		if (stateIn.get(CANDLES) == 2 && !stateIn.get(WATERLOGGED)) {
			worldIn.addParticle(this.particleData, smallCandle2X, smallCandle2Y, smallCandle2Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, smallCandle2X, smallCandle2Y, smallCandle2Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, largeCandle2X, largeCandle2Y, largeCandle2Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, largeCandle2X, largeCandle2Y, largeCandle2Z, 0.0D, 0.0D, 0.0D);
		}
		if (stateIn.get(CANDLES) == 3 && !stateIn.get(WATERLOGGED)) {
			worldIn.addParticle(this.particleData, smallCandle3X, smallCandle3Y, smallCandle3Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, smallCandle3X, smallCandle3Y, smallCandle3Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, largeCandle3X, largeCandle3Y, largeCandle3Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, largeCandle3X, largeCandle3Y, largeCandle3Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, mediumCandle3X, mediumCandle3Y, mediumCandle3Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, mediumCandle3X, mediumCandle3Y, mediumCandle3Z, 0.0D, 0.0D, 0.0D);
		}
		if (stateIn.get(CANDLES) == 4 && !stateIn.get(WATERLOGGED)) {
			worldIn.addParticle(this.particleData, smallCandle4X, smallCandle4Y, smallCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, smallCandle4X, smallCandle4Y, smallCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, largeCandle4X, largeCandle4Y, largeCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, largeCandle4X, largeCandle4Y, largeCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, mediumCandle4X, mediumCandle4Y, mediumCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, mediumCandle4X, mediumCandle4Y, mediumCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.SMOKE, giantCandle4X, giantCandle4Y, giantCandle4Z, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(this.particleData, giantCandle4X, giantCandle4Y, giantCandle4Z, 0.0D, 0.0D, 0.0D);
		}
	}

}
