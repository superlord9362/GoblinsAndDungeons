package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GarchModel;
import superlord.goblinsanddungeons.common.entity.Garch;

public class GarchRenderer extends MobRenderer<Garch, GarchModel<Garch>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/garch.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GarchRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GarchModel(renderManagerIn.bakeLayer(ClientEvents.GARCH)), 0.375F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Garch entity) {
		return TEXTURE;
	}

}
