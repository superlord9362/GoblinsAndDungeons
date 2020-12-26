package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.OgreModel;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class OgreRenderer extends MobRenderer<OgreEntity, OgreModel<OgreEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/ogre.png");

	public OgreRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new OgreModel<>(), 0.375F);
	}

	@Override
	public ResourceLocation getEntityTexture(OgreEntity entity) {
		return TEXTURE;
	}

}
