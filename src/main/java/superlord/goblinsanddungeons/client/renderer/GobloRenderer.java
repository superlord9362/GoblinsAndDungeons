package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.GobloModel;
import superlord.goblinsanddungeons.client.model.SleepingGobloModel;
import superlord.goblinsanddungeons.common.entity.GobloEntity;

public class GobloRenderer extends MobRenderer<GobloEntity, EntityModel<GobloEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goblo.png");
	private static GobloModel<?> GOBLO;
	private static SleepingGobloModel<?> SLEEPING_GOBLO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobloRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GobloModel(renderManager.bakeLayer(ClientEvents.GOBLO)), 0.375F);
		this.addLayer(new ItemInHandLayer(this));
		GOBLO = new GobloModel(renderManager.bakeLayer(ClientEvents.GOBLO));
		SLEEPING_GOBLO = new SleepingGobloModel(renderManager.bakeLayer(ClientEvents.SLEEPING_GOBLO));
	}
	
    protected void scale(GobloEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
		if (entitylivingbaseIn.isSleeping()) {
			model = SLEEPING_GOBLO;
		} else {
			model = GOBLO;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(GobloEntity entity) {
			return TEXTURE;
	}

}
