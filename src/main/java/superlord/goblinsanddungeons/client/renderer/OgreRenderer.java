package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.OgreModel;
import superlord.goblinsanddungeons.client.model.SittingOgreModel;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class OgreRenderer extends MobRenderer<OgreEntity, EntityModel<OgreEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/ogre.png");
	private static final OgreModel<?> OGRE = new OgreModel<>();
	private static final SittingOgreModel<?> FALLING_OGRE = new SittingOgreModel<>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OgreRenderer() {
		super(Minecraft.getInstance().getRenderManager(), OGRE, 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}
	
	public void render(OgreEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
		if (entity.isFallingOnButt() && entity.isAirBorne) {
			entityModel = FALLING_OGRE;
		} else {
			entityModel = OGRE;
		}
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getEntityTexture(OgreEntity entity) {
			return TEXTURE;
	}

}
