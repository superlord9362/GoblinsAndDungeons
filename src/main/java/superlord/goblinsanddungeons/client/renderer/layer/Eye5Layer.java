package superlord.goblinsanddungeons.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.entity.Beholder;

public class Eye5Layer extends RenderLayer<Beholder, EntityModel<Beholder>> {
	
	public Eye5Layer(RenderLayerParent<Beholder, EntityModel<Beholder>> p_117346_) {
		super(p_117346_);
	}

	private static final RenderType TEXTURE = RenderType.entityCutoutNoCull(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/beholder/eye_5_shut.png"));

	@Override
	   public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Beholder beholder, float p_116670_, float p_116671_, float p_116672_, float p_116673_, float p_116674_, float p_116675_) {
		RenderType tex = null;
		if (beholder.isStalk5Shut()) {
			tex = TEXTURE;
			if(tex != null){
				VertexConsumer ivertexbuilder = bufferIn.getBuffer(TEXTURE);
		        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(beholder, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
	        }
		}
	}

	
	
}
