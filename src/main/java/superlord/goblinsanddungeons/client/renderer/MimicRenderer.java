package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.HiddenMimicModel;
import superlord.goblinsanddungeons.client.model.MimicModel;
import superlord.goblinsanddungeons.common.entity.Mimic;

public class MimicRenderer extends MobRenderer<Mimic, EntityModel<Mimic>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/mimic.png");
	private static MimicModel<?> MIMIC;
	private static HiddenMimicModel<?> HIDDEN_MIMIC;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MimicRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new HiddenMimicModel(renderManager.bakeLayer(ClientEvents.HIDDEN_MIMIC)), 0.0F);
		MIMIC = new MimicModel(renderManager.bakeLayer(ClientEvents.MIMIC));
		HIDDEN_MIMIC = new HiddenMimicModel(renderManager.bakeLayer(ClientEvents.HIDDEN_MIMIC));
	}

	protected void scale(Mimic entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
		if (entitylivingbaseIn.isHiding()) {
			model = HIDDEN_MIMIC;
		} else {
			model = MIMIC;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(Mimic entity) {
		return TEXTURE;
	}

	public boolean shouldRender(Mimic p_115913_, Frustum p_115914_, double p_115915_, double p_115916_, double p_115917_) {
		return true;
	}

	protected void setupRotations(Mimic p_115907_, PoseStack p_115908_, float p_115909_, float p_115910_, float p_115911_) {
		super.setupRotations(p_115907_, p_115908_, p_115909_, p_115910_ + 180.0F, p_115911_);
		p_115908_.translate(0.0D, 0.5D, 0.0D);
		p_115908_.translate(0.0D, -0.5D, 0.0D);
	}


}
