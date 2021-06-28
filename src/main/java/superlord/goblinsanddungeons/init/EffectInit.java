package superlord.goblinsanddungeons.init;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class EffectInit {

	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, GoblinsAndDungeons.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<Effect> MIGHT = EFFECTS.register("might", () -> new Effect(EffectType.NEUTRAL, 0xB8B546).addAttributesModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributesModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, AttributeModifier.Operation.ADDITION));
	
	public static final RegistryObject<Potion> OGRES_MIGHT = POTIONS.register("ogres_might", () -> new Potion(new EffectInstance(MIGHT.get(), 600)));
	public static final RegistryObject<Potion> OGRES_MIGHT_STRONG = POTIONS.register("ogres_might_strong", () -> new Potion(new EffectInstance(MIGHT.get(), 600, 1)));
	public static final RegistryObject<Potion> OGRES_MIGHT_LONG = POTIONS.register("ogres_might_long", () -> new Potion(new EffectInstance(MIGHT.get(), 900)));
	
	public static void brewingRecipes() {
		BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(ItemInit.OGRE_TUSK.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), OGRES_MIGHT.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), OGRES_MIGHT.get())), Ingredient.fromItems(Items.GLOWSTONE_DUST), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), OGRES_MIGHT_STRONG.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), OGRES_MIGHT.get())), Ingredient.fromItems(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), OGRES_MIGHT_LONG.get()));
	}
	
}
