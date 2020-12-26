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
import superlord.goblinsanddungeons.entity.OgreEntity;

/**
 * Ogre - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class OgreModel<T extends Entity> extends EntityModel<OgreEntity> {
    public ModelRenderer Chestlayer;
    public ModelRenderer Head;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer Chest;
    public ModelRenderer Belly;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer cloth;
    public ModelRenderer Jaw;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    public ModelRenderer tusk1;
    public ModelRenderer tusk2;
    public ModelRenderer teeth;

    public OgreModel() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.tusk2 = new ModelRenderer(this, 146, 0);
        this.tusk2.setRotationPoint(-6.0F, -7.0F, -4.0F);
        this.tusk2.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.teeth = new ModelRenderer(this, 149, 6);
        this.teeth.setRotationPoint(0.0F, -7.0F, -4.0F);
        this.teeth.addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.cloth = new ModelRenderer(this, 214, 34);
        this.cloth.setRotationPoint(0.0F, 14.0F, -12.0F);
        this.cloth.addBox(-6.0F, 0.0F, -1.0F, 12.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Belly = new ModelRenderer(this, 0, 35);
        this.Belly.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.Belly.addBox(-17.0F, -13.0F, -16.0F, 34.0F, 27.0F, 31.0F, 0.0F, 0.0F, 0.0F);
        this.RightEar = new ModelRenderer(this, 0, 0);
        this.RightEar.setRotationPoint(-8.0F, -11.0F, -1.0F);
        this.RightEar.addBox(-4.0F, 0.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Chestlayer = new ModelRenderer(this, 0, 0);
        this.Chestlayer.setRotationPoint(0.0F, -26.0F, 1.0F);
        this.Chestlayer.addBox(-13.0F, -12.0F, -12.0F, 26.0F, 12.0F, 23.0F, 0.5F, 0.5F, 0.5F);
        this.RightLeg = new ModelRenderer(this, 162, 0);
        this.RightLeg.setRotationPoint(-9.0F, 1.0F, 0.0F);
        this.RightLeg.addBox(-6.0F, 0.0F, -5.0F, 11.0F, 23.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 162, 0);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(9.0F, 1.0F, 0.0F);
        this.LeftLeg.addBox(-5.0F, 0.0F, -5.0F, 11.0F, 23.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 0, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(8.0F, -11.0F, -1.0F);
        this.LeftEar.addBox(0.0F, 0.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 98, 0);
        this.Head.setRotationPoint(0.0F, -38.0F, -5.0F);
        this.Head.addBox(-8.0F, -15.0F, -12.0F, 16.0F, 19.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 130, 69);
        this.RightArm.setRotationPoint(-13.0F, -9.0F, -1.0F);
        this.RightArm.addBox(-10.0F, -3.0F, -5.0F, 11.0F, 37.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightArm, 0.0F, 0.0F, 0.18203784630933073F);
        this.Chest = new ModelRenderer(this, 139, 34);
        this.Chest.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Chest.addBox(-13.0F, -12.0F, -12.0F, 26.0F, 12.0F, 23.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 130, 69);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(13.0F, -9.0F, -1.0F);
        this.LeftArm.addBox(-1.0F, -3.0F, -5.0F, 11.0F, 37.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftArm, 0.0F, 0.0F, -0.18203784630933073F);
        this.Jaw = new ModelRenderer(this, 75, 0);
        this.Jaw.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.Jaw.addBox(-7.0F, -7.0F, -4.0F, 14.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tusk1 = new ModelRenderer(this, 146, 0);
        this.tusk1.mirror = true;
        this.tusk1.setRotationPoint(6.0F, -7.0F, -4.0F);
        this.tusk1.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Jaw.addChild(this.tusk2);
        this.Jaw.addChild(this.teeth);
        this.Belly.addChild(this.cloth);
        this.Chest.addChild(this.Belly);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Chest.addChild(this.RightArm);
        this.Chestlayer.addChild(this.Chest);
        this.Chest.addChild(this.LeftArm);
        this.Head.addChild(this.Jaw);
        this.Jaw.addChild(this.tusk1);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Chestlayer, this.RightLeg, this.LeftLeg, this.Head).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(OgreEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
