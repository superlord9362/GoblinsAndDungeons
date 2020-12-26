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
 * goom - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GoomModel<T extends Entity> extends EntityModel<GoomEntity> {
    public ModelRenderer Body;
    public ModelRenderer Rightleg;
    public ModelRenderer Leftleg;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer Head;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    public ModelRenderer Bombbody;
    public ModelRenderer Bomb2;
    public ModelRenderer Bomb3;

    public GoomModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 12);
        this.Body.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Body.addBox(-2.0F, 0.0F, -1.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Bomb2 = new ModelRenderer(this, 45, 16);
        this.Bomb2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Bomb2.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Bomb3 = new ModelRenderer(this, 53, 15);
        this.Bomb3.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Bomb3.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.RightEar = new ModelRenderer(this, 24, 0);
        this.RightEar.setRotationPoint(-4.0F, -4.0F, 1.0F);
        this.RightEar.addBox(-4.0F, -1.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 12, 12);
        this.RightArm.mirror = true;
        this.RightArm.setRotationPoint(2.0F, 15.0F, 0.0F);
        this.RightArm.addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Leftleg = new ModelRenderer(this, 0, 19);
        this.Leftleg.setRotationPoint(-1.0F, 20.0F, 0.0F);
        this.Leftleg.addBox(-1.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Rightleg = new ModelRenderer(this, 6, 19);
        this.Rightleg.setRotationPoint(1.0F, 20.0F, 0.0F);
        this.Rightleg.addBox(0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 34, 0);
        this.LeftEar.setRotationPoint(4.0F, -4.0F, 1.0F);
        this.LeftEar.addBox(0.0F, -1.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Bombbody = new ModelRenderer(this, 40, 19);
        this.Bombbody.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.Bombbody.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 12, 12);
        this.LeftArm.setRotationPoint(-2.0F, 15.0F, 0.0F);
        this.LeftArm.addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Bombbody.addChild(this.Bomb2);
        this.Bomb2.addChild(this.Bomb3);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.Bombbody);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body, this.RightArm, this.Leftleg, this.Rightleg, this.Head, this.LeftArm).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GoomEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
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
}
