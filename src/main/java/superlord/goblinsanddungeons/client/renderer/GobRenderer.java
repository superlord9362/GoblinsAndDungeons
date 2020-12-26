package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GobModel;
import superlord.goblinsanddungeons.entity.GobEntity;

public class GobRenderer extends MobRenderer<GobEntity, GobModel<GobEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gob.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GobModel<>(), 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(GobEntity entity) {
		return TEXTURE;
	}

}
