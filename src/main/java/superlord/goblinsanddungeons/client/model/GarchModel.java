package superlord.goblinsanddungeons.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.entity.GarchEntity;

/**
 * garch - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GarchModel<T extends MobEntity & IRangedAttackMob> extends EntityModel<GarchEntity> implements IHasArm {
	public ModelRenderer Headlayer;
	public ModelRenderer Body;
	public ModelRenderer RightArm;
	public ModelRenderer LeftArm;
	public ModelRenderer Rightleg;
	public ModelRenderer Leftleg;
	public ModelRenderer Head;
	public ModelRenderer Hat;
	public ModelRenderer Leftear;
	public ModelRenderer Rightear;

	public GarchModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.Rightleg = new ModelRenderer(this, 0, 22);
		this.Rightleg.setRotationPoint(-1.0F, 18.0F, 0.0F);
		this.Rightleg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Rightear = new ModelRenderer(this, 18, 2);
		this.Rightear.mirror = true;
		this.Rightear.setRotationPoint(-3.0F, -5.0F, 1.0F);
		this.Rightear.addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.RightArm = new ModelRenderer(this, 12, 14);
		this.RightArm.setRotationPoint(-2.0F, 13.0F, 0.0F);
		this.RightArm.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Hat = new ModelRenderer(this, 24, 21);
		this.Hat.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.Hat.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.Leftleg = new ModelRenderer(this, 8, 23);
		this.Leftleg.setRotationPoint(1.0F, 18.0F, 0.0F);
		this.Leftleg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Body = new ModelRenderer(this, 0, 14);
		this.Body.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.Body.addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Leftear = new ModelRenderer(this, 18, 2);
		this.Leftear.setRotationPoint(3.0F, -5.0F, 1.0F);
		this.Leftear.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.LeftArm = new ModelRenderer(this, 12, 14);
		this.LeftArm.mirror = true;
		this.LeftArm.setRotationPoint(2.0F, 13.0F, 0.0F);
		this.LeftArm.addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Headlayer = new ModelRenderer(this, 24, 0);
		this.Headlayer.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.Headlayer.addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.2F, 0.2F, 0.2F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Head.addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.Head.addChild(this.Rightear);
		this.Headlayer.addChild(this.Hat);
		this.Head.addChild(this.Leftear);
		this.Headlayer.addChild(this.Head);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
		ImmutableList.of(this.Rightleg, this.RightArm, this.Leftleg, this.Body, this.LeftArm, this.Headlayer).forEach((modelRenderer) -> { 
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(GarchEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Headlayer.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.Headlayer.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.Rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.Leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.RightArm.rotateAngleY = -0.1F + this.Headlayer.rotateAngleY;
		this.LeftArm.rotateAngleY = 0.1F + this.Headlayer.rotateAngleY + 0.4F;
		this.RightArm.rotateAngleX = (-(float)Math.PI / 2F) + this.Headlayer.rotateAngleX;
		this.LeftArm.rotateAngleX = (-(float)Math.PI / 2F) + this.Headlayer.rotateAngleX;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}


	public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
		float f = sideIn == HandSide.RIGHT ? 1.0F : -1.0F;
		ModelRenderer modelrenderer = this.getArmForSide(sideIn);
		modelrenderer.rotationPointX += f;
		modelrenderer.translateRotate(matrixStackIn);
		modelrenderer.rotationPointX -= f;
		matrixStackIn.translate(-0.1, -0.3, 0);
	}

	protected ModelRenderer getArmForSide(HandSide side) {
		return side == HandSide.LEFT ? this.LeftArm : this.RightArm;
	}

}
