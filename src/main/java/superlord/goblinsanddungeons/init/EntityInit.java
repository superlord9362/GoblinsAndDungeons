package superlord.goblinsanddungeons.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GarchEntity;
import superlord.goblinsanddungeons.entity.GobEntity;
import superlord.goblinsanddungeons.entity.GobloEntity;
import superlord.goblinsanddungeons.entity.GoomEntity;
import superlord.goblinsanddungeons.entity.HobGobEntity;
import superlord.goblinsanddungeons.entity.OgreEntity;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, GoblinsAndDungeons.MOD_ID);

	public static final RegistryObject<EntityType<GobEntity>> GOB = REGISTER.register("gob", () -> EntityType.Builder.<GobEntity>create(GobEntity::new, EntityClassification.MONSTER).size(0.375F, 1.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "gob").toString()));
	public static final RegistryObject<EntityType<HobGobEntity>> HOBGOB = REGISTER.register("hobgob", () -> EntityType.Builder.<HobGobEntity>create(HobGobEntity::new, EntityClassification.MONSTER).size(1.25F, 2.0F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "hobgob").toString()));
	public static final RegistryObject<EntityType<GobloEntity>> GOBLO = REGISTER.register("goblo", () -> EntityType.Builder.<GobloEntity>create(GobloEntity::new, EntityClassification.MONSTER).size(0.875F, 1F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goblo").toString()));
	public static final RegistryObject<EntityType<GarchEntity>> GARCH = REGISTER.register("garch", () -> EntityType.Builder.<GarchEntity>create(GarchEntity::new, EntityClassification.MONSTER).size(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "garch").toString()));
	public static final RegistryObject<EntityType<GoomEntity>> GOOM = REGISTER.register("goom", () -> EntityType.Builder.<GoomEntity>create(GoomEntity::new, EntityClassification.MONSTER).size(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "goom").toString()));
	public static final RegistryObject<EntityType<OgreEntity>> OGRE = REGISTER.register("ogre", () -> EntityType.Builder.<OgreEntity>create(OgreEntity::new, EntityClassification.MONSTER).size(0.5F, 1.25F).build(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "ogre").toString()));

}
