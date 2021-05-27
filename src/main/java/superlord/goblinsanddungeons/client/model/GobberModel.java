package superlord.goblinsanddungeons.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.entity.GobberEntity;

/**
 * GobberModel - superlord9362
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GobberModel<T extends Entity> extends EntityModel<GobberEntity> {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer RightPauldron;
    public ModelRenderer LeftPauldron;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer LeftEar;
    public ModelRenderer RightEar;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;

    public GobberModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightArm = new ModelRenderer(this, 28, 13);
        this.RightArm.setRotationPoint(-1.0F, 1.0F, 0.0F);
        this.RightArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightPauldron = new ModelRenderer(this, 36, 22);
        this.RightPauldron.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.RightPauldron.addBox(-5.0F, -2.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.RightEar = new ModelRenderer(this, 22, 3);
        this.RightEar.mirror = true;
        this.RightEar.setRotationPoint(-4.0F, -3.0F, 0.0F);
        this.RightEar.addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.LeftPauldron = new ModelRenderer(this, 36, 22);
        this.LeftPauldron.mirror = true;
        this.LeftPauldron.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.LeftPauldron.addBox(0.0F, -2.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 22, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(4.0F, -3.0F, 0.0F);
        this.LeftEar.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 13);
        this.Body.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.Body.addBox(-5.0F, 0.0F, -4.0F, 10.0F, 9.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 28, 5);
        this.LeftArm.setRotationPoint(1.0F, 1.0F, 0.0F);
        this.LeftArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -5.0F, -3.0F, 8.0F, 7.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 36, 9);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-3.0F, 8.0F, 0.0F);
        this.RightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 36, 9);
        this.LeftLeg.setRotationPoint(3.0F, 8.0F, 0.0F);
        this.LeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightPauldron.addChild(this.RightArm);
        this.Body.addChild(this.RightPauldron);
        this.Head.addChild(this.RightEar);
        this.Body.addChild(this.LeftPauldron);
        this.Head.addChild(this.LeftEar);
        this.LeftPauldron.addChild(this.LeftArm);
        this.Body.addChild(this.Head);
        this.Body.addChild(this.RightLeg);
        this.Body.addChild(this.LeftLeg);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GobberEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
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
}
