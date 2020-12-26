package superlord.goblinsanddungeons;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.entity.GoomEntity;
import superlord.goblinsanddungeons.entity.HobGobEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.TileEntityInit;

@Mod(GoblinsAndDungeons.MOD_ID)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID)
public class GoblinsAndDungeons {
	
	public static final String MOD_ID = "goblinsanddungeons";
	
	public GoblinsAndDungeons() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);

		ItemInit.REGISTER.register(bus);
		BlockInit.REGISTER.register(bus);
		TileEntityInit.REGISTER.register(bus);
		EntityInit.REGISTER.register(bus);
	}
	
	private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
	}
	
	private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(EntityInit.GOB.get(), GobEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.HOBGOB.get(), HobGobEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOBLO.get(), GobloEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GARCH.get(), GarchEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.GOOM.get(), GoomEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(EntityInit.OGRE.get(), OgreEntity.createAttributes().create());
    }
	
	public final static ItemGroup GROUP = new ItemGroup("goblinsanddungeons_item_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.GOBLIN_CROWN.get());
        }
    };

}
