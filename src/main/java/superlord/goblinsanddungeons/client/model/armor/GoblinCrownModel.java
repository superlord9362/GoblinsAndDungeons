package superlord.goblinsanddungeons.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("rawtypes")
@OnlyIn(Dist.CLIENT)
public class GoblinCrownModel extends HumanoidModel {
	
	public GoblinCrownModel(ModelPart part) {
        super(part);
    }
	
    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
        head.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(0, 64).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, deformation), PartPose.offset(0.0F, -8.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 80);
    }

}
