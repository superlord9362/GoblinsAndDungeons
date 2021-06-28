package superlord.goblinsanddungeons.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GDFallingBlockEntity;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobKingEntity;
import superlord.goblinsanddungeons.entity.GobberEntity;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.entity.GoomEntity;
import superlord.goblinsanddungeons.entity.HobGobEntity;
import superlord.goblinsanddungeons.entity.MimicEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<EntityType<GobEntity>> GOB = REGISTER.register("gob", () -> EntityType.Builder.<GobEntity>create(GobEntity::new, EntityClassification.MONSTER).size(0.375F, 1.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gob").toString()));
	public static final RegistryObject<EntityType<HobGobEntity>> HOBGOB = REGISTER.register("hobgob", () -> EntityType.Builder.<HobGobEntity>create(HobGobEntity::new, EntityClassification.MONSTER).size(1.25F, 2.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "hobgob").toString()));
	public static final RegistryObject<EntityType<GobloEntity>> GOBLO = REGISTER.register("goblo", () -> EntityType.Builder.<GobloEntity>create(GobloEntity::new, EntityClassification.MONSTER).size(0.875F, 1F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblo").toString()));
	public static final RegistryObject<EntityType<GarchEntity>> GARCH = REGISTER.register("garch", () -> EntityType.Builder.<GarchEntity>create(GarchEntity::new, EntityClassification.MONSTER).size(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "garch").toString()));
	public static final RegistryObject<EntityType<GoomEntity>> GOOM = REGISTER.register("goom", () -> EntityType.Builder.<GoomEntity>create(GoomEntity::new, EntityClassification.MONSTER).size(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goom").toString()));
	public static final RegistryObject<EntityType<OgreEntity>> OGRE = REGISTER.register("ogre", () -> EntityType.Builder.<OgreEntity>create(OgreEntity::new, EntityClassification.MONSTER).size(2.125F, 4.5F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "ogre").toString()));
	public static final RegistryObject<EntityType<GobberEntity>> GOBBER = REGISTER.register("gobber", () -> EntityType.Builder.<GobberEntity>create(GobberEntity::new, EntityClassification.MONSTER).size(1.0625F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gobber").toString()));
	public static final RegistryObject<EntityType<GoblinSoulBulletEntity>> GOBLIN_SOUL_BULLET = REGISTER.register("goblin_soul_bullet", () -> EntityType.Builder.<GoblinSoulBulletEntity>create(GoblinSoulBulletEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_soul_bullet").toString()));
	public static final RegistryObject<EntityType<GobKingEntity>> GOB_KING = REGISTER.register("goblin_king", () -> EntityType.Builder.<GobKingEntity>create(GobKingEntity::new, EntityClassification.MONSTER).size(0.75F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_king").toString()));
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = REGISTER.register("mimic", () -> EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER).size(0.875F, 1.0625F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "mimic").toString()));
	public static final RegistryObject<EntityType<GDFallingBlockEntity>> FALLING_BLOCK = REGISTER.register("falling_block", () -> EntityType.Builder.<GDFallingBlockEntity>create(GDFallingBlockEntity::new, EntityClassification.MISC).size(1, 1).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "falling_block").toString()));
}
