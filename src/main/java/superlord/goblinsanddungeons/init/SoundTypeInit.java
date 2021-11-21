package superlord.goblinsanddungeons.init;

import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

public class SoundTypeInit extends SoundType {

	@SuppressWarnings("deprecation")
	public static final SoundType URN = new SoundType(1.0F, 1.0F, SoundInit.URN_BREAK, SoundInit.URN_STEP, SoundInit.URN_PLACE, SoundInit.URN_HIT, SoundInit.URN_FALL);

	public final float volume;
	public final float pitch;
	private final SoundEvent breakSound;
	private final SoundEvent stepSound;
	private final SoundEvent placeSound;
	private final SoundEvent hitSound;
	private final SoundEvent fallSound;

	@Deprecated // Forge: Use {@link net.minecraftforge.common.util.ForgeSoundType} instead for suppliers
	public SoundTypeInit(float volumeIn, float pitchIn, SoundEvent breakSoundIn, SoundEvent stepSoundIn, SoundEvent placeSoundIn, SoundEvent hitSoundIn, SoundEvent fallSoundIn) {
		super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
		this.volume = volumeIn;
		this.pitch = pitchIn;
		this.breakSound = breakSoundIn;
		this.stepSound = stepSoundIn;
		this.placeSound = placeSoundIn;
		this.hitSound = hitSoundIn;
		this.fallSound = fallSoundIn;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	public SoundEvent getBreakSound() {
		return this.breakSound;
	}

	public SoundEvent getStepSound() {
		return this.stepSound;
	}

	public SoundEvent getPlaceSound() {
		return this.placeSound;
	}

	public SoundEvent getHitSound() {
		return this.hitSound;
	}

	public SoundEvent getFallSound() {
		return this.fallSound;
	}

}
