package superlord.goblinsanddungeons.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * GoblinKing - superlord9362
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GoblinKingModel<T extends LivingEntity> extends EntityModel<T>  implements IHasArm {
	public ModelRenderer Body;
    public ModelRenderer RightArm;
    public ModelRenderer Head;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer HeldItemRight;
    public ModelRenderer LeftEar;
    public ModelRenderer Crown;
    public ModelRenderer RightEar;
    public ModelRenderer HeldItemLeft;

    public GoblinKingModel() {
        this.textureWidth = 64;
        this.textureHeight = 48;
        this.RightEar = new ModelRenderer(this, 30, 3);
        this.RightEar.mirror = true;
        this.RightEar.setRotationPoint(-5.0F, -4.0F, 0.0F);
        this.RightEar.addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-5.0F, -6.0F, -5.0F, 10.0F, 6.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 40, 11);
        this.LeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
        this.LeftArm.addBox(0.0F, -1.0F, -2.0F, 2.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 30, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(5.0F, -4.0F, 0.0F);
        this.LeftEar.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 52, 11);
        this.LeftLeg.setRotationPoint(2.0F, 9.0F, 0.0F);
        this.LeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 28, 16);
        this.RightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
        this.RightArm.addBox(-2.0F, -1.0F, -2.0F, 2.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Crown = new ModelRenderer(this, 40, 0);
        this.Crown.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Crown.addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 16);
        this.Body.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8.0F, 13.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.HeldItemLeft = new ModelRenderer(this, 0, 0);
        this.HeldItemLeft.setRotationPoint(-1.0F, 7.0F, 0.0F);
        this.HeldItemLeft.addBox(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.HeldItemRight = new ModelRenderer(this, 0, 0);
        this.HeldItemRight.setRotationPoint(-1.0F, 7.0F, 0.0F);
        this.HeldItemRight.addBox(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 52, 11);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-2.0F, 9.0F, 0.0F);
        this.RightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Body.addChild(this.Head);
        this.Body.addChild(this.LeftArm);
        this.Head.addChild(this.LeftEar);
        this.Body.addChild(this.LeftLeg);
        this.Body.addChild(this.RightArm);
        this.Head.addChild(this.Crown);
        this.LeftArm.addChild(this.HeldItemLeft);
        this.RightArm.addChild(this.HeldItemRight);
        this.Body.addChild(this.RightLeg);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.HeldItemRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.HeldItemLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
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
		matrixStackIn.translate(-0.25F, 0, 0.15);
		matrixStackIn.scale(1F, 1F, 1F);
	}

	protected ModelRenderer getArmForSide(HandSide side) {
		return side == HandSide.LEFT ? this.HeldItemLeft : this.HeldItemRight;
	}
}
