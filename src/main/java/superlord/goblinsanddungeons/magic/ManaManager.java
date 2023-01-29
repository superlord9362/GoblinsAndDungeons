package superlord.goblinsanddungeons.magic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ManaManager extends SavedData {
	
	private final Map<Mana, Mana> manaMap = new HashMap<>();
	
	@Nonnull
	public static ManaManager get(Level level) {
		if (level.isClientSide) {
			throw new RuntimeException("Don't access this client side!");
		}
		DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
		return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
	}
	
	public ManaManager() {
	}
	
	public ManaManager(CompoundTag tag) {
		ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);
		for (Tag t : list) {
			CompoundTag manaTag = (CompoundTag) t;
			Mana mana = new Mana(manaTag.getInt("mana"));
			manaMap.put(mana, mana);
		}
	}
	
	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag list = new ListTag();
		manaMap.forEach((mana1, mana) -> {
			CompoundTag manaTag = new CompoundTag();
			manaTag.putInt("mana", mana.getMana());
			list.add(manaTag);
		});
		tag.put("mana", list);
		return tag;
	}

}
