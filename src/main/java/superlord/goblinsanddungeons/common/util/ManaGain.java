package superlord.goblinsanddungeons.common.util;

public class ManaGain {

	private final float saturation;
	private final boolean canEatWhenFull;

	private ManaGain(ManaGain.Builder builder) {
		this.saturation = builder.saturation;
		this.canEatWhenFull = builder.alwaysEdible;
	}

	public float getSaturation() {
		return this.saturation;
	}

	public boolean canEatWhenFull() {
		return this.canEatWhenFull;
	}

	public static class Builder {
		private float saturation;
		private boolean alwaysEdible;
		ManaSystem mana = new ManaSystem();

		public ManaGain.Builder saturation(float saturationIn) {
			this.saturation = saturationIn;
			saturationIn = mana.getManaFilledLevel();
			return this;
		}

		public ManaGain.Builder setAlwaysEdible() {
			this.alwaysEdible = true;
			return this;
		}

		public ManaGain build() {
			return new ManaGain(this);
		}

	}


}
