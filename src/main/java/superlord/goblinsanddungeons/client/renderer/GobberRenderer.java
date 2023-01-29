package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GobberModel;
import superlord.goblinsanddungeons.common.entity.Gobber;

public class GobberRenderer extends MobRenderer<Gobber, EntityModel<Gobber>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gobber.png");
	private static final ResourceLocation SLEEPING = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gobber_sleeping.png");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobberRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GobberModel(renderManagerIn.bakeLayer(ClientEvents.GOBBER)), 0.375F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Gobber entity) {
		if (entity.isSleeping()) {
			return SLEEPING;
		} else {
			return TEXTURE;
		}
	}

}
