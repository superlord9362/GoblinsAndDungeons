package superlord.goblinsanddungeons.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.entity.GobloEntity;

/**
 * goblo - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GobloModel<T extends Entity> extends EntityModel<GobloEntity> implements IHasArm {
    public ModelRenderer Body;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer Head;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public GobloModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 11);
        this.Body.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Body.addBox(-5.0F, 0.0F, -4.5F, 10.0F, 10.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 29, 13);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.5F, 20.0F, 0.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 41, 0);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(5.0F, 13.0F, 0.0F);
        this.LeftArm.addBox(0.0F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 29, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(5.0F, 0.0F, -1.0F);
        this.LeftEar.addBox(0.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 4.5F);
        this.Head.addBox(-5.0F, -2.0F, -9.0F, 10.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.RightEar = new ModelRenderer(this, 29, 0);
        this.RightEar.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.RightEar.addBox(-5.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 41, 0);
        this.RightArm.setRotationPoint(-5.0F, 13.0F, 0.0F);
        this.RightArm.addBox(-2.0F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 29, 13);
        this.RightLeg.setRotationPoint(-2.5F, 20.0F, 0.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.LeftEar);
        this.Body.addChild(this.Head);
        this.Head.addChild(this.RightEar);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body, this.LeftLeg, this.LeftArm, this.RightArm, this.RightLeg).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GobloEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
	@Override
	public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
		float f = sideIn == HandSide.RIGHT ? 1.0F : -1.0F;
		ModelRenderer modelrenderer = this.getArmForSide(sideIn);
		modelrenderer.rotationPointX += f;
		modelrenderer.translateRotate(matrixStackIn);
		modelrenderer.rotationPointX -= f;		
	}

	protected ModelRenderer getArmForSide(HandSide side) {
		return side == HandSide.LEFT ? this.LeftArm : this.RightArm;
	}
	
}
