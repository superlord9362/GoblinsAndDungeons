package superlord.goblinsanddungeons.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class ManaStats {
	
	public static String manaStatsID = "manaStats";
	
	public static ManaSystem getManaStats(LivingEntity entity) {
		ManaSystem stats = new ManaSystem();
		if(entity != null) {
			if (getModNBT(entity) != null && getModNBT(entity).contains(manaStatsID, 10)) {
				stats.read(getModNBT(entity).getCompound(manaStatsID));
				return stats;
			}
		}
		return stats;
	}
	
	public static void setManaStats(LivingEntity entity, ManaSystem manaStats) {
		CompoundNBT compound2 = new CompoundNBT();
		manaStats.write(compound2);
		getModNBT(entity).put(manaStatsID, compound2);
	}
	
	public static String getModDataString() {
		return GoblinsAndDungeons.MOD_ID + 	":PlayerData";
	}
	
	public static CompoundNBT getModNBT(Entity entity) {
		return entity.getPersistentData().getCompound(getModDataString());
	}
	
	public static CompoundNBT getOrCreateModNBT(Entity entity) {
		if (!entity.getPersistentData().contains(getModDataString(), 10)) {
			entity.getPersistentData().put(getModDataString(), new CompoundNBT());
		}
		return entity.getPersistentData().getCompound(getModDataString());
	}
	
	public static void setModNBT(CompoundNBT nbt, Entity entity) {
		entity.getPersistentData().put(getModDataString(), nbt);
	}

}
