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
import superlord.goblinsanddungeons.entity.MimicEntity;

/**
 * mimic goblin - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class MimicModel<T extends Entity> extends EntityModel<MimicEntity> {
    public ModelRenderer Base;
    public ModelRenderer Head;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer Top;
    public ModelRenderer lock;
    public ModelRenderer Leftear;
    public ModelRenderer Rightear;

    public MimicModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.RightLeg = new ModelRenderer(this, 6, 26);
        this.RightLeg.setRotationPoint(-2.0F, 19.0F, 0.0F);
        this.RightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.lock = new ModelRenderer(this, 0, 0);
        this.lock.setRotationPoint(0.0F, -2.0F, -14.0F);
        this.lock.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Rightear = new ModelRenderer(this, 24, 47);
        this.Rightear.mirror = true;
        this.Rightear.setRotationPoint(-4.0F, -5.0F, 0.0F);
        this.Rightear.addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 43);
        this.Head.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.Head.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Base = new ModelRenderer(this, 0, 19);
        this.Base.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.Base.addBox(-7.0F, 0.0F, -7.0F, 14.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.Top = new ModelRenderer(this, 0, 0);
        this.Top.setRotationPoint(0.0F, 1.0F, 7.0F);
        this.Top.addBox(-7.0F, -5.0F, -14.0F, 14.0F, 5.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Top, -0.5462880425584197F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 6, 26);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, 19.0F, 0.0F);
        this.LeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Leftear = new ModelRenderer(this, 24, 47);
        this.Leftear.setRotationPoint(4.0F, -5.0F, 0.0F);
        this.Leftear.addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Top.addChild(this.lock);
        this.Head.addChild(this.Rightear);
        this.Base.addChild(this.Top);
        this.Head.addChild(this.Leftear);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.RightLeg, this.Head, this.Base, this.LeftLeg).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
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
