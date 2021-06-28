package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import superlord.goblinsanddungeons.entity.GDFallingBlockEntity;

public class FallingBlockRenderer extends EntityRenderer<GDFallingBlockEntity> {

	public FallingBlockRenderer(EntityRendererManager mgr) {
		super(mgr);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(GDFallingBlockEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
		BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		matrixStack.push();
		matrixStack.translate(0, 0.5F, 0);
		if (entity.getMode() == GDFallingBlockEntity.EnumFallingState.MOBILE) {
			matrixStack.rotate(new Quaternion(0, MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw), 0, true));
			matrixStack.rotate(new Quaternion(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch), 0, 0, true));
		} else {
			matrixStack.translate(0, MathHelper.lerp(partialTicks, entity.prevAnimY, entity.animY), 0);
			matrixStack.translate(0, -1, 0);
		}
		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		dispatcher.renderBlock(entity.getBlock(), matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
		matrixStack.pop();
	}
	
	@Override
	public ResourceLocation getEntityTexture(GDFallingBlockEntity entity) {
		return null;
	}
	
}
