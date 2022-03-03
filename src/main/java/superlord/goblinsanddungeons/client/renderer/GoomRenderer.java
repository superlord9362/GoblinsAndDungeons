package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GoomModel;
import superlord.goblinsanddungeons.entity.GoomEntity;

public class GoomRenderer extends MobRenderer<GoomEntity, GoomModel<GoomEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goom.png");
	private static final ResourceLocation EXPLODED_TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goom_ignited.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GoomRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GoomModel(renderManagerIn.bakeLayer(ClientEvents.GOOM)), 0.375F);
	}

	@Override
	public ResourceLocation getTextureLocation(GoomEntity entity) {
		if (entity.isBlownUp()) {
			return EXPLODED_TEXTURE;
		} else {
			return TEXTURE;
		}
	}

}
