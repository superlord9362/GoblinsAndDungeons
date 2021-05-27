package superlord.goblinsanddungeons.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.item.GoblinItemTiers;
import superlord.goblinsanddungeons.item.GoblinsAndDungeonsSpawnEggItem;

public class ItemInit {

	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, GoblinsAndDungeons.MOD_ID);
	
    public static final RegistryObject<Item> GOBLIN_CROWN = REGISTER.register("goblin_crown", () -> new Item(new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBLIN_EYE = REGISTER.register("goblin_eye", () -> new Item(new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<Item> DAGGER = REGISTER.register("dagger", () -> new SwordItem(GoblinItemTiers.DAGGER, 0, -2.0F, new Item.Properties().group(GoblinsAndDungeons.GROUP).maxStackSize(1)));
	public static final RegistryObject<Item> KEY = REGISTER.register("key", () -> new Item(new Item.Properties().group(GoblinsAndDungeons.GROUP).maxStackSize(1)));
	public static final RegistryObject<Item> GOB_SPAWN_EGG = REGISTER.register("gob_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOB, 0x647D14, 0xA7A322, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> HOBGOB_SPAWN_EGG = REGISTER.register("hobgob_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.HOBGOB, 0x261F3A, 0x9163B8, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBLO_SPAWN_EGG = REGISTER.register("goblo_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOBLO, 0xD9BE48, 0x843C8B, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GARCH_SPAWN_EGG = REGISTER.register("garch_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GARCH, 0x992318, 0x2C394C, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOOM_SPAWN_EGG = REGISTER.register("goom_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOOM, 0x468D70, 0x313533, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBBER_SPAWN_EGG = REGISTER.register("gobber_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOBBER, 0x677FE8, 0x82A190, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> OGRE_SPAWN_EGG = REGISTER.register("ogre_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.OGRE, 0xB8AE4D, 0x875D2A, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	public static final RegistryObject<Item> GOBLIN_KING_SPAWN_EGG = REGISTER.register("goblin_king_spawn_egg", () -> new GoblinsAndDungeonsSpawnEggItem(EntityInit.GOB_KING, 0x79911E, 0xCF4466, new Item.Properties().group(GoblinsAndDungeons.GROUP)));
	
    public static final RegistryObject<BlockItem> CANDLE = REGISTER.register("candle", () -> new BlockItem(BlockInit.CANDLE.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> URN = REGISTER.register("urn", () -> new BlockItem(BlockInit.URN.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> LOCKED_CHEST = REGISTER.register("locked_chest", () -> new BlockItem(BlockInit.LOCKED_CHEST.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA = REGISTER.register("scoria", () -> new BlockItem(BlockInit.SCORIA.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_BRICKS = REGISTER.register("scoria_bricks", () -> new BlockItem(BlockInit.SCORIA_BRICKS.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> CRACKED_SCORIA_BRICKS = REGISTER.register("cracked_scoria_bricks", () -> new BlockItem(BlockInit.CRACKED_SCORIA_BRICKS.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> CHISELED_SCORIA = REGISTER.register("chiseled_scoria", () -> new BlockItem(BlockInit.CHISELED_SCORIA.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> POLISHED_SCORIA = REGISTER.register("polished_scoria", () -> new BlockItem(BlockInit.POLISHED_SCORIA.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_SLAB = REGISTER.register("scoria_slab", () -> new BlockItem(BlockInit.SCORIA_SLAB.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_BRICK_SLAB = REGISTER.register("scoria_brick_slab", () -> new BlockItem(BlockInit.SCORIA_BRICK_SLAB.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> CRACKED_SCORIA_BRICK_SLAB = REGISTER.register("cracked_scoria_brick_slab", () -> new BlockItem(BlockInit.CRACKED_SCORIA_BRICK_SLAB.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_STAIRS = REGISTER.register("scoria_stairs", () -> new BlockItem(BlockInit.SCORIA_STAIRS.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_BRICK_STAIRS = REGISTER.register("scoria_brick_stairs", () -> new BlockItem(BlockInit.SCORIA_BRICK_STAIRS.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> CRACKED_SCORIA_BRICK_STAIRS = REGISTER.register("cracked_scoria_brick_stairs", () -> new BlockItem(BlockInit.CRACKED_SCORIA_BRICK_STAIRS.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_WALL = REGISTER.register("scoria_wall", () -> new BlockItem(BlockInit.SCORIA_WALL.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> SCORIA_BRICK_WALL = REGISTER.register("scoria_brick_wall", () -> new BlockItem(BlockInit.SCORIA_BRICK_WALL.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
    public static final RegistryObject<BlockItem> CRACKED_SCORIA_BRICK_WALL = REGISTER.register("cracked_scoria_brick_wall", () -> new BlockItem(BlockInit.CRACKED_SCORIA_BRICK_WALL.get(), new Item.Properties().group(GoblinsAndDungeons.GROUP)));
   
}
