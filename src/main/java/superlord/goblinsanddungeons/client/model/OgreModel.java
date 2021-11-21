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
	public ModelRenderer Body;
	public ModelRenderer RightLeg;
	public ModelRenderer LeftLeg;
	public ModelRenderer Cloth;
	public ModelRenderer Chest;
	public ModelRenderer RightArm;
	public ModelRenderer LeftArm;
	public ModelRenderer Head;
	public ModelRenderer RightFist;
	public ModelRenderer LeftFist;
	public ModelRenderer nose;
	public ModelRenderer Jaw;
	public ModelRenderer RightEar;
	public ModelRenderer LetfEar;
	public ModelRenderer tusk1;
	public ModelRenderer tusk2;

	public OgreModel() {
		this.textureWidth = 256;
		this.textureHeight = 128;
		this.Body = new ModelRenderer(this, 72, 72);
		this.Body.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.Body.addBox(-17.0F, -20.0F, -14.0F, 34.0F, 28.0F, 28.0F, 0.0F, 0.0F, 0.0F);
		this.nose = new ModelRenderer(this, 52, 3);
		this.nose.setRotationPoint(0.0F, -14.0F, -11.0F);
		this.nose.addBox(-3.0F, 0.0F, -5.0F, 6.0F, 7.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.Jaw = new ModelRenderer(this, 0, 35);
		this.Jaw.setRotationPoint(0.0F, -7.0F, -5.0F);
		this.Jaw.addBox(-7.0F, 0.0F, -8.0F, 14.0F, 7.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.tusk1 = new ModelRenderer(this, 0, 0);
		this.tusk1.setRotationPoint(6.0F, 0.0F, -7.0F);
		this.tusk1.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.RightLeg = new ModelRenderer(this, 0, 84);
		this.RightLeg.setRotationPoint(-8.5F, 6.0F, 0.0F);
		this.RightLeg.addBox(-5.5F, 0.0F, -5.5F, 11.0F, 18.0F, 11.0F, 0.0F, 0.0F, 0.0F);
		this.Chest = new ModelRenderer(this, 0, 50);
		this.Chest.setRotationPoint(0.0F, -20.0F, 0.0F);
		this.Chest.addBox(-15.0F, -12.0F, -10.0F, 30.0F, 14.0F, 20.0F, 0.0F, 0.0F, 0.0F);
		this.LeftArm = new ModelRenderer(this, 162, 32);
		this.LeftArm.mirror = true;
		this.LeftArm.setRotationPoint(14.0F, -7.0F, 0.0F);
		this.LeftArm.addBox(0.0F, -3.0F, -5.0F, 8.0F, 29.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftArm, 0.0F, 0.0F, -0.13665927909957545F);
		this.RightArm = new ModelRenderer(this, 162, 32);
		this.RightArm.setRotationPoint(-14.0F, -7.0F, 0.0F);
		this.RightArm.addBox(-8.0F, -3.0F, -5.0F, 8.0F, 29.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(RightArm, 0.0F, 0.0F, 0.13665927909957545F);
		this.Cloth = new ModelRenderer(this, 48, 7);
		this.Cloth.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Cloth.addBox(-17.0F, 0.0F, -14.0F, 34.0F, 7.0F, 28.0F, 0.0F, 0.0F, 0.0F);
		this.LetfEar = new ModelRenderer(this, 48, 4);
		this.LetfEar.setRotationPoint(8.0F, -15.0F, -2.0F);
		this.LetfEar.addBox(0.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -7.0F, -2.0F);
		this.Head.addBox(-8.0F, -19.0F, -11.0F, 16.0F, 19.0F, 16.0F, 0.0F, 0.0F, 0.0F);
		this.LeftLeg = new ModelRenderer(this, 0, 84);
		this.LeftLeg.mirror = true;
		this.LeftLeg.setRotationPoint(8.5F, 6.0F, 0.0F);
		this.LeftLeg.addBox(-5.5F, 0.0F, -5.5F, 11.0F, 18.0F, 11.0F, 0.0F, 0.0F, 0.0F);
		this.RightEar = new ModelRenderer(this, 48, 0);
		this.RightEar.setRotationPoint(-8.0F, -15.0F, -2.0F);
		this.RightEar.addBox(-5.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.LeftFist = new ModelRenderer(this, 80, 45);
		this.LeftFist.mirror = true;
		this.LeftFist.setRotationPoint(4.0F, 26.0F, 0.0F);
		this.LeftFist.addBox(-6.0F, 0.0F, -7.0F, 12.0F, 11.0F, 14.0F, 0.0F, 0.0F, 0.0F);
		this.RightFist = new ModelRenderer(this, 80, 45);
		this.RightFist.setRotationPoint(-4.0F, 26.0F, 0.0F);
		this.RightFist.addBox(-6.0F, 0.0F, -7.0F, 12.0F, 11.0F, 14.0F, 0.0F, 0.0F, 0.0F);
		this.tusk2 = new ModelRenderer(this, 0, 0);
		this.tusk2.setRotationPoint(-6.0F, 0.0F, -7.0F);
		this.tusk2.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Head.addChild(this.nose);
		this.Head.addChild(this.Jaw);
		this.Jaw.addChild(this.tusk1);
		this.Body.addChild(this.Chest);
		this.Chest.addChild(this.LeftArm);
		this.Chest.addChild(this.RightArm);
		this.Body.addChild(this.Cloth);
		this.Head.addChild(this.LetfEar);
		this.Chest.addChild(this.Head);
		this.Head.addChild(this.RightEar);
		this.LeftArm.addChild(this.LeftFist);
		this.RightArm.addChild(this.RightFist);
		this.Jaw.addChild(this.tusk2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
		ImmutableList.of(this.Body, this.RightLeg, this.LeftLeg).forEach((modelRenderer) -> { 
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(OgreEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entityIn.isRoaring()) {
			this.Jaw.rotateAngleX = 0.35F;
			this.Head.rotateAngleX = -0.4F;
			this.Body.rotateAngleX = 0.5F;
			this.LeftArm.rotateAngleZ = -0.5F;
			this.LeftArm.rotateAngleX = 0.3F;
			this.RightArm.rotateAngleZ = 0.5F;
			this.RightArm.rotateAngleX = 0.3F;
		} else {
			this.Jaw.rotateAngleX = 0F;
			this.Head.rotateAngleX = 0F;
			this.Body.rotateAngleX = 0F;
			this.LeftArm.rotateAngleZ = 0F;
			this.LeftArm.rotateAngleX = 0F;
			this.RightArm.rotateAngleZ = 0F;
			this.RightArm.rotateAngleX = 0F;
			this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
			this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
			this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		}
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
