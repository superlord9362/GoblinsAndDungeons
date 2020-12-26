package superlord.goblinsanddungeons.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.tile.LockedChestTileEntity;

public class TileEntityInit {

	public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<TileEntityType<LockedChestTileEntity>> LOCKED_CHEST = REGISTER.register("locked_chest_tile_entity", () -> TileEntityType.Builder.<LockedChestTileEntity>create(LockedChestTileEntity::new, BlockInit.LOCKED_CHEST.get()).build(null));

}
