package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.TeleportationOrb;

public class TeleportationOrbRenderer extends ThrownItemRenderer<TeleportationOrb> {

    public TeleportationOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}