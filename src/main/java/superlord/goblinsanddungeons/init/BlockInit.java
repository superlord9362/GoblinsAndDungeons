package superlord.goblinsanddungeons.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.blocks.UrnBlock;

public class BlockInit {
	
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, GoblinsAndDungeons.MOD_ID);

    public static final RegistryObject<Block> URN = REGISTER.register("urn", () -> new UrnBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).sound(SoundType.STONE).hardnessAndResistance(0.3F).notSolid()));

    public static final RegistryObject<Block> SCORIA = REGISTER.register("scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_SCORIA = REGISTER.register("polished_scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICKS = REGISTER.register("scoria_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_SCORIA_BRICKS = REGISTER.register("cracked_scoria_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CHISELED_SCORIA = REGISTER.register("chiseled_scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_SLAB = REGISTER.register("scoria_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_SLAB = REGISTER.register("scoria_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_STAIRS = REGISTER.register("scoria_stairs", () -> new StairsBlock(SCORIA.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_BRICK_STAIRS = REGISTER.register("scoria_brick_stairs", () -> new StairsBlock(SCORIA_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_WALL = REGISTER.register("scoria_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_WALL = REGISTER.register("scoria_brick_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));

}
