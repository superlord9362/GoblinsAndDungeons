package superlord.goblinsanddungeons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.entity.Beholder;
import superlord.goblinsanddungeons.common.entity.BlindnessOrb;
import superlord.goblinsanddungeons.common.entity.ExplosiveOrb;
import superlord.goblinsanddungeons.common.entity.GDFallingBlock;
import superlord.goblinsanddungeons.common.entity.Garch;
import superlord.goblinsanddungeons.common.entity.Gob;
import superlord.goblinsanddungeons.common.entity.GobKing;
import superlord.goblinsanddungeons.common.entity.Gobber;
import superlord.goblinsanddungeons.common.entity.GoblinSoulBullet;
import superlord.goblinsanddungeons.common.entity.Goblo;
import superlord.goblinsanddungeons.common.entity.Goom;
import superlord.goblinsanddungeons.common.entity.HobGob;
import superlord.goblinsanddungeons.common.entity.LevitationOrb;
import superlord.goblinsanddungeons.common.entity.Mimic;
import superlord.goblinsanddungeons.common.entity.Ogre;
import superlord.goblinsanddungeons.common.entity.SoulBullet;
import superlord.goblinsanddungeons.common.entity.TeleportationOrb;
import superlord.goblinsanddungeons.common.entity.WeaknessOrb;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<EntityType<Gob>> GOB = REGISTER.register("gob", () -> EntityType.Builder.<Gob>of(Gob::new, MobCategory.MONSTER).sized(0.375F, 1.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gob").toString()));
	public static final RegistryObject<EntityType<HobGob>> HOBGOB = REGISTER.register("hobgob", () -> EntityType.Builder.<HobGob>of(HobGob::new, MobCategory.MONSTER).sized(1.25F, 2.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "hobgob").toString()));
	public static final RegistryObject<EntityType<Goblo>> GOBLO = REGISTER.register("goblo", () -> EntityType.Builder.<Goblo>of(Goblo::new, MobCategory.MONSTER).sized(0.875F, 1F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblo").toString()));
	public static final RegistryObject<EntityType<Garch>> GARCH = REGISTER.register("garch", () -> EntityType.Builder.<Garch>of(Garch::new, MobCategory.MONSTER).sized(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "garch").toString()));
	public static final RegistryObject<EntityType<Goom>> GOOM = REGISTER.register("goom", () -> EntityType.Builder.<Goom>of(Goom::new, MobCategory.MONSTER).sized(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goom").toString()));
	public static final RegistryObject<EntityType<Ogre>> OGRE = REGISTER.register("ogre", () -> EntityType.Builder.<Ogre>of(Ogre::new, MobCategory.MONSTER).sized(2.125F, 4.5F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "ogre").toString()));
	public static final RegistryObject<EntityType<Gobber>> GOBBER = REGISTER.register("gobber", () -> EntityType.Builder.<Gobber>of(Gobber::new, MobCategory.MONSTER).sized(1.0625F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gobber").toString()));
	public static final RegistryObject<EntityType<GoblinSoulBullet>> GOBLIN_SOUL_BULLET = REGISTER.register("goblin_soul_bullet", () -> EntityType.Builder.<GoblinSoulBullet>of(GoblinSoulBullet::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_soul_bullet").toString()));
	public static final RegistryObject<EntityType<SoulBullet>> SOUL_BULLET = REGISTER.register("soul_bullet", () -> EntityType.Builder.<SoulBullet>of(SoulBullet::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "soul_bullet").toString()));
	public static final RegistryObject<EntityType<GobKing>> GOB_KING = REGISTER.register("goblin_king", () -> EntityType.Builder.<GobKing>of(GobKing::new, MobCategory.MONSTER).sized(0.75F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblin_king").toString()));
	public static final RegistryObject<EntityType<Mimic>> MIMIC = REGISTER.register("mimic", () -> EntityType.Builder.<Mimic>of(Mimic::new, MobCategory.MONSTER).sized(0.875F, 1.0625F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "mimic").toString()));
	public static final RegistryObject<EntityType<GDFallingBlock>> FALLING_BLOCK = REGISTER.register("falling_block", () -> EntityType.Builder.<GDFallingBlock>of(GDFallingBlock::new, MobCategory.MISC).sized(1, 1).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "falling_block").toString()));
	public static final RegistryObject<EntityType<Beholder>> BEHOLDER = REGISTER.register("beholder", () -> EntityType.Builder.<Beholder>of(Beholder::new, MobCategory.MONSTER).sized(1.8F, 1.8F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "beholder").toString()));

	public static final RegistryObject<EntityType<BlindnessOrb>> BLINDNESS_ORB = REGISTER.register("blindness_orb", () -> EntityType.Builder.<BlindnessOrb>of(BlindnessOrb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "blindness_orb").toString()));
	public static final RegistryObject<EntityType<TeleportationOrb>> TELEPORTATION_ORB = REGISTER.register("teleportation_orb", () -> EntityType.Builder.<TeleportationOrb>of(TeleportationOrb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "teleportation_orb").toString()));
	public static final RegistryObject<EntityType<LevitationOrb>> LEVITATION_ORB = REGISTER.register("levitation_orb", () -> EntityType.Builder.<LevitationOrb>of(LevitationOrb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "levitation_orb").toString()));
	public static final RegistryObject<EntityType<WeaknessOrb>> WEAKNESS_ORB = REGISTER.register("weakness_orb", () -> EntityType.Builder.<WeaknessOrb>of(WeaknessOrb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "weakness_orb").toString()));
	public static final RegistryObject<EntityType<ExplosiveOrb>> EXPLOSIVE_ORB = REGISTER.register("explosive_orb", () -> EntityType.Builder.<ExplosiveOrb>of(ExplosiveOrb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "explosive_orb").toString()));

	
}
