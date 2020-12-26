package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.HobgobModel;
import superlord.goblinsanddungeons.entity.HobGobEntity;

public class HobGobRenderer extends MobRenderer<HobGobEntity, HobgobModel<HobGobEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/hobgob.png");

	public HobGobRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new HobgobModel<>(), 1.255F);
	}

	@Override
	public ResourceLocation getEntityTexture(HobGobEntity entity) {
		return TEXTURE;
	}

}
