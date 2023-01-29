package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import superlord.goblinsanddungeons.common.entity.GDFallingBlock;

public class FallingBlockRenderer extends EntityRenderer<GDFallingBlock> {

	public FallingBlockRenderer(EntityRendererProvider.Context mgr) {
		super(mgr);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(GDFallingBlock entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
		matrixStack.pushPose();
		matrixStack.translate(0, 0.5F, 0);
		if (entity.getMode() == GDFallingBlock.EnumFallingState.MOBILE) {
			matrixStack.mulPose(new Quaternion(0, Mth.lerp(partialTicks, entity.yRotO, entity.yRot), 0, true));
			matrixStack.mulPose(new Quaternion(Mth.lerp(partialTicks, entity.xRotO, entity.xRot), 0, 0, true));
		} else {
			matrixStack.translate(0, Mth.lerp(partialTicks, entity.prevAnimY, entity.animY), 0);
			matrixStack.translate(0, -1, 0);
		}
		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		dispatcher.renderSingleBlock(entity.getBlock(), matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
		matrixStack.popPose();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getTextureLocation(GDFallingBlock entity) {
	      return TextureAtlas.LOCATION_BLOCKS;
	}
	
}
