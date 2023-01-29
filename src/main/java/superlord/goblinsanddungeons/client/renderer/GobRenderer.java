package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GobModel;
import superlord.goblinsanddungeons.common.entity.Gob;

public class GobRenderer extends MobRenderer<Gob, EntityModel<Gob>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/gob.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GobModel(renderManagerIn.bakeLayer(ClientEvents.GOB)), 0.375F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Gob entity) {
		return TEXTURE;
	}

}
