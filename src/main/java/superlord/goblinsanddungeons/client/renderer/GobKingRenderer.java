package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GoblinKingModel;
import superlord.goblinsanddungeons.entity.GobKingEntity;

public class GobKingRenderer extends MobRenderer<GobKingEntity, GoblinKingModel<GobKingEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goblin_king.png");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobKingRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GoblinKingModel(renderManagerIn.bakeLayer(ClientEvents.GOB_KING)), 0.375F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(GobKingEntity p_114482_) {
		return TEXTURE;
	}

}
