package superlord.goblinsanddungeons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.MOD)
public class SoundInit {
	
	public static final SoundEvent OGRE_IDLE;
	public static final SoundEvent OGRE_HURT;
	public static final SoundEvent OGRE_DEATH;
	public static final SoundEvent OGRE_ROAR;
	
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
	
	public static final SoundEvent GOBBER_IDLE;
	public static final SoundEvent GOBBER_HURT;
	public static final SoundEvent GOBBER_DEATH;
	public static final SoundEvent GOBBER_SNORING;
	
	public static final SoundEvent GOBLO_IDLE;
	public static final SoundEvent GOBLO_HURT;
	public static final SoundEvent GOBLO_DEATH;
	public static final SoundEvent GOBLO_SNORING;
	public static final SoundEvent GOBLO_EATING;
	
	public static final SoundEvent GOBLIN_KING_IDLE;
	public static final SoundEvent GOBLIN_KING_LAUGH;
	public static final SoundEvent GOBLIN_KING_HURT;
	public static final SoundEvent GOBLIN_KING_DEATH;
	
	public static final SoundEvent SPELL_CASTING;
	public static final SoundEvent SOUL_BULLET_LAUNCH;
	public static final SoundEvent SOUL_BULLET_COLLISION;

	public static final SoundEvent URN_PLACE;
	public static final SoundEvent URN_FALL;
	public static final SoundEvent URN_HIT;
	public static final SoundEvent URN_BREAK;
	public static final SoundEvent URN_STEP;
	
	@SubscribeEvent
	public static void registerSounds(final RegistryEvent.Register<SoundEvent> evt) {
		evt.getRegistry().register(OGRE_IDLE);
		evt.getRegistry().register(OGRE_HURT);
		evt.getRegistry().register(OGRE_DEATH);
		evt.getRegistry().register(OGRE_ROAR);
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
		evt.getRegistry().register(GOBBER_IDLE);
		evt.getRegistry().register(GOBBER_HURT);
		evt.getRegistry().register(GOBBER_DEATH);
		evt.getRegistry().register(GOBBER_SNORING);
		evt.getRegistry().register(GOBLO_IDLE);
		evt.getRegistry().register(GOBLO_HURT);
		evt.getRegistry().register(GOBLO_DEATH);
		evt.getRegistry().register(GOBLO_SNORING);
		evt.getRegistry().register(GOBLO_EATING);
		evt.getRegistry().register(GOBLIN_KING_IDLE);
		evt.getRegistry().register(GOBLIN_KING_LAUGH);
		evt.getRegistry().register(GOBLIN_KING_HURT);
		evt.getRegistry().register(GOBLIN_KING_DEATH);
		evt.getRegistry().register(SPELL_CASTING);
		evt.getRegistry().register(SOUL_BULLET_LAUNCH);
		evt.getRegistry().register(SOUL_BULLET_COLLISION);
		evt.getRegistry().register(URN_PLACE);
		evt.getRegistry().register(URN_BREAK);
		evt.getRegistry().register(URN_FALL);
		evt.getRegistry().register(URN_HIT);
		evt.getRegistry().register(URN_STEP);
	}
	
	private static SoundEvent createEvent(final String soundName) {
		final ResourceLocation soundId = new ResourceLocation(GoblinsAndDungeons.MOD_ID, soundName);
		return new SoundEvent(soundId).setRegistryName(soundId);
	}
	
	static {
		OGRE_IDLE = createEvent("ogre_idle");
		OGRE_HURT = createEvent("ogre_hurt");
		OGRE_DEATH = createEvent("ogre_death");
		OGRE_ROAR = createEvent("ogre_roar");
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
		GOBBER_IDLE = createEvent("gobber_idle");
		GOBBER_HURT = createEvent("gobber_hurt");
		GOBBER_DEATH = createEvent("gobber_death");
		GOBBER_SNORING = createEvent("gobber_snoring");
		GOBLO_IDLE = createEvent("goblo_idle");
		GOBLO_HURT = createEvent("goblo_hurt");
		GOBLO_DEATH = createEvent("goblo_death");
		GOBLO_SNORING = createEvent("goblo_snoring");
		GOBLO_EATING = createEvent("goblo_eating");
		GOBLIN_KING_IDLE = createEvent("goblin_king_idle");
		GOBLIN_KING_HURT = createEvent("goblin_king_hurt");
		GOBLIN_KING_DEATH = createEvent("goblin_king_death");
		GOBLIN_KING_LAUGH = createEvent("goblin_king_laugh");
		SPELL_CASTING = createEvent("spell_casting");
		SOUL_BULLET_LAUNCH = createEvent("soul_bullet_launch");
		SOUL_BULLET_COLLISION = createEvent("soul_bullet_collision");
		URN_PLACE = createEvent("urn_place");
		URN_BREAK = createEvent("urn_break");
		URN_FALL = createEvent("urn_fall");
		URN_HIT = createEvent("urn_hit");
		URN_STEP = createEvent("urn_step");
	}

}
