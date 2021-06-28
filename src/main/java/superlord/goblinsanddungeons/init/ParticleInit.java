package superlord.goblinsanddungeons.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.particle.GoblinSoulBulletParticle;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleInit {

	public static final List<ParticleType<?>> PARTICLE_TYPES = Lists.newArrayList();

	public static final BasicParticleType gobSoulBullet = registerBasicParticle("goblin_soul_bullet");


	private static BasicParticleType registerBasicParticle(String name) {
		return registerParticle(name, new BasicParticleType(false));
	}
	
	public static <T extends ParticleType<?>> T registerParticle(String name, T particle) {
        particle.setRegistryName(new ResourceLocation(GoblinsAndDungeons.MOD_ID, name));
        PARTICLE_TYPES.add(particle);
        return particle;
    }

	@SubscribeEvent
	public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event) {
		IForgeRegistry<ParticleType<?>> registry = event.getRegistry();
		for (ParticleType<?> particle : PARTICLE_TYPES) {
			registry.register(particle);
		}
	}


	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent e) {
		@SuppressWarnings("resource")
        ParticleManager particles = Minecraft.getInstance().particles;
        particles.registerFactory(gobSoulBullet, GoblinSoulBulletParticle.Factory::new);
	}
}
