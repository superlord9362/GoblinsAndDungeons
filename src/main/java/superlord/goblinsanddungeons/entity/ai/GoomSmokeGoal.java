package superlord.goblinsanddungeons.entity.ai;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import superlord.goblinsanddungeons.entity.GoomEntity;

public class GoomSmokeGoal extends Goal {
   private final GoomEntity swellingGoom;
   private LivingEntity goomAttackTarget;

   public GoomSmokeGoal(GoomEntity entitygoomIn) {
      this.swellingGoom = entitygoomIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean shouldExecute() {
      LivingEntity livingentity = this.swellingGoom.getAttackTarget();
      return this.swellingGoom.getGoomState() > 0 || livingentity != null && this.swellingGoom.getDistanceSq(livingentity) < 9.0D;
   }

   public void startExecuting() {
      this.swellingGoom.getNavigator().clearPath();
      this.goomAttackTarget = this.swellingGoom.getAttackTarget();
   }

   public void resetTask() {
   }

   public void tick() {
      if (this.goomAttackTarget == null) {
         this.swellingGoom.setGoomState(-1);
      } else if (this.swellingGoom.getDistanceSq(this.goomAttackTarget) > 49.0D) {
         this.swellingGoom.setGoomState(-1);
      } else if (!this.swellingGoom.getEntitySenses().canSee(this.goomAttackTarget)) {
         this.swellingGoom.setGoomState(-1);
      } else {
         this.swellingGoom.setGoomState(1);
      }
   }
}
