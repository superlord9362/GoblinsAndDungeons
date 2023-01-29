package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.LevitationOrb;

public class LevitationOrbRenderer extends ThrownItemRenderer<LevitationOrb> {

    public LevitationOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}