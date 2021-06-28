package superlord.goblinsanddungeons.entity.ai;

import java.util.List;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class FollowOgreGoal extends Goal {
   private final AnimalEntity animal;
   private OgreEntity ogre;
   private final double moveSpeed;
   private int delayCounter;

   public FollowOgreGoal(AnimalEntity animal, double speed) {
      this.animal = animal;
      this.moveSpeed = speed;
   }

   public boolean shouldExecute() {
      if (this.animal.getGrowingAge() >= 0) {
         return false;
      } else {
         List<OgreEntity> list = this.animal.world.getEntitiesWithinAABB(OgreEntity.class, this.animal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
         if (!list.isEmpty()) {
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean shouldContinueExecuting() {
	   if (!this.ogre.isAlive()) {
         return false;
      } else {
         double d0 = this.animal.getDistanceSq(this.ogre);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   public void startExecuting() {
      this.delayCounter = 0;
   }

   public void resetTask() {
      this.ogre = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.animal.getNavigator().tryMoveToEntityLiving(this.ogre, this.moveSpeed);
      }
   }
}
