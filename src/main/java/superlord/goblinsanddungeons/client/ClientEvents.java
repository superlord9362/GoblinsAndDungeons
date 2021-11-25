package superlord.goblinsanddungeons.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.renderer.FallingBlockRenderer;
import superlord.goblinsanddungeons.client.renderer.GarchRenderer;
import superlord.goblinsanddungeons.client.renderer.GobKingRenderer;
import superlord.goblinsanddungeons.client.renderer.GobRenderer;
import superlord.goblinsanddungeons.client.renderer.GobberRenderer;
import superlord.goblinsanddungeons.client.renderer.GoblinSoulBulletRenderer;
import superlord.goblinsanddungeons.client.renderer.GobloRenderer;
import superlord.goblinsanddungeons.client.renderer.GoomRenderer;
import superlord.goblinsanddungeons.client.renderer.HobGobRenderer;
import superlord.goblinsanddungeons.client.renderer.MimicRenderer;
import superlord.goblinsanddungeons.client.renderer.OgreRenderer;
import superlord.goblinsanddungeons.client.renderer.SoulAshCampfireTileEntityRenderer;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.TileEntityInit;
import superlord.goblinsanddungeons.item.GoblinsAndDungeonsSpawnEggItem;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOB.get(), GobRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.HOBGOB.get(), HobGobRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOBLO.get(), manager -> new GobloRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GARCH.get(), GarchRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOOM.get(), GoomRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOBBER.get(), GobberRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.OGRE.get(), manager -> new OgreRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOBLIN_SOUL_BULLET.get(), GoblinSoulBulletRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOB_KING.get(), GobKingRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.MIMIC.get(), manager -> new MimicRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.FALLING_BLOCK.get(), FallingBlockRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.SOUL_ASH_CAMPFIRE.get(), SoulAshCampfireTileEntityRenderer::new);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void itemColors(ColorHandlerEvent.Item event) {
		ItemColors handler = event.getItemColors();
		IItemColor eggColor = (stack, tintIndex) -> ((GoblinsAndDungeonsSpawnEggItem) stack.getItem()).getColor(tintIndex);
		for (GoblinsAndDungeonsSpawnEggItem e : GoblinsAndDungeonsSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			RenderType cutoutRenderType = RenderType.getCutout();

			RenderTypeLookup.setRenderLayer(BlockInit.SOUL_ASH_CAMPFIRE.get(), cutoutRenderType);
			RenderTypeLookup.setRenderLayer(BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get(), cutoutRenderType);
		}
	}

}
