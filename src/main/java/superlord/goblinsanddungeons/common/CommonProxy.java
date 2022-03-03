package superlord.goblinsanddungeons.common;

import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
	
	public void init() {
    }

    public void clientInit() {
    }

	public Object getArmorRenderProperties() {
        return null;
    }
	
}
