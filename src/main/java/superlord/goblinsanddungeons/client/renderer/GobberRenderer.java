package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GobberModel;
import superlord.goblinsanddungeons.entity.GobberEntity;

public class GobberRenderer extends MobRenderer<GobberEntity, GobberModel<GobberEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gobber.png");
	
	public GobberRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GobberModel<>(), 0.375F);
	}

	@Override
	public ResourceLocation getEntityTexture(GobberEntity entity) {
		return TEXTURE;
	}

}
