package superlord.goblinsanddungeons.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.MOD)
public class SoundInit {
	
	public static final SoundEvent OGRE_IDLE;
	
	public static final SoundEvent GARCH_IDLE;
	public static final SoundEvent GARCH_HURT;
	public static final SoundEvent GARCH_DEATH;
	
	public static final SoundEvent GOOM_IDLE;
	public static final SoundEvent GOOM_HURT;
	public static final SoundEvent GOOM_DEATH;
	public static final SoundEvent GOOM_WARNING;
	
	public static final SoundEvent HOBGOB_IDLE;
	public static final SoundEvent HOBGOB_HURT;
	public static final SoundEvent HOBGOB_DEATH;
	
	public static final SoundEvent GOB_IDLE;
	public static final SoundEvent GOB_HURT;
	public static final SoundEvent GOB_DEATH;
	
	public static final SoundEvent MIMIC_IDLE;
	public static final SoundEvent MIMIC_HURT;
	public static final SoundEvent MIMIC_DEATH;
	
	@SubscribeEvent
	public static void registerSounds(final RegistryEvent.Register<SoundEvent> evt) {
		evt.getRegistry().register(OGRE_IDLE);
		evt.getRegistry().register(GARCH_IDLE);
		evt.getRegistry().register(GARCH_HURT);
		evt.getRegistry().register(GARCH_DEATH);
		evt.getRegistry().register(GOOM_IDLE);
		evt.getRegistry().register(GOOM_HURT);
		evt.getRegistry().register(GOOM_DEATH);
		evt.getRegistry().register(GOOM_WARNING);
		evt.getRegistry().register(HOBGOB_IDLE);
		evt.getRegistry().register(HOBGOB_HURT);
		evt.getRegistry().register(HOBGOB_DEATH);
		evt.getRegistry().register(GOB_IDLE);
		evt.getRegistry().register(GOB_HURT);
		evt.getRegistry().register(GOB_DEATH);
		evt.getRegistry().register(MIMIC_IDLE);
		evt.getRegistry().register(MIMIC_HURT);
		evt.getRegistry().register(MIMIC_DEATH);
	}
	
	private static SoundEvent createEvent(final String soundName) {
		final ResourceLocation soundId = new ResourceLocation(GoblinsAndDungeons.MOD_ID, soundName);
		return new SoundEvent(soundId).setRegistryName(soundId);
	}
	
	static {
		OGRE_IDLE = createEvent("ogre_idle");
		GARCH_IDLE = createEvent("garch_idle");
		GARCH_HURT = createEvent("garch_hurt");
		GARCH_DEATH = createEvent("garch_death");
		GOOM_IDLE = createEvent("goom_idle");
		GOOM_HURT = createEvent("goom_hurt");
		GOOM_DEATH = createEvent("goom_death");
		GOOM_WARNING = createEvent("goom_warning");
		HOBGOB_IDLE = createEvent("hobgob_idle");
		HOBGOB_HURT = createEvent("hobgob_hurt");
		HOBGOB_DEATH = createEvent("hobgob_death");
		GOB_IDLE = createEvent("gob_idle");
		GOB_HURT = createEvent("gob_hurt");
		GOB_DEATH = createEvent("gob_death");
		MIMIC_IDLE = createEvent("mimic_idle");
		MIMIC_HURT = createEvent("mimic_hurt");
		MIMIC_DEATH = createEvent("mimic_death");
	}

}
