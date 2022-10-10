package superlord.goblinsanddungeons.common.world;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import superlord.goblinsanddungeons.init.BlockInit;

public class GoblinsAndDungeonsFeatures {

	public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SCORIA = FeatureUtils.register("ore_scoria", Feature.ORE, new OreConfiguration(NATURAL_STONE, BlockInit.SCORIA.get().defaultBlockState(), 64));

	public static final Holder<PlacedFeature> ORE_SCORIA_UPPER = PlacementUtils.register("ore_scoria_upper", ORE_SCORIA, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-29), VerticalAnchor.absolute(0))));
	public static final Holder<PlacedFeature> ORE_SCORIA_LOWER = PlacementUtils.register("ore_scoria_lower", ORE_SCORIA, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(-30))));

	private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
		return orePlacement(CountPlacement.of(p_195344_), p_195345_);
	}

	private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
		return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
	}

}
