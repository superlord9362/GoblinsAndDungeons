package superlord.goblinsanddungeons.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.model.GobloModel;
import superlord.goblinsanddungeons.client.model.SleepingGobloModel;
import superlord.goblinsanddungeons.entity.GobloEntity;

public class GobloRenderer extends MobRenderer<GobloEntity, EntityModel<GobloEntity>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/entities/goblo.png");
	private static final GobloModel<?> GOBLO = new GobloModel<>();
	private static final SleepingGobloModel<?> SLEEPING_GOBLO = new SleepingGobloModel<>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GobloRenderer() {
		super(Minecraft.getInstance().getRenderManager(), GOBLO, 0.375F);
		this.addLayer(new HeldItemLayer(this));
	}
	
	public void render(GobloEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
		if (entity.isSleeping()) {
			entityModel = SLEEPING_GOBLO;
		} else {
			entityModel = GOBLO;
		}
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getEntityTexture(GobloEntity entity) {
			return TEXTURE;
	}

}
