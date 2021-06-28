package superlord.goblinsanddungeons.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.entity.GobloEntity;

/**
 * goblo - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SleepingGobloModel<T extends Entity> extends EntityModel<GobloEntity> implements IHasArm {
    public ModelRenderer Body;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer Head;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public SleepingGobloModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftLeg = new ModelRenderer(this, 29, 13);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.5F, 20.0F, -5.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftLeg, -0.9452703202169677F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 41, 0);
        this.RightArm.setRotationPoint(-5.0F, 20.0F, 2.0F);
        this.RightArm.addBox(-2.0F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightArm, -1.1798425477165557F, 0.46914448828868976F, 0.0F);
        this.RightEar = new ModelRenderer(this, 29, 0);
        this.RightEar.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.RightEar.addBox(-5.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 29, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(5.0F, 0.0F, -1.0F);
        this.LeftEar.addBox(0.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 41, 0);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(5.0F, 20.0F, 2.0F);
        this.LeftArm.addBox(0.0F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftArm, -1.1798425477165557F, -0.46914448828868976F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 4.5F);
        this.Head.addBox(-5.0F, -2.0F, -9.0F, 10.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head, -0.23457224414434488F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 11);
        this.Body.setRotationPoint(0.0F, 20.0F, 5.0F);
        this.Body.addBox(-5.0F, 0.0F, -4.5F, 10.0F, 10.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body, -1.5707963267948966F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 29, 13);
        this.RightLeg.setRotationPoint(-2.5F, 20.0F, -5.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightLeg, -0.9452703202169677F, 0.0F, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Body.addChild(this.Head);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.LeftLeg, this.RightArm, this.LeftArm, this.Body, this.RightLeg).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(GobloEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

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
		
	}
}
