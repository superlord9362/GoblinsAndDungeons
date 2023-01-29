package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.ExplosiveOrb;

public class ExplosiveOrbRenderer extends ThrownItemRenderer<ExplosiveOrb> {

    public ExplosiveOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}