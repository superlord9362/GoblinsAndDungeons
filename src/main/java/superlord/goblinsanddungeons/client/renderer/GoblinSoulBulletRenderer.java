package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;

public class GoblinSoulBulletRenderer extends ThrownItemRenderer<GoblinSoulBulletEntity> {

    public GoblinSoulBulletRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}