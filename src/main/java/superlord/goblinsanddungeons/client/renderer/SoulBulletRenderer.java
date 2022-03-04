package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import superlord.goblinsanddungeons.entity.SoulBulletEntity;

public class SoulBulletRenderer extends ThrownItemRenderer<SoulBulletEntity> {

    public SoulBulletRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}