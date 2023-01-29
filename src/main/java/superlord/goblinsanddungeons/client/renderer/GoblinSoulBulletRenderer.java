package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.common.entity.GoblinSoulBullet;

public class GoblinSoulBulletRenderer extends ThrownItemRenderer<GoblinSoulBullet> {

    public GoblinSoulBulletRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}