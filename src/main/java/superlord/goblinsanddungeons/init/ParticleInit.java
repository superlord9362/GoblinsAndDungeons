package superlord.goblinsanddungeons.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.particle.BlindnessOrbParticle;
import superlord.goblinsanddungeons.common.particle.GoblinSoulBulletParticle;
import superlord.goblinsanddungeons.common.particle.LevitationOrbParticle;
import superlord.goblinsanddungeons.common.particle.SoulBulletParticle;
import superlord.goblinsanddungeons.common.particle.TeleportationOrbParticle;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleInit {

	public static final SimpleParticleType GOB_SOUL_BULLET = registerBasicParticle("goblin_soul_bullet");
	public static final SimpleParticleType SOUL_BULLET = registerBasicParticle("soul_bullet");
	public static final SimpleParticleType BLINDNESS_ORB = registerBasicParticle("blindness_orb");
	public static final SimpleParticleType TELEPORTATION_ORB = registerBasicParticle("teleportation_orb");
	public static final SimpleParticleType LEVITATION_ORB = registerBasicParticle("levitation_orb");

	
	private static SimpleParticleType registerBasicParticle(String name) {
		return ParticleRegistry.registerParticle(name, new SimpleParticleType(false));
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent e) {
		ParticleEngine particles = Minecraft.getInstance().particleEngine;

		particles.register(GOB_SOUL_BULLET, GoblinSoulBulletParticle.Provider::new);
		particles.register(SOUL_BULLET, SoulBulletParticle.Provider::new);
		particles.register(BLINDNESS_ORB, BlindnessOrbParticle.Provider::new);
		particles.register(LEVITATION_ORB, LevitationOrbParticle.Provider::new);
		particles.register(TELEPORTATION_ORB, TeleportationOrbParticle.Provider::new);
	}
}
