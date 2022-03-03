package superlord.goblinsanddungeons.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.renderer.item.CrownRenderProperties;
import superlord.goblinsanddungeons.common.CommonProxy;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	

    public void init(){
    }
    
    public void clientInit() {
    }
    
    @Override
    public Object getArmorRenderProperties() {
        return new CrownRenderProperties();
    }

}
