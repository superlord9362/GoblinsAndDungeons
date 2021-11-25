package superlord.goblinsanddungeons.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.tile.SoulAshCampfireTileEntity;

public class TileEntityInit {
	
	public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, GoblinsAndDungeons.MOD_ID);
	
	public static final RegistryObject<TileEntityType<SoulAshCampfireTileEntity>> SOUL_ASH_CAMPFIRE = REGISTER.register("soul_ash_campfire", () -> TileEntityType.Builder.create(SoulAshCampfireTileEntity::new, BlockInit.SOUL_ASH_CAMPFIRE.get(), BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get()).build(null));


}
