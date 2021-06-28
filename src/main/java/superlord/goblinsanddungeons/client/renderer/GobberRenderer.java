package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GobberModel;
import superlord.goblinsanddungeons.entity.GobberEntity;

public class GobberRenderer extends MobRenderer<GobberEntity, GobberModel<GobberEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gobber.png");
	private static final ResourceLocation SLEEPING = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gobber_sleeping.png");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobberRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GobberModel<>(), 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(GobberEntity entity) {
		if (entity.isSleeping()) {
			return SLEEPING;
		} else {
			return TEXTURE;
		}
	}

}
