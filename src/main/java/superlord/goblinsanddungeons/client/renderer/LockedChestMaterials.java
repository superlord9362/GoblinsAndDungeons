package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.Atlases;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LockedChestMaterials {

	public static final ResourceLocation LOCKED_CHEST = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "model/locked_chest");
	  public static final ResourceLocation LOCKED_CHEST_LEFT = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "model/locked_chest_left");
	  public static final ResourceLocation LOCKED_CHEST_RIGHT = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "model/locked_chest_right");
	
	public static ResourceLocation chooseChestTexture(ChestType type) {
	    switch (type) {
	      case SINGLE:
	        return LOCKED_CHEST;
	      case RIGHT:
	        return LOCKED_CHEST_RIGHT;
	      case LEFT:
	        return LOCKED_CHEST_LEFT;
	      default:
	        return LOCKED_CHEST;
	  }
	}
	
	@SubscribeEvent
	  public static void onStitch(TextureStitchEvent.Pre event) {
	    if (!event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
	      return;
	    }

	    event.addSprite(LOCKED_CHEST);
	    event.addSprite(LOCKED_CHEST_LEFT);
	    event.addSprite(LOCKED_CHEST_RIGHT);
	  }

}
