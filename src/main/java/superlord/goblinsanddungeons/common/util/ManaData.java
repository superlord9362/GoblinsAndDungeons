package superlord.goblinsanddungeons.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ManaData extends GDData {
	
	private int manaLevel = 20;
	
	public ManaData() {
		
	}
	
	public void tick(Player player) {

	}
	
	public void read(CompoundTag compound) {
		if (compound.contains("manaLevel", 99)) {
			this.manaLevel = compound.getInt("manaLevel");
		}

	}
	
	public void setManaLevel(int manaLevel) {
		this.manaLevel = manaLevel;
	}
	
	public int getManaLevel() {
		return manaLevel;
	}

	public void write(CompoundTag compound) {
		compound.putFloat("manaLevel", this.manaLevel);
	}
	
	public void shrink(int shrinkAmount, Player player) {
		if (getManaLevel() - shrinkAmount <= 0) {
			this.manaLevel = 0;
		} else {
			this.manaLevel = getManaLevel() - shrinkAmount;
		}
		this.save(player);
	}
	
	public void grow(int growAmount, Player player) {
		if (getManaLevel() + growAmount >= 20) {
			this.manaLevel = 20;
		} else {
			this.manaLevel = getManaLevel() + growAmount;
		}
		this.save(player);
	}
	
	@Override
	public void save(LivingEntity player) {
		ManaEntityStats.setManaStats(player, this);
	}

	@Override
	public boolean shouldTick() {
		return true;
	}

}
