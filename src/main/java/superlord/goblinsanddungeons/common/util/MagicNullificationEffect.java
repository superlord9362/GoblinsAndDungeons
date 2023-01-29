package superlord.goblinsanddungeons.common.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import superlord.goblinsanddungeons.magic.PlayerMana;

public class MagicNullificationEffect extends MobEffect {
    public MagicNullificationEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 50 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
    
    @Override
	public void applyEffectTick(LivingEntity entity, int p_19468_) {
		PlayerMana mana = new PlayerMana();
		mana.subMana(20);
	}
    
}