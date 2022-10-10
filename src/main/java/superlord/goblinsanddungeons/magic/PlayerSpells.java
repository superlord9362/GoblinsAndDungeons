package superlord.goblinsanddungeons.magic;

import net.minecraft.nbt.CompoundTag;

public class PlayerSpells {
	
	private boolean knowsSoulBullet = false;
	private boolean knowsSoulJump = false;
	
	public boolean doesKnowSoulBullet() {
		return knowsSoulBullet;
	}
	
	public boolean doesKnowSoulJump() {
		return knowsSoulJump;
	}
	
	public void setKnowsSoulBullet(boolean soulBullet) {
		knowsSoulBullet = soulBullet;
	}
	
	public void setKnowsSoulJump(boolean soulJump) {
		knowsSoulJump = soulJump;
	}
	
	public void copyFrom(PlayerSpells source) {
		this.knowsSoulBullet = source.knowsSoulBullet;
		this.knowsSoulJump = source.knowsSoulJump;
	}
	
	public void write(CompoundTag nbt) {
		nbt.putBoolean("soul_bullet", knowsSoulBullet);
		nbt.putBoolean("soul_jump", knowsSoulJump);
	}
	
	public void read(CompoundTag nbt) {
		knowsSoulBullet = nbt.getBoolean("soul_bullet");
		knowsSoulJump = nbt.getBoolean("soul_jump");
	}

}
