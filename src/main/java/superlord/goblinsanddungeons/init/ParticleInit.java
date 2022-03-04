package superlord.goblinsanddungeons.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.particle.GoblinSoulBulletParticle;
import superlord.goblinsanddungeons.particle.SoulBulletParticle;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleInit {

	public static final SimpleParticleType GOB_SOUL_BULLET = registerBasicParticle("goblin_soul_bullet");
	public static final SimpleParticleType SOUL_BULLET = registerBasicParticle("soul_bullet");

	
	private static SimpleParticleType registerBasicParticle(String name) {
		return ParticleRegistry.registerParticle(name, new SimpleParticleType(false));
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent e) {
		ParticleEngine particles = Minecraft.getInstance().particleEngine;

		particles.register(GOB_SOUL_BULLET, GoblinSoulBulletParticle.Provider::new);
		particles.register(SOUL_BULLET, SoulBulletParticle.Provider::new);
	}
}
