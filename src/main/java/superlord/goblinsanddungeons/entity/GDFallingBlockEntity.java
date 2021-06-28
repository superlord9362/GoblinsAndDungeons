package superlord.goblinsanddungeons.entity;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GDFallingBlockEntity extends Entity {
	
	public static float GRAVITY = 0.1f;
	public double prevMotionX, prevMotionY, prevMotionZ;
	
	private static final DataParameter<Optional<BlockState>> BLOCK_STATE = EntityDataManager.createKey(GDFallingBlockEntity.class, DataSerializers.OPTIONAL_BLOCK_STATE);
	private static final DataParameter<Integer> DURATION = EntityDataManager.createKey(GDFallingBlockEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TICKS_EXISTED = EntityDataManager.createKey(GDFallingBlockEntity.class, DataSerializers.VARINT);
	private static final DataParameter<String> MODE = EntityDataManager.createKey(GDFallingBlockEntity.class, DataSerializers.STRING);
	private static final DataParameter<Float> ANIM = EntityDataManager.createKey(GDFallingBlockEntity.class, DataSerializers.FLOAT);

	public enum EnumFallingState {
		MOBILE,
		POP_UP
	}
	
	public float animY = 0;
	public float prevAnimY = 0;
	
	public GDFallingBlockEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
		setBlock(Blocks.DIRT.getDefaultState());
		setDuration(70);
	}
	
	public GDFallingBlockEntity(EntityType<?> type, World world, int duration, BlockState state) {
		super(type, world);
		setBlock(state);
		setDuration(duration);
	}
	
	public GDFallingBlockEntity(EntityType<?> type, World world, BlockState state, float vy) {
		super(type, world);
		setBlock(state);
		setMode(EnumFallingState.POP_UP);
		setAnimVY(vy);
	}
	
	@Override
	public void onAddedToWorld() {
		if (getMotion().getX() > 0 || getMotion().getZ() > 0) rotationYaw = (float) ((180F / Math.PI) * Math.atan2(getMotion().getX(), getMotion().getZ()));
		rotationPitch += rand.nextFloat() * 360;
		super.onAddedToWorld();
	}
	
	@Override
	public void tick() {
		if (getMode() == EnumFallingState.POP_UP) {
			setMotion(0, 0, 0);
		}
		prevMotionX = getMotion().x;
		prevMotionY = getMotion().y;
		prevMotionZ = getMotion().z;
		super.tick();
		if (getMode() == EnumFallingState.MOBILE) {
			setMotion(getMotion().subtract(0, GRAVITY, 0));
			if (onGround) setMotion(getMotion().scale(0.7));
			else rotationPitch += 15;
			this.move(MoverType.SELF, this.getMotion());
			if (ticksExisted > getDuration()) remove();
		} else {
			float animVY = getAnimVY();
			prevAnimY = animY;
			animY += animVY;
			setAnimVY(animVY - GRAVITY);
			if (animY < -0.5) remove();
		}
	}
	
	@Override
	protected void registerData() {
		getDataManager().register(BLOCK_STATE, Optional.of(Blocks.DIRT.getDefaultState()));
		getDataManager().register(DURATION, 70);
		getDataManager().register(TICKS_EXISTED, 0);
		getDataManager().register(MODE, EnumFallingState.MOBILE.toString());
		getDataManager().register(ANIM, 1F);
	}
	
	@Override
	protected void readAdditional(CompoundNBT compound) {
		INBT blockStateCompound = compound.get("block");
		if (blockStateCompound != null) {
			BlockState blockState = NBTUtil.readBlockState((CompoundNBT)blockStateCompound);
			setBlock(blockState);
		}
		setDuration(compound.getInt("duration"));
		ticksExisted = compound.getInt("ticksExisted");
		getDataManager().set(MODE, compound.getString("mode"));
		setAnimVY(compound.getFloat("vy"));
	}
	
	@Override
	protected void writeAdditional(CompoundNBT compound) {
		BlockState blockState = getBlock();
		if (blockState != null) compound.put("block", NBTUtil.writeBlockState(blockState));
		compound.putInt("duration", getDuration());
		compound.putInt("ticksExisted", ticksExisted);
		compound.putString("mode", getDataManager().get(MODE));
		compound.putFloat("vy", getDataManager().get(ANIM));
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public BlockState getBlock() {
		Optional<BlockState> bsOp = getDataManager().get(BLOCK_STATE);
		return bsOp.orElse(null);
	}
	
	public void setBlock(BlockState state) {
		getDataManager().set(BLOCK_STATE, Optional.of(state));
	}
	
	public int getDuration() {
		return getDataManager().get(DURATION);
	}
	
	public void setDuration(int duration) {
		getDataManager().set(DURATION, duration);
	}
	
	public int getTicksExisted() {
		return getDataManager().get(TICKS_EXISTED);
	}
	
	public void setTicksExisted(int ticksExisted) {
		getDataManager().set(TICKS_EXISTED, ticksExisted);
	}
	
	public EnumFallingState getMode() {
		String mode = getDataManager().get(MODE);
		if (mode.isEmpty()) return EnumFallingState.MOBILE;
		return EnumFallingState.valueOf(getDataManager().get(MODE));
	}
	
	private void setMode(EnumFallingState mode) {
		getDataManager().set(MODE, mode.toString());
	}
	
	public float getAnimVY() {
		return getDataManager().get(ANIM);
	}
	
	private void setAnimVY(float vy) {
		getDataManager().set(ANIM, vy);
	}
	
}
