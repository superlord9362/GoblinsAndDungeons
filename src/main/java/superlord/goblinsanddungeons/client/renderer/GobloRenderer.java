package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GobloModel;
import superlord.goblinsanddungeons.entity.GobloEntity;

public class GobloRenderer extends MobRenderer<GobloEntity, GobloModel<GobloEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goblo.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobloRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GobloModel<>(), 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(GobloEntity entity) {
		return TEXTURE;
	}

}
