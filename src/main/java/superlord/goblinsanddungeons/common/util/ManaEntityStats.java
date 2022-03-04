package superlord.goblinsanddungeons.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class ManaEntityStats {
	
	public static String manaStatsID = "ManaStats";
	public static String knownSpellsID = "KnownSpells";

	public static ManaData getManaStats(LivingEntity entity) {
		ManaData stats = new ManaData();
		if (entity != null) {
			if (getModNBT(entity) != null && getModNBT(entity).contains(manaStatsID, 10)) {
				stats.read(getModNBT(entity).getCompound(manaStatsID));
				return stats;
			}
		}
		return stats;
	}
	
	public static void setManaStats(LivingEntity entity, ManaData manaStats) {
		CompoundTag compound2 = new CompoundTag();
		manaStats.write(compound2);
		getModNBT(entity).put(manaStatsID, compound2);
	}
	
	public static KnownSpellsData getKnownSpells(LivingEntity entity) {
		KnownSpellsData stats = new KnownSpellsData();
		if (entity != null) {
			if (getModNBT(entity) != null && getModNBT(entity).contains(knownSpellsID, 10)) {
				stats.read(getModNBT(entity).getCompound(knownSpellsID));
				return stats;
			}
		}
		return stats;
	}
	
	public static void setKnownSpells(LivingEntity entity, KnownSpellsData knownSpells) {
		CompoundTag compound2 = new CompoundTag();
		knownSpells.write(compound2);
		getModNBT(entity).put(knownSpellsID, compound2);
	}
	
	@SuppressWarnings("unused")
	public static void addStatsOnSpawn(Player player) {
		if (player != null) {
			CompoundTag compound;
			compound = getOrCreateModNBT(player);
			String name = player.getScoreboardName();
			if (player.isAlive()) {
				if (!compound.contains(manaStatsID)) {
					setManaStats(player, new ManaData());
				}
				if (!compound.contains(knownSpellsID)) {
					setKnownSpells(player, new KnownSpellsData());
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static String append(String string) {
		return GoblinsAndDungeons.MOD_ID + ":" + string;
	}
	
	public static String getModDataString() {
		return GoblinsAndDungeons.MOD_ID + ":PlayerData";
	}
	
	public static CompoundTag getModNBT(Entity entity) {
		return entity.getPersistentData().getCompound(getModDataString());
	}
	
	public static CompoundTag getOrCreateModNBT(Entity entity) {
		if (!entity.getPersistentData().contains(getModDataString(), 10)) {
			entity.getPersistentData().put(getModDataString(), new CompoundTag());
		}
		return entity.getPersistentData().getCompound(getModDataString());
	}
	
	public static void setModNBT(CompoundTag nbt, Entity entity) {
		entity.getPersistentData().put(getModDataString(), nbt);
	}

}
