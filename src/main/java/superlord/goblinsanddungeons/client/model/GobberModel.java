package superlord.goblinsanddungeons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.common.entity.GobberEntity;

/**
 * GobberModel - superlord9362
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GobberModel extends EntityModel<GobberEntity> implements ArmedModel {
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart RightPauldron;
	private final ModelPart LeftPauldron;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;

	public GobberModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Head = Body.getChild("Head");
		this.RightPauldron = Body.getChild("RightPauldron");
		this.LeftPauldron = Body.getChild("LeftPauldron");
		this.RightArm = RightPauldron.getChild("RightArm");
		this.LeftArm = LeftPauldron.getChild("LeftArm");
		this.RightLeg = Body.getChild("RightLeg");
		this.LeftLeg = Body.getChild("LeftLeg");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 13).addBox(-5.0F, 0.0F, -4.0F, 10.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create().texOffs(22, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.0F, 0.0F));

		PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create().texOffs(22, 3).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, 0.0F));

		PartDefinition RightPauldron = Body.addOrReplaceChild("RightPauldron", CubeListBuilder.create().texOffs(36, 22).mirror().addBox(0.0F, -2.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition RightArm = RightPauldron.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(28, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, 0.0F));

		PartDefinition LeftPauldron = Body.addOrReplaceChild("LeftPauldron", CubeListBuilder.create().texOffs(36, 22).addBox(-5.0F, -2.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition LeftArm = LeftPauldron.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(28, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 0.0F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(36, 9).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 8.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(36, 9).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 8.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}


	@Override
	public void setupAnim(GobberEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isSleeping()) {
			this.RightPauldron.zRot = -0.5F * 0.45F * Mth.sin((0.6F * ageInTicks) / 10);
			this.LeftPauldron.zRot = -(-0.5F * 0.45F * Mth.sin((0.6F * ageInTicks) / 10));
			this.Head.xRot = 0.25F;
			this.LeftArm.xRot = 0.0F;
			this.RightArm.xRot = 0.0F;
			this.RightLeg.xRot = 0.0F;
			this.LeftLeg.xRot = 0.0F;
		} else {
			this.RightPauldron.zRot = 0.0F;
			this.LeftPauldron.zRot = 0.0F;
			this.Head.xRot = 0.0F;
			this.Head.xRot = headPitch * ((float)Math.PI / 180F);
			this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
			this.RightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	        this.LeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	        this.RightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	        this.LeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, buffer, packedLight, packedOverlay);
	}

	public void translateToHand(HumanoidArm sideIn, PoseStack matrixStackIn) {
		float f = sideIn == HumanoidArm.RIGHT ? -4F : 0F;
		float f1 = sideIn == HumanoidArm.RIGHT ? 10F : 0F;
		ModelPart modelrenderer = this.getArmForSide(sideIn);
		modelrenderer.x += f;
		modelrenderer.y += f1;
		modelrenderer.translateAndRotate(matrixStackIn);
		matrixStackIn.translate(0, 0, 0);
		modelrenderer.x -= f;
		modelrenderer.y -= f1;
	}

	protected ModelPart getArmForSide(HumanoidArm side) {
		return side == HumanoidArm.RIGHT ? this.LeftArm : this.RightArm;
	}

}
