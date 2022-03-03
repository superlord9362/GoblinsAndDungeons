package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.OgreModel;
import superlord.goblinsanddungeons.client.model.SittingOgreModel;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class OgreRenderer extends MobRenderer<OgreEntity, EntityModel<OgreEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/ogre.png");
	private static final ResourceLocation SHREK = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/ogre_shrek.png");
	private static OgreModel<?> OGRE;
	private static SittingOgreModel<?> FALLING_OGRE;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OgreRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OgreModel(renderManager.bakeLayer(ClientEvents.OGRE)), 2.125F);
		OGRE = new OgreModel(renderManager.bakeLayer(ClientEvents.OGRE));
		FALLING_OGRE = new SittingOgreModel(renderManager.bakeLayer(ClientEvents.SITTING_OGRE));
	}

	protected void scale(OgreEntity entity, PoseStack matrixStackIn, float partialTickTime) {
		if (entity.isFallingOnButt() && !entity.isOnGround()) {
			model = FALLING_OGRE;
		} else {
			model = OGRE;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(OgreEntity entity) {
		String s = ChatFormatting.stripFormatting(entity.getName().getString());
		if (s != null && "Shrek".equals(s)) {
			return SHREK;
		} else {
			return TEXTURE;
		}
	}

}
