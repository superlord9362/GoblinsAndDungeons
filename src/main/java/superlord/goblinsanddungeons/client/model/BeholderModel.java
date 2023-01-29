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
import net.minecraft.util.Mth;
import superlord.goblinsanddungeons.common.entity.Beholder;

public class BeholderModel extends EntityModel<Beholder> {
	
	private final ModelPart main;
	private final ModelPart maineye;

	public BeholderModel(ModelPart root) {
		this.main = root.getChild("main");
		this.maineye = main.getChild("maineye");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -15.0F, -15.0F, 30.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition eyestalk1 = main.addOrReplaceChild("eyestalk1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -1.0F));

		PartDefinition eye1 = eyestalk1.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(32, 60).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition eyestalk2 = main.addOrReplaceChild("eyestalk2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, -13.0F, -1.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition eye2 = eyestalk2.addOrReplaceChild("eye2", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition eye2_r1 = eye2.addOrReplaceChild("eye2_r1", CubeListBuilder.create().texOffs(0, 60).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition eyestalk3 = main.addOrReplaceChild("eyestalk3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -13.0F, -1.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition eye3 = eyestalk3.addOrReplaceChild("eye3", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition eye3_r1 = eye3.addOrReplaceChild("eye3_r1", CubeListBuilder.create().texOffs(64, 60).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition eyestalk4 = main.addOrReplaceChild("eyestalk4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -1.0F, -1.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition eye4 = eyestalk4.addOrReplaceChild("eye4", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition eye4_r1 = eye4.addOrReplaceChild("eye4_r1", CubeListBuilder.create().texOffs(90, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition eyestalk5 = main.addOrReplaceChild("eyestalk5", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -1.0F, -1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition eye5 = eyestalk5.addOrReplaceChild("eye5", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition eye5_r1 = eye5.addOrReplaceChild("eye5_r1", CubeListBuilder.create().texOffs(96, 60).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition maineye = main.addOrReplaceChild("maineye", CubeListBuilder.create().texOffs(90, 23).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -15.0F));

		return LayerDefinition.create(meshdefinition, 128, 76);
	}

	@Override
	public void setupAnim(Beholder entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.maineye.x = headPitch * ((float)Math.PI / 180F);
		this.maineye.y = netHeadYaw * ((float)Math.PI / 180F);
		this.main.y = -0.5F * Mth.sin(0.5F * ageInTicks / 5);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}