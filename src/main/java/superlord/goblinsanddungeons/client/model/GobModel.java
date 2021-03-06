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
import superlord.goblinsanddungeons.entity.GobEntity;

/**
 * gob - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GobModel<T extends Entity> extends EntityModel<GobEntity> implements IHasArm {
    public ModelRenderer Body;
    public ModelRenderer Rightleg;
    public ModelRenderer Leftleg;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer Head;
    public ModelRenderer Gobhat;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public GobModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftEar = new ModelRenderer(this, 22, 3);
        this.LeftEar.setRotationPoint(4.0F, -4.0F, 1.0F);
        this.LeftEar.addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Rightleg = new ModelRenderer(this, 8, 19);
        this.Rightleg.setRotationPoint(1.0F, 20.0F, 0.0F);
        this.Rightleg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 12, 12);
        this.LeftArm.setRotationPoint(-2.0F, 16.0F, 0.0F);
        this.LeftArm.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 12, 12);
        this.RightArm.mirror = true;
        this.RightArm.setRotationPoint(2.0F, 16.0F, 0.0F);
        this.RightArm.addBox(0.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightEar = new ModelRenderer(this, 22, 0);
        this.RightEar.setRotationPoint(-4.0F, -4.0F, 1.0F);
        this.RightEar.addBox(-3.0F, 0.0F, -1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Leftleg = new ModelRenderer(this, 0, 19);
        this.Leftleg.setRotationPoint(-1.0F, 20.0F, 0.0F);
        this.Leftleg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Head.addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Gobhat = new ModelRenderer(this, 48, 0);
        this.Gobhat.setRotationPoint(0.0F, -6.0F, -1.0F);
        this.Gobhat.addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 12);
        this.Body.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Body.addBox(-2.0F, 0.0F, -1.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.Gobhat);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Rightleg, this.LeftArm, this.RightArm, this.Leftleg, this.Head, this.Body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GobEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.Leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
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
    
    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
		float f = sideIn == HandSide.RIGHT ? 1.0F : -1.0F;
		ModelRenderer modelrenderer = this.getArmForSide(sideIn);
		modelrenderer.rotationPointX += f;
		modelrenderer.translateRotate(matrixStackIn);
		modelrenderer.rotationPointX -= f;
		matrixStackIn.translate(-0.075, -0.225, 0);
		matrixStackIn.scale(0.75F, 0.75F, 0.75F);
	}

	protected ModelRenderer getArmForSide(HandSide side) {
		return side == HandSide.LEFT ? this.RightArm : this.LeftArm;
	}
	
}
