package superlord.goblinsanddungeons.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class TileEntityInit {
	
	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, GoblinsAndDungeons.MOD_ID);
	
	//public static final RegistryObject<TileEntityType<SoulAshCampfireTileEntity>> SOUL_ASH_CAMPFIRE = REGISTER.register("soul_ash_campfire", () -> TileEntityType.Builder.create(SoulAshCampfireTileEntity::new, BlockInit.SOUL_ASH_CAMPFIRE.get(), BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get()).build(null));


}
