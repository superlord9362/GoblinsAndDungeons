package superlord.goblinsanddungeons.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.blocks.CandleBlock;
import superlord.goblinsanddungeons.blocks.LockedChestBlock;
import superlord.goblinsanddungeons.blocks.UrnBlock;

public class BlockInit {
	
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, GoblinsAndDungeons.MOD_ID);

    public static final RegistryObject<Block> CANDLE = REGISTER.register("candle", () -> new CandleBlock(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).sound(SoundType.SAND).setLightLevel((state) -> {
        return CandleBlock.isInBadEnvironment(state) ? 0 : 3 + 3 * state.get(CandleBlock.CANDLES);
    }).hardnessAndResistance(0.1F).notSolid(), ParticleTypes.FLAME));
    public static final RegistryObject<Block> URN = REGISTER.register("urn", () -> new UrnBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).sound(SoundType.STONE).hardnessAndResistance(0.3F).notSolid()));
    public static final RegistryObject<Block> LOCKED_CHEST = REGISTER.register("locked_chest", () -> new LockedChestBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.5F).notSolid(), () -> {
        return TileEntityInit.LOCKED_CHEST.get();
    }));
    
    public static final RegistryObject<Block> SCORIA = REGISTER.register("scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_SCORIA = REGISTER.register("polished_scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICKS = REGISTER.register("scoria_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_SCORIA_BRICKS = REGISTER.register("cracked_scoria_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CHISELED_SCORIA = REGISTER.register("chiseled_scoria", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_SLAB = REGISTER.register("scoria_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_SLAB = REGISTER.register("scoria_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_SCORIA_BRICK_SLAB = REGISTER.register("cracked_scoria_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_STAIRS = REGISTER.register("scoria_stairs", () -> new StairsBlock(SCORIA.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> SCORIA_BRICK_STAIRS = REGISTER.register("scoria_brick_stairs", () -> new StairsBlock(SCORIA_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> CRACKED_SCORIA_BRICK_STAIRS = REGISTER.register("cracked_scoria_brick_stairs", () -> new StairsBlock(CRACKED_SCORIA_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_WALL = REGISTER.register("scoria_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> SCORIA_BRICK_WALL = REGISTER.register("scoria_brick_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_SCORIA_BRICK_WALL = REGISTER.register("cracked_scoria_brick_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F)));

}
