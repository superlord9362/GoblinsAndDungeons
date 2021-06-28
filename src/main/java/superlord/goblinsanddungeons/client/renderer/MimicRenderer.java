package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.MimicModel;
import superlord.goblinsanddungeons.client.model.HiddenMimicModel;
import superlord.goblinsanddungeons.entity.MimicEntity;

public class MimicRenderer extends MobRenderer<MimicEntity, EntityModel<MimicEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/mimic.png");
	private static final MimicModel<?> MIMIC = new MimicModel<>();
	private static final HiddenMimicModel<?> HIDDEN_MIMIC = new HiddenMimicModel<>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MimicRenderer() {
		super(Minecraft.getInstance().getRenderManager(), MIMIC, 0.0F);
		this.addLayer(new HeldItemLayer(this));
	}
	
	public void render(MimicEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
		if (entity.isHiding()) {
			entityModel = HIDDEN_MIMIC;
		} else {
			entityModel = MIMIC;
		}
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getEntityTexture(MimicEntity entity) {
			return TEXTURE;
	}

}
