package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.WeaknessOrb;

public class WeaknessOrbRenderer extends ThrownItemRenderer<WeaknessOrb> {

    public WeaknessOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}