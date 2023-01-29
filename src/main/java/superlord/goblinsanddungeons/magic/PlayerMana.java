package superlord.goblinsanddungeons.magic;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
	
	private int mana;
	private final int MIN_MANA = 0;
	private final int MAX_MANA = 20;
	
	public int getMana() {
		return mana;
	}
	
	public void addMana(int add) {
		this.mana = Math.min(mana + add, MAX_MANA);
	}
	
	public void subMana(int sub) {
		this.mana = Math.max(mana - sub, MIN_MANA);
	}
	
	public void copyFrom(PlayerMana source) {
		this.mana = source.mana;
	}
	
	public void write(CompoundTag nbt) {
		nbt.putInt("mana", mana);
	}
	
	public void read(CompoundTag nbt) {
		mana = nbt.getInt("mana");
	}
	
}
