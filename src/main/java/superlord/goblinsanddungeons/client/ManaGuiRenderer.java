package superlord.goblinsanddungeons.client;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.ManaStats;

@EventBusSubscriber(value = Dist.CLIENT)
public class ManaGuiRenderer {

	public static final ResourceLocation GUI_ICONS = new ResourceLocation(GoblinsAndDungeons.MOD_ID, "textures/gui/icons.png");

	static Minecraft mc = Minecraft.getInstance();

	static IngameGui gui() {
		return mc.ingameGUI;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	@SubscribeEvent
	public static void renderGUI(RenderGameOverlayEvent event) {
		Random rand = new Random();
		PlayerEntity player = gui().getRenderViewPlayer();
		LivingEntity living;
		if (player != null) {
			Entity entity = player.getRidingEntity();
			if (entity == null) {
				living = null;
			}
			if (entity instanceof LivingEntity) {
				living = (LivingEntity)entity;
			}
		}
		living = null;
		if (player != null) {
			int i1 = gui().scaledWidth / 2 - 91;
			int j1 = gui().scaledWidth / 2 + 91;
			int k1 = gui().scaledHeight - 39;
			boolean needsAir = false;
			int l6 = player.getAir();
			int j7 = player.getMaxAir();
			if (player.areEyesInFluid(FluidTags.WATER) || l6 < j7) {
				int j8 = MathHelper.ceil((double)(l6 - 2) * 10.0D / (double)j7);
				int l8 = MathHelper.ceil((double)l6 * 10.0D / (double)j7) - j8;

				for(int k5 = 0; k5 < j8 + l8; ++k5) {
					if (k5 < j8) {
						needsAir = true;
					} else {
						needsAir = true;
					}
				}
			}
			if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
				LivingEntity livingentity = living;
				int moveUp = needsAir ? -10 : 0;
				int l = (int) ManaStats.getManaStats(player).getManaLevel();
				int j6 = getRenderMountHealth(livingentity);
				if (j6 == 0) {
					mc.getProfiler().startSection("thirst");
					mc.getTextureManager().bindTexture(GUI_ICONS);
					for(int k6 = 0; k6 < 10; ++k6) {
						int i7 = k1;
						int k7 = 16;
						int i8 = 0;
						
						if (ManaStats.getManaStats(player).getManaFilledLevel() <= 0.0F && gui().getTicks() % (l * 3 + 1) == 0) {
							i7 = k1 + (rand.nextInt(3) - 1);
						}
						
						int k8 = j1 - k6 * 8 - 9;
						gui().blit(event.getMatrixStack(), k8, i7 - 10 + moveUp, 16 + i8 * 9, 54, 9, 9);
						if (k6 * 2 + 1 < l) {
							gui().blit(event.getMatrixStack(), k8, i7 - 10 + moveUp, k7 + 36, 54, 9, 9);
						}
						
						if (k6 * 2 + 1 == l) {
							gui().blit(event.getMatrixStack(), k8, i7 - 10 + moveUp, k7 + 45, 54, 9, 9);
						}
					}
					mc.getProfiler().endSection();
					mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
				}
			}
		}
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		RenderSystem.disableAlphaTest();
	}


	private static int getRenderMountHealth(LivingEntity p_212306_1_) {
		if (p_212306_1_ != null && p_212306_1_.isLiving()) {
			float f = p_212306_1_.getMaxHealth();
			int i = (int)(f + 0.5F) / 2;
			if (i > 30) {
				i = 30;
			}

			return i;
		} else {
			return 0;
		}
	}

}
