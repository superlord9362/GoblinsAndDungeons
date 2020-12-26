package superlord.goblinsanddungeons.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
    
}
