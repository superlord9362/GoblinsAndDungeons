package superlord.goblinsanddungeons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.HobgobModel;
import superlord.goblinsanddungeons.common.entity.HobGobEntity;

public class HobGobRenderer extends MobRenderer<HobGobEntity, HobgobModel<HobGobEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/hobgob.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HobGobRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new HobgobModel(renderManagerIn.bakeLayer(ClientEvents.HOBGOB)), 1.255F);
	}

	@Override
	public ResourceLocation getTextureLocation(HobGobEntity entity) {
		return TEXTURE;
	}

}
