package superlord.goblinsanddungeons.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import superlord.goblinsanddungeons.init.ItemInit;

public class AshedSoulSandBlock extends SoulSandBlock {

	public AshedSoulSandBlock(Properties p_56672_) {
		super(p_56672_);
	}

	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(handIn);
		Item item = stack.getItem();
		ItemStack soulAsh = new ItemStack(ItemInit.SOUL_ASH.get());
		if(item instanceof ShovelItem) {
			ItemEntity ash = new ItemEntity(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), soulAsh);
			worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
			worldIn.addFreshEntity(ash);
			if (player.isCreative()) {
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, player, (p_43122_) -> {
						p_43122_.broadcastBreakEvent(player.getUsedItemHand());
					});
				}
			}
			worldIn.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 1);
			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		} else {
			return InteractionResult.CONSUME;
		}
	}

}
