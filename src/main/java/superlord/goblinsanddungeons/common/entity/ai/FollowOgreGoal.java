package superlord.goblinsanddungeons.common.entity.ai;

import java.util.List;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import superlord.goblinsanddungeons.common.entity.OgreEntity;

public class FollowOgreGoal extends Goal {
   private final Animal animal;
   private OgreEntity ogre;
   private final double moveSpeed;
   private int delayCounter;

   public FollowOgreGoal(Animal animal, double speed) {
      this.animal = animal;
      this.moveSpeed = speed;
   }

   public boolean canUse() {
      if (this.animal.getAge() >= 0) {
         return false;
      } else {
         List<OgreEntity> list = this.animal.level.getEntitiesOfClass(OgreEntity.class, this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
         if (!list.isEmpty()) {
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean canContinueToUse() {
	   if (!this.ogre.isAlive()) {
         return false;
      } else {
         double d0 = this.animal.distanceToSqr(this.ogre);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   public void start() {
      this.delayCounter = 0;
   }

   public void stop() {
      this.ogre = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.animal.getNavigation().moveTo(this.ogre, this.moveSpeed);
      }
   }
}
