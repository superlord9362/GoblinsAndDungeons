package superlord.goblinsanddungeons.common.entity;

import java.util.Optional;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class GDFallingBlockEntity extends Entity {
	
	public static float GRAVITY = 0.1f;
	public double prevMotionX, prevMotionY, prevMotionZ;
	
	private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.defineId(GDFallingBlockEntity.class, EntityDataSerializers.BLOCK_STATE);
	private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(GDFallingBlockEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TICKS_EXISTED = SynchedEntityData.defineId(GDFallingBlockEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<String> MODE = SynchedEntityData.defineId(GDFallingBlockEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> ANIM = SynchedEntityData.defineId(GDFallingBlockEntity.class, EntityDataSerializers.FLOAT);

	public enum EnumFallingState {
		MOBILE,
		POP_UP
	}
	
	public float animY = 0;
	public float prevAnimY = 0;
	
	public GDFallingBlockEntity(EntityType<?> entityType, Level world) {
		super(entityType, world);
		setBlock(Blocks.DIRT.defaultBlockState());
		setDuration(70);
	}
	
	public GDFallingBlockEntity(EntityType<?> type, Level world, int duration, BlockState state) {
		super(type, world);
		setBlock(state);
		setDuration(duration);
	}
	
	public GDFallingBlockEntity(EntityType<?> type, Level world, BlockState state, float vy) {
		super(type, world);
		setBlock(state);
		setMode(EnumFallingState.POP_UP);
		setAnimVY(vy);
	}
	
	@Override
	public void onAddedToWorld() {
		if (getDeltaMovement().x() > 0 || getDeltaMovement().z() > 0) animY = (float) ((180F / Math.PI) * Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()));
		yRotO += random.nextFloat() * 360;
		super.onAddedToWorld();
	}
	
	@Override
	public void tick() {
		if (getMode() == EnumFallingState.POP_UP) {
			setDeltaMovement(0, 0, 0);
		}
		prevMotionX = getDeltaMovement().x;
		prevMotionY = getDeltaMovement().y;
		prevMotionZ = getDeltaMovement().z;
		super.tick();
		if (getMode() == EnumFallingState.MOBILE) {
			setDeltaMovement(getDeltaMovement().subtract(0, GRAVITY, 0));
			if (onGround) setDeltaMovement(getDeltaMovement().scale(0.7));
			else yRotO += 15;
			this.move(MoverType.SELF, this.getDeltaMovement());
			if (tickCount > getDuration()) remove(RemovalReason.DISCARDED);
		} else {
			float animVY = getAnimVY();
			prevAnimY = animY;
			animY += animVY;
			setAnimVY(animVY - GRAVITY);
			if (animY < -0.5) remove(RemovalReason.DISCARDED);
		}
	}
	
	@Override
	protected void defineSynchedData() {
		this.entityData.define(BLOCK_STATE, Optional.of(Blocks.DIRT.defaultBlockState()));
		this.entityData.define(DURATION, 70);
		this.entityData.define(TICKS_EXISTED, 0);
		this.entityData.define(MODE, EnumFallingState.MOBILE.toString());
		this.entityData.define(ANIM, 1F);
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		Tag blockStateCompound = compound.get("block");
		if (blockStateCompound != null) {
			BlockState blockState = NbtUtils.readBlockState((CompoundTag)blockStateCompound);
			setBlock(blockState);
		}
		setDuration(compound.getInt("duration"));
		tickCount = compound.getInt("ticksExisted");
		this.entityData.set(MODE, compound.getString("mode"));
		setAnimVY(compound.getFloat("vy"));
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		BlockState blockState = getBlock();
		if (blockState != null) compound.put("block", NbtUtils.writeBlockState(blockState));
		compound.putInt("duration", getDuration());
		compound.putInt("ticksExisted", tickCount);
		compound.putString("mode", this.entityData.get(MODE));
		compound.putFloat("vy", this.entityData.get(ANIM));
	}
	
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public BlockState getBlock() {
		Optional<BlockState> bsOp = this.entityData.get(BLOCK_STATE);
		return bsOp.orElse(null);
	}
	
	public void setBlock(BlockState state) {
		this.entityData.set(BLOCK_STATE, Optional.of(state));
	}
	
	public int getDuration() {
		return this.entityData.get(DURATION);
	}
	
	public void setDuration(int duration) {
		this.entityData.set(DURATION, duration);
	}
	
	public int getTicksExisted() {
		return this.entityData.get(TICKS_EXISTED);
	}
	
	public void setTicksExisted(int ticksExisted) {
		this.entityData.set(TICKS_EXISTED, ticksExisted);
	}
	
	public EnumFallingState getMode() {
		String mode = this.entityData.get(MODE);
		if (mode.isEmpty()) return EnumFallingState.MOBILE;
		return EnumFallingState.valueOf(this.entityData.get(MODE));
	}
	
	private void setMode(EnumFallingState mode) {
		this.entityData.set(MODE, mode.toString());
	}
	
	public float getAnimVY() {
		return this.entityData.get(ANIM);
	}
	
	private void setAnimVY(float vy) {
		this.entityData.set(ANIM, vy);
	}
	
}
