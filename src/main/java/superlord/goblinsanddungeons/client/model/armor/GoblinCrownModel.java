package superlord.goblinsanddungeons.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoblinCrownModel extends BipedModel<LivingEntity> {
	
	public static final GoblinCrownModel INSTANCE = new GoblinCrownModel();
	
    public ModelRenderer Crown;

    public GoblinCrownModel() {
    	super(0);
        this.textureWidth = 64;
        this.textureHeight = 80;
        this.Crown = new ModelRenderer(this, 0, 64);
        this.Crown.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Crown.addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        
        this.bipedHead.addChild(this.Crown);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
