package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.BlindnessOrb;

public class BlindnessOrbRenderer extends ThrownItemRenderer<BlindnessOrb> {

    public BlindnessOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}