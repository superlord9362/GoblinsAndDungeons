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
import superlord.goblinsanddungeons.entity.HobGobEntity;

/**
 * hobgob - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HobgobModel<T extends Entity> extends EntityModel<HobGobEntity> {
    public ModelRenderer Body;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer Head;
    public ModelRenderer nose;
    public ModelRenderer Hair;
    public ModelRenderer Beard;
    public ModelRenderer Rightear;
    public ModelRenderer Leftear;

    public HobgobModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Leftear = new ModelRenderer(this, 36, 27);
        this.Leftear.setRotationPoint(4.0F, -4.0F, -3.0F);
        this.Leftear.addBox(0.0F, 0.0F, 0.0F, 4.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Beard = new ModelRenderer(this, 14, 46);
        this.Beard.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.Beard.addBox(-5.0F, -2.0F, -3.0F, 10.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -3.0F, -3.0F);
        this.Head.addBox(-4.0F, -5.0F, -7.0F, 8.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 18);
        this.Body.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Body.addBox(-6.0F, 0.0F, -6.0F, 12.0F, 16.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 46);
        this.RightLeg.setRotationPoint(-3.0F, 13.0F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 3.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 36, 1);
        this.Hair.setRotationPoint(0.0F, -1.0F, 3.0F);
        this.Hair.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 26, 0);
        this.nose.setRotationPoint(0.0F, -3.0F, -7.0F);
        this.nose.addBox(-2.0F, -1.0F, -4.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 48, 26);
        this.RightArm.setRotationPoint(-6.0F, 0.0F, 0.0F);
        this.RightArm.addBox(-3.0F, -2.0F, -2.0F, 3.0F, 21.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 46);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(3.0F, 13.0F, 0.0F);
        this.LeftLeg.addBox(-1.0F, 0.0F, -2.0F, 3.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 48, 26);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.LeftArm.addBox(0.0F, -2.0F, -2.0F, 3.0F, 21.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Rightear = new ModelRenderer(this, 36, 27);
        this.Rightear.mirror = true;
        this.Rightear.setRotationPoint(-4.0F, -4.0F, -3.0F);
        this.Rightear.addBox(-4.0F, 0.0F, 0.0F, 4.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.Leftear);
        this.Head.addChild(this.Beard);
        this.Head.addChild(this.Hair);
        this.Head.addChild(this.nose);
        this.Head.addChild(this.Rightear);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Head, this.Body, this.RightLeg, this.RightArm, this.LeftLeg, this.LeftArm).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(HobGobEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
