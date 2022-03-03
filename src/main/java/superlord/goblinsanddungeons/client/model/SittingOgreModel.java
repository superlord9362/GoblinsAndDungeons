package superlord.goblinsanddungeons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.entity.OgreEntity;

/**
 * Ogre - Weastian
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SittingOgreModel<T extends Entity> extends EntityModel<OgreEntity> {
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart Body;
	private final ModelPart Chest;
	private final ModelPart Head;

	public SittingOgreModel(ModelPart root) {
		this.LeftLeg = root.getChild("LeftLeg");
		this.RightLeg = root.getChild("RightLeg");
		this.Body = root.getChild("Body");
		this.Chest = Body.getChild("Chest");
		this.Head = Chest.getChild("Head");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 84).addBox(-5.5F, 0.0F, -5.5F, 11.0F, 18.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 19.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 84).mirror().addBox(-5.5F, 0.0F, -5.5F, 11.0F, 18.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 19.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(72, 72).addBox(-17.0F, -20.0F, -14.0F, 34.0F, 28.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition Cloth = Body.addOrReplaceChild("Cloth", CubeListBuilder.create().texOffs(48, 7).addBox(-17.0F, 0.0F, -14.0F, 34.0F, 7.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(0, 50).addBox(-15.0F, -12.0F, -10.0F, 30.0F, 14.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, 0.0F));

		PartDefinition RightArm = Chest.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(162, 32).mirror().addBox(0.0F, -3.0F, -5.0F, 8.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.1367F));

		PartDefinition RightFist = RightArm.addOrReplaceChild("RightFist", CubeListBuilder.create().texOffs(80, 45).mirror().addBox(-6.0F, 0.0F, -7.0F, 12.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 26.0F, 0.0F));

		PartDefinition LeftArm = Chest.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(162, 32).addBox(-8.0F, -3.0F, -5.0F, 8.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.1367F));

		PartDefinition LeftFist = LeftArm.addOrReplaceChild("LeftFist", CubeListBuilder.create().texOffs(80, 45).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 26.0F, 0.0F));

		PartDefinition Head = Chest.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -19.0F, -11.0F, 16.0F, 19.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -2.0F));

		PartDefinition nose = Head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(52, 3).addBox(-3.0F, 0.0F, -5.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -11.0F));

		PartDefinition LetfEar = Head.addOrReplaceChild("LetfEar", CubeListBuilder.create().texOffs(48, 4).mirror().addBox(-5.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -15.0F, -2.0F));

		PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -15.0F, -2.0F));

		PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 35).addBox(-7.0F, 0.0F, -8.0F, 14.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -5.0F));

		PartDefinition tusk2 = Jaw.addOrReplaceChild("tusk2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -7.0F));

		PartDefinition tusk1 = Jaw.addOrReplaceChild("tusk1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	@Override
	public void setupAnim(OgreEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Head.xRot = headPitch * ((float)Math.PI / 180F);
		this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		LeftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		RightLeg.render(poseStack, buffer, packedLight, packedOverlay);
		Body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}
