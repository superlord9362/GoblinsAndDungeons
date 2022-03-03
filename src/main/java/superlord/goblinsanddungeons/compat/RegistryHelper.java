package superlord.goblinsanddungeons.compat;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHelper {
	
	@SuppressWarnings("unused")
	private final String modId;
	private final DeferredRegister<Item> itemRegister;
	private final DeferredRegister<Block> blockRegister;
	
	public RegistryHelper(String modId) {
		this.modId = modId;
		this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
		this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
	}
	
	public DeferredRegister<Item> getDeferredItemRegister() {
		return this.itemRegister;
	}
	
	public DeferredRegister<Block> getDeferredBlockRegister() {
		return this.blockRegister;
	}
	
	public <B extends Block> RegistryObject<B> createCompatBlock(String modId, String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group) {
		CreativeModeTab determineGroup = ModList.get().isLoaded(modId) || modId == "indev" ? group : null;
		RegistryObject<B> block = this.blockRegister.register(name, supplier);
		this.itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(determineGroup)));
		return block;
	}

}
