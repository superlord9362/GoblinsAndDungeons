package superlord.goblinsanddungeons.common.entity.ai;

import java.util.EnumSet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import superlord.goblinsanddungeons.common.entity.Goom;

public class GoomSmokeGoal extends Goal {
   private final Goom swellingGoom;
   private LivingEntity goomAttackTarget;

   public GoomSmokeGoal(Goom entitygoomIn) {
      this.swellingGoom = entitygoomIn;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean canUse() {
      LivingEntity livingentity = this.swellingGoom.getTarget();
      return this.swellingGoom.getGoomState() > 0 || livingentity != null && this.swellingGoom.distanceToSqr(livingentity) < 9.0D;
   }

   public void start() {
      this.swellingGoom.getNavigation().isDone();
      this.goomAttackTarget = this.swellingGoom.getTarget();
   }

   public void stop() {
   }

   public void tick() {
      if (this.goomAttackTarget == null) {
         this.swellingGoom.setGoomState(-1);
      } else if (this.swellingGoom.distanceToSqr(this.goomAttackTarget) > 49.0D) {
         this.swellingGoom.setGoomState(-1);
      } else if (!this.swellingGoom.getSensing().hasLineOfSight(this.goomAttackTarget)) {
         this.swellingGoom.setGoomState(-1);
      } else {
         this.swellingGoom.setGoomState(1);
      }
   }
}
