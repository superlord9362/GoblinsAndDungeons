package superlord.goblinsanddungeons.init;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.resources.ResourceLocation;

public class LootTableInit {

	private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
	private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
	
	public static final ResourceLocation CAMP_LOOT_TABLE = register("goblinsanddungeons:chests/camp_loot_table");
	public static final ResourceLocation KEEP_LOOT_TABLE = register("goblinsanddungeons:chests/keep_loot_table");
	
	private static ResourceLocation register(String id) {
		return register(new ResourceLocation(id));
	}
	
	private static ResourceLocation register(ResourceLocation id) {
		if (LOOT_TABLES.add(id)) {
			return id;
		} else {
			throw new IllegalArgumentException(id + " is already a registered built-in loot table");
		}
	}
	
	public static Set<ResourceLocation> func_215796_a() {
		return READ_ONLY_LOOT_TABLES;
	}
	
}
