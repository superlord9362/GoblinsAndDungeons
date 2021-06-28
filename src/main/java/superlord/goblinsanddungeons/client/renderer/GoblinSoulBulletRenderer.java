package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;

public class GoblinSoulBulletRenderer extends SpriteRenderer<GoblinSoulBulletEntity> {

    public GoblinSoulBulletRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}