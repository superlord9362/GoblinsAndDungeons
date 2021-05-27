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
import superlord.goblinsanddungeons.entity.GoomEntity;

/**
 * GoomModel - superlord9362
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GoomModel<T extends Entity> extends EntityModel<GoomEntity> {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer LeftArm;
    public ModelRenderer RightArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    public ModelRenderer Bomb;
    public ModelRenderer FuseLit;
    public ModelRenderer Bomb2;
    public ModelRenderer Fuse;

    public GoomModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightEar = new ModelRenderer(this, 24, 0);
        this.RightEar.setRotationPoint(-4.0F, -4.0F, 0.0F);
        this.RightEar.addBox(-4.0F, -1.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 12, 13);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(2.0F, 0.5F, 0.0F);
        this.LeftArm.addBox(0.0F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 20);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(1.5F, 5.0F, 0.0F);
        this.LeftLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Bomb2 = new ModelRenderer(this, 45, 16);
        this.Bomb2.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Bomb2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.FuseLit = new ModelRenderer(this, 0, 3);
        this.FuseLit.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.FuseLit.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 12, 13);
        this.RightArm.setRotationPoint(-2.0F, 0.5F, 0.0F);
        this.RightArm.addBox(-1.0F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 20);
        this.RightLeg.setRotationPoint(-1.5F, 5.0F, 0.0F);
        this.RightLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Fuse = new ModelRenderer(this, 0, 0);
        this.Fuse.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Fuse.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 12);
        this.Body.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Body.addBox(-2.0F, 0.0F, -1.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Bomb = new ModelRenderer(this, 40, 19);
        this.Bomb.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.Bomb.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 24, 4);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(4.0F, -4.0F, 0.0F);
        this.LeftEar.addBox(0.0F, -1.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Body.addChild(this.LeftArm);
        this.Body.addChild(this.LeftLeg);
        this.Bomb.addChild(this.Bomb2);
        this.Head.addChild(this.FuseLit);
        this.Body.addChild(this.RightArm);
        this.Body.addChild(this.RightLeg);
        this.Bomb2.addChild(this.Fuse);
        this.Body.addChild(this.Head);
        this.Head.addChild(this.Bomb);
        this.Head.addChild(this.LeftEar);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GoomEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
