package superlord.goblinsanddungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.init.ItemInit;

public class HobGobEntity extends GobEntity {

	public HobGobEntity(EntityType<? extends GobEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SheepEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, HorseEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DonkeyEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.22F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D);
	}
	
	@Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ItemInit.HOBGOB_SPAWN_EGG.get());
    }

}
