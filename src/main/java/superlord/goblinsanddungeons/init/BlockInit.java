package superlord.goblinsanddungeons.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.blocks.AshedSoulSandBlock;
import superlord.goblinsanddungeons.common.blocks.SoulAshCampfireBlock;
import superlord.goblinsanddungeons.common.blocks.UrnBlock;
import superlord.goblinsanddungeons.common.compat.RegistryHelper;
import superlord.goblinsanddungeons.common.compat.VerticalSlabBlock;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.MOD)
public class BlockInit {
	
	public static final RegistryHelper HELPER = GoblinsAndDungeons.REGISTRY_HELPER;
	
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, GoblinsAndDungeons.MOD_ID);

    public static final RegistryObject<Block> URN = REGISTER.register("urn", () -> new UrnBlock(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundTypeInit.URN).strength(0.3F).noOcclusion()));

    public static final RegistryObject<Block> SCORIA = REGISTER.register("scoria", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_SCORIA = REGISTER.register("polished_scoria", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICKS = REGISTER.register("scoria_bricks", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_SCORIA_BRICKS = REGISTER.register("cracked_scoria_bricks", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CHISELED_SCORIA = REGISTER.register("chiseled_scoria", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_SLAB = REGISTER.register("scoria_slab", () -> new SlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_SCORIA_SLAB = REGISTER.register("polished_scoria_slab", () -> new SlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_SLAB = REGISTER.register("scoria_brick_slab", () -> new SlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_STAIRS = REGISTER.register("scoria_stairs", () -> new StairBlock(SCORIA.get().defaultBlockState(), Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
   	public static final RegistryObject<Block> POLISHED_SCORIA_STAIRS = REGISTER.register("polished_scoria_stairs", () -> new StairBlock(POLISHED_SCORIA.get().defaultBlockState(), Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_BRICK_STAIRS = REGISTER.register("scoria_brick_stairs", () -> new StairBlock(SCORIA_BRICKS.get().defaultBlockState(), Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_WALL = REGISTER.register("scoria_wall", () -> new WallBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_SCORIA_WALL = REGISTER.register("polished_scoria_wall", () -> new WallBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_WALL = REGISTER.register("scoria_brick_wall", () -> new WallBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> ASHED_SOUL_SAND = REGISTER.register("ashed_soul_sand", () -> new AshedSoulSandBlock(Block.Properties.copy(Blocks.SOUL_SAND)));

    public static final RegistryObject<Block> SOUL_ASH_CAMPFIRE = REGISTER.register("soul_ash_campfire", () -> new SoulAshCampfireBlock(true, 1, Block.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_ASH_SOUL_CAMPFIRE = REGISTER.register("soul_ash_soul_campfire", () -> new SoulAshCampfireBlock(false, 2, Block.Properties.copy(Blocks.SOUL_CAMPFIRE)));

    public static final RegistryObject<Block> CHISELED_SCORIA_BRICKS = HELPER.createCompatBlock("quark", "chiseled_scoria_bricks", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);
    public static final RegistryObject<Block> SCORIA_PILLAR = HELPER.createCompatBlock("quark", "scoria_pillar", () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);
    public static final RegistryObject<Block> SCORIA_PAVEMENT = HELPER.createCompatBlock("quark", "scoria_pavement", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);
    
    public static final RegistryObject<Block> SCORIA_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "scoria_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);
    public static final RegistryObject<Block> POLISHED_SCORIA_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "polished_scoria_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);
    public static final RegistryObject<Block> SCORIA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "scoria_brick_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GoblinsAndDungeons.GROUP);

}
