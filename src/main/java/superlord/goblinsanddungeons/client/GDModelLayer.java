package superlord.goblinsanddungeons.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import superlord.goblinsanddungeons.client.model.armor.GoblinCrownModel;

@OnlyIn(Dist.CLIENT)
public class GDModelLayer {
	
    public static final ModelLayerLocation GOBLIN_CROWN = createLocation("goblin_crown", "main");

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GOBLIN_CROWN, () -> GoblinCrownModel.createArmorLayer(new CubeDeformation(0.5F)));
    }
    
    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation("golbinsanddungeons", model), layer);
    }
}
