package superlord.goblinsanddungeons.common.entity.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class GobKingTeleportEvent extends LivingEvent
{

    private double targetX;
    private double targetY;
    private double targetZ;
    private float attackDamage;

    public GobKingTeleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ, float attackDamage)
    {
        super(entity);
        this.setTargetX(targetX);
        this.setTargetY(targetY);
        this.setTargetZ(targetZ);
        this.setAttackDamage(attackDamage);
    }

    public double getTargetX() { return targetX; }
    public void setTargetX(double targetX) { this.targetX = targetX; }
    public double getTargetY() { return targetY; }
    public void setTargetY(double targetY) { this.targetY = targetY; }
    public double getTargetZ() { return targetZ; }
    public void setTargetZ(double targetZ) { this.targetZ = targetZ; }
    public float getAttackDamage() { return attackDamage; }
    public void setAttackDamage(float attackDamage) { this.attackDamage = attackDamage; }
}
