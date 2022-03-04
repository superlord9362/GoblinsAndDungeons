package superlord.goblinsanddungeons.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KnownSpellsData extends GDData {
	
	private boolean knowsSoulBullet = false;
	private boolean knowsSoulJump = false;
	
	public KnownSpellsData() {
		
	}	
	
	@Override
	public void tick(Player player) {
		
	}

	@Override
	public void read(CompoundTag compound) {
		this.knowsSoulBullet = compound.getBoolean("knowsSoulBullet");
		this.knowsSoulJump = compound.getBoolean("knowsSoulJump");
	}

	@Override
	public void write(CompoundTag compound) {
		compound.putBoolean("knowsSoulBullet", knowsSoulBullet);
		compound.putBoolean("knowsSoulJump", knowsSoulJump);
	}

	@Override
	public void save(LivingEntity player) {
		ManaEntityStats.setKnownSpells(player, this);		
	}

	@Override
	public boolean shouldTick() {
		return true;
	}
	
	public boolean knowsSoulBullet() {
		return this.knowsSoulBullet;
	}

	public void setKnowsSoulBullet(boolean knowsSoulBullet, Player player) {
		this.knowsSoulBullet = knowsSoulBullet;
		this.save(player);
	}
	
	public boolean knowsSoulJump() {
		return this.knowsSoulJump;
	}

	public void setKnowsSoulJump(boolean knowsSoulJump, Player player) {
		this.knowsSoulJump = knowsSoulJump;
		this.save(player);
	}

}
