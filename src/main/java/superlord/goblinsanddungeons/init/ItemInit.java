package superlord.goblinsanddungeons.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.item.GoblinCrownItem;
import superlord.goblinsanddungeons.item.GoblinSoulBulletItem;
import superlord.goblinsanddungeons.item.GoblinsAndDungeonsSpawnEggItem;
import superlord.goblinsanddungeons.item.HealthRingItem;
import superlord.goblinsanddungeons.item.SoulBottleItem;
import superlord.goblinsanddungeons.item.SoulBulletItem;
import superlord.goblinsanddungeons.item.SpellTomeItem;
import superlord.goblinsanddungeons.item.StaffItem;
import superlord.goblinsanddungeons.item.StealthRingItem;

public class ItemInit {

	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<Item> GOBLIN_CROWN = REGISTER.register("goblin_crown", () -> new GoblinCrownItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOB_SPAWN_EGG = REGISTER.register("gob_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOB, 0x647D14, 0xA7A322, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> HOBGOB_SPAWN_EGG = REGISTER.register("hobgob_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.HOBGOB, 0x261F3A, 0x9163B8, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBLO_SPAWN_EGG = REGISTER.register("goblo_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOBLO, 0xD9BE48, 0x843C8B, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GARCH_SPAWN_EGG = REGISTER.register("garch_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GARCH, 0x992318, 0x2C394C, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOOM_SPAWN_EGG = REGISTER.register("goom_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOOM, 0x468D70, 0x313533, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> MIMIC_SPAWN_EGG = REGISTER.register("mimic_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.MIMIC, 0x8F691D, 0x407133, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBBER_SPAWN_EGG = REGISTER.register("gobber_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOBBER, 0x677FE8, 0x82A190, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> OGRE_SPAWN_EGG = REGISTER.register("ogre_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.OGRE, 0xB8AE4D, 0x875D2A, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBLIN_KING_SPAWN_EGG = REGISTER.register("goblin_king_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOB_KING, 0x79911E, 0xCF4466, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));

	public static final RegistryObject<Item> OGRE_TUSK = REGISTER.register("ogre_tusk", () -> new Item(new Item.Properties().tab(GoblinsAndDungeons.GROUP)));

	public static final RegistryObject<Item> GOBLIN_EYE = REGISTER.register("goblin_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> STAFF_AMETHYST = REGISTER.register("staff_amethyst", () -> new StaffItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1).defaultDurability(250)));
	public static final RegistryObject<Item> SOUL_ASH = REGISTER.register("soul_ash", () -> new Item(new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> BOTTLED_SOULS = REGISTER.register("bottled_souls", () -> new SoulBottleItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));

	public static final RegistryObject<Item> GOBLIN_SOUL_BULLET = REGISTER.register("goblin_soul_bullet", () -> new GoblinSoulBulletItem(new Item.Properties()));
	public static final RegistryObject<Item> SOUL_BULLET = REGISTER.register("soul_bullet", () -> new SoulBulletItem(new Item.Properties()));

	public static final RegistryObject<BlockItem> URN = REGISTER.register("urn", () -> new BlockItem(BlockInit.URN.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA = REGISTER.register("scoria", () -> new BlockItem(BlockInit.SCORIA.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_BRICKS = REGISTER.register("scoria_bricks", () -> new BlockItem(BlockInit.SCORIA_BRICKS.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> CRACKED_SCORIA_BRICKS = REGISTER.register("cracked_scoria_bricks", () -> new BlockItem(BlockInit.CRACKED_SCORIA_BRICKS.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> CHISELED_SCORIA = REGISTER.register("chiseled_scoria", () -> new BlockItem(BlockInit.CHISELED_SCORIA.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> POLISHED_SCORIA = REGISTER.register("polished_scoria", () -> new BlockItem(BlockInit.POLISHED_SCORIA.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_SLAB = REGISTER.register("scoria_slab", () -> new BlockItem(BlockInit.SCORIA_SLAB.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_BRICK_SLAB = REGISTER.register("scoria_brick_slab", () -> new BlockItem(BlockInit.SCORIA_BRICK_SLAB.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> POLISHED_SCORIA_SLAB = REGISTER.register("polished_scoria_slab", () -> new BlockItem(BlockInit.POLISHED_SCORIA_SLAB.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_STAIRS = REGISTER.register("scoria_stairs", () -> new BlockItem(BlockInit.SCORIA_STAIRS.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_BRICK_STAIRS = REGISTER.register("scoria_brick_stairs", () -> new BlockItem(BlockInit.SCORIA_BRICK_STAIRS.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> POLISHED_SCORIA_STAIRS = REGISTER.register("polished_scoria_stairs", () -> new BlockItem(BlockInit.POLISHED_SCORIA_STAIRS.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_WALL = REGISTER.register("scoria_wall", () -> new BlockItem(BlockInit.SCORIA_WALL.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> SCORIA_BRICK_WALL = REGISTER.register("scoria_brick_wall", () -> new BlockItem(BlockInit.SCORIA_BRICK_WALL.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<BlockItem> POLISHED_SCORIA_WALL = REGISTER.register("polished_scoria_wall", () -> new BlockItem(BlockInit.POLISHED_SCORIA_WALL.get(), new Item.Properties().tab(GoblinsAndDungeons.GROUP)));

	public static final RegistryObject<BlockItem> ASHED_SOUL_SAND = REGISTER.register("ashed_soul_sand", () -> new BlockItem(BlockInit.ASHED_SOUL_SAND, new Item.Properties().tab(GoblinsAndDungeons.GROUP)));
	
	public static final RegistryObject<Item> RING_OF_HEALTH = REGISTER.register("ring_of_health", () -> new HealthRingItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));
	public static final RegistryObject<Item> RING_OF_STEALTH = REGISTER.register("ring_of_stealth", () -> new StealthRingItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));
	public static final RegistryObject<Item> RING_OF_EXPERIENCE = REGISTER.register("ring_of_experience", () -> new StealthRingItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));
	public static final RegistryObject<Item> RING_OF_GLORY = REGISTER.register("ring_of_glory", () -> new StealthRingItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));
	
	public static final RegistryObject<Item> SOUL_BULLET_SPELL_TOME = REGISTER.register("soul_bullet_spell_tome", () -> new SpellTomeItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));
	public static final RegistryObject<Item> SOUL_JUMP_SPELL_TOME = REGISTER.register("soul_jump_spell_tome", () -> new SpellTomeItem(new Item.Properties().tab(GoblinsAndDungeons.GROUP).stacksTo(1)));

	
}
