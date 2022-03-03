package superlord.goblinsanddungeons.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import superlord.goblinsanddungeons.client.ClientEvents;
import superlord.goblinsanddungeons.client.model.armor.GoblinCrownModel;
import superlord.goblinsanddungeons.init.ItemInit;

public class CrownRenderProperties implements IItemRenderProperties {
	
	private static boolean init;

    public static GoblinCrownModel CROWN_MODEL;

    public static void initializeModels() {
    	init = true;
    	CROWN_MODEL = new GoblinCrownModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.GOBLIN_CROWN));
    }
    
    public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
    	if(!init){
            initializeModels();
        }
    	if(itemStack.getItem() == ItemInit.GOBLIN_CROWN.get()){
            return CROWN_MODEL;
        }
    	return _default;
    }
    
}
