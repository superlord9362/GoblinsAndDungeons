package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GarchModel;
import superlord.goblinsanddungeons.entity.GarchEntity;

public class GarchRenderer extends MobRenderer<GarchEntity, GarchModel<GarchEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/garch.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GarchRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GarchModel<>(), 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(GarchEntity entity) {
		return TEXTURE;
	}

}
