package superlord.goblinsanddungeons.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.OgresMightEffect;

public class EffectInit {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GoblinsAndDungeons.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<MobEffect> MIGHT = EFFECTS.register("might", () -> new OgresMightEffect(MobEffectCategory.NEUTRAL, 0xB8B546).addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Potion> OGRES_MIGHT = POTIONS.register("ogres_might", () -> new Potion(new MobEffectInstance(MIGHT.get(), 600)));
	public static final RegistryObject<Potion> OGRES_MIGHT_STRONG = POTIONS.register("ogres_might_strong", () -> new Potion(new MobEffectInstance(MIGHT.get(), 600, 1)));
	public static final RegistryObject<Potion> OGRES_MIGHT_LONG = POTIONS.register("ogres_might_long", () -> new Potion(new MobEffectInstance(MIGHT.get(), 900)));

	public static void brewingRecipes() {
		PotionBrewing.addMix(Potions.AWKWARD, ItemInit.OGRE_TUSK.get(), OGRES_MIGHT.get());
		PotionBrewing.addMix(OGRES_MIGHT.get(), Items.GLOWSTONE_DUST, OGRES_MIGHT_STRONG.get());
		PotionBrewing.addMix(OGRES_MIGHT.get(), Items.REDSTONE, OGRES_MIGHT_LONG.get());
	}

}
