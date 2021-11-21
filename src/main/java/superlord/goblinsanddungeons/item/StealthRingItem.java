package superlord.goblinsanddungeons.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.GDArmorMaterial;
import superlord.goblinsanddungeons.init.ItemInit;

public class StealthRingItem extends ArmorItem {

	public static final IArmorMaterial MATERIAL = new GDArmorMaterial(GoblinsAndDungeons.MOD_ID + ":stealth_ring", 7, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, null);

	public StealthRingItem(Properties builderIn) {
		super(MATERIAL, EquipmentSlotType.OFFHAND, builderIn);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344)) {
			if (stack.getItem() == ItemInit.RING_OF_STEALTH.get()) {
				tooltip.add(new TranslationTextComponent("stealth_ring").mergeStyle(TextFormatting.GRAY));
			}
			if (stack.getItem() == ItemInit.RING_OF_EXPERIENCE.get()) {
				tooltip.add(new TranslationTextComponent("experience_ring").mergeStyle(TextFormatting.GRAY));
			}
			if (stack.getItem() == ItemInit.RING_OF_GLORY.get()) {
				tooltip.add(new TranslationTextComponent("glory_ring").mergeStyle(TextFormatting.GRAY));
			}
		} else {
			tooltip.add(new TranslationTextComponent("shift").mergeStyle(TextFormatting.GRAY));
		}
	}

}
