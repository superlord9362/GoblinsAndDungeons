package superlord.goblinsanddungeons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.entity.GDFallingBlockEntity;
import superlord.goblinsanddungeons.common.entity.GarchEntity;
import superlord.goblinsanddungeons.common.entity.GobEntity;
import superlord.goblinsanddungeons.common.entity.GobKingEntity;
import superlord.goblinsanddungeons.common.entity.GobberEntity;
import superlord.goblinsanddungeons.common.entity.GoblinSoulBulletEntity;
import superlord.goblinsanddungeons.common.entity.GobloEntity;
import superlord.goblinsanddungeons.common.entity.GoomEntity;
import superlord.goblinsanddungeons.common.entity.HobGobEntity;
import superlord.goblinsanddungeons.common.entity.MimicEntity;
import superlord.goblinsanddungeons.common.entity.OgreEntity;
import superlord.goblinsanddungeons.common.entity.SoulBulletEntity;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<EntityType<GobEntity>> GOB = REGISTER.register("gob", () -> EntityType.Builder.<GobEntity>of(GobEntity::new, MobCategory.MONSTER).sized(0.375F, 1.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gob").toString()));
	public static final RegistryObject<EntityType<HobGobEntity>> HOBGOB = REGISTER.register("hobgob", () -> EntityType.Builder.<HobGobEntity>of(HobGobEntity::new, MobCategory.MONSTER).sized(1.25F, 2.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "hobgob").toString()));
	public static final RegistryObject<EntityType<GobloEntity>> GOBLO = REGISTER.register("goblo", () -> EntityType.Builder.<GobloEntity>of(GobloEntity::new, MobCategory.MONSTER).sized(0.875F, 1F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblo").toString()));
	public static final RegistryObject<EntityType<GarchEntity>> GARCH = REGISTER.register("garch", () -> EntityType.Builder.<GarchEntity>of(GarchEntity::new, MobCategory.MONSTER).sized(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "garch").toString()));
	public static final RegistryObject<EntityType<GoomEntity>> GOOM = REGISTER.register("goom", () -> EntityType.Builder.<GoomEntity>of(GoomEntity::new, MobCategory.MONSTER).sized(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goom").toString()));
	public static final RegistryObject<EntityType<OgreEntity>> OGRE = REGISTER.register("ogre", () -> EntityType.Builder.<OgreEntity>of(OgreEntity::new, MobCategory.MONSTER).sized(2.125F, 4.5F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "ogre").toString()));
	public static final RegistryObject<EntityType<GobberEntity>> GOBBER = REGISTER.register("gobber", () -> EntityType.Builder.<GobberEntity>of(GobberEntity::new, MobCategory.MONSTER).sized(1.0625F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gobber").toString()));
	public static final RegistryObject<EntityType<GoblinSoulBulletEntity>> GOBLIN_SOUL_BULLET = REGISTER.register("goblin_soul_bullet", () -> EntityType.Builder.<GoblinSoulBulletEntity>of(GoblinSoulBulletEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_soul_bullet").toString()));
	public static final RegistryObject<EntityType<SoulBulletEntity>> SOUL_BULLET = REGISTER.register("soul_bullet", () -> EntityType.Builder.<SoulBulletEntity>of(SoulBulletEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "soul_bullet").toString()));
	public static final RegistryObject<EntityType<GobKingEntity>> GOB_KING = REGISTER.register("goblin_king", () -> EntityType.Builder.<GobKingEntity>of(GobKingEntity::new, MobCategory.MONSTER).sized(0.75F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_king").toString()));
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = REGISTER.register("mimic", () -> EntityType.Builder.<MimicEntity>of(MimicEntity::new, MobCategory.MONSTER).sized(0.875F, 1.0625F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "mimic").toString()));
	public static final RegistryObject<EntityType<GDFallingBlockEntity>> FALLING_BLOCK = REGISTER.register("falling_block", () -> EntityType.Builder.<GDFallingBlockEntity>of(GDFallingBlockEntity::new, MobCategory.MISC).sized(1, 1).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "falling_block").toString()));
}
