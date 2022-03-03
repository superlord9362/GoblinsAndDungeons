package superlord.goblinsanddungeons.entity.tile;

public class SoulAshCampfireTileEntity {} /**extends TileEntity implements IClearable, ITickableTileEntity {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
	private final int[] cookingTimes = new int[4];
	private final int[] cookingTotalTimes = new int[4];

	public SoulAshCampfireTileEntity() {
		super(TileEntityInit.SOUL_ASH_CAMPFIRE.get());
	}

	public void tick() {
		boolean flag = this.getBlockState().get(SoulAshCampfireBlock.LIT);
		boolean flag1 = this.world.isRemote;
		if (flag1) {
			if (flag) {
				this.addParticles();
			}

		} else {
			if (flag) {
				this.cookAndDrop();
			} else {
				for(int i = 0; i < this.inventory.size(); ++i) {
					if (this.cookingTimes[i] > 0) {
						this.cookingTimes[i] = MathHelper.clamp(this.cookingTimes[i] - 2, 0, this.cookingTotalTimes[i]);
					}
				}
			}

		}
		if (flag) {
			BlockPos pos = this.pos;
			if (world.getBlockState(pos.up()).getBlock() == Blocks.SOUL_SAND) {
				Random random = new Random();
				int chance = random.nextInt(199);
				if (chance == 23) {
					world.setBlockState(pos.up(), BlockInit.ASHED_SOUL_SAND.get().getDefaultState());
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void cookAndDrop() {
		for(int i = 0; i < this.inventory.size(); ++i) {
			ItemStack itemstack = this.inventory.get(i);
			if (!itemstack.isEmpty()) {
				int j = this.cookingTimes[i]++;
				if (this.cookingTimes[i] >= this.cookingTotalTimes[i]) {
					IInventory iinventory = new Inventory(itemstack);
					ItemStack itemstack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, iinventory, this.world).map((campfireRecipe) -> {
						return campfireRecipe.getCraftingResult(iinventory);
					}).orElse(itemstack);
					BlockPos blockpos = this.getPos();
					InventoryHelper.spawnItemStack(this.world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack1);
					this.inventory.set(i, ItemStack.EMPTY);
					this.inventoryChanged();
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private void addParticles() {
		World world = this.getWorld();
		if (world != null) {
			BlockPos blockpos = this.getPos();
			Random random = world.rand;
			if (random.nextFloat() < 0.11F) {
				for(int i = 0; i < random.nextInt(2) + 2; ++i) {
					SoulAshCampfireBlock.spawnSmokeParticles(world, blockpos, this.getBlockState().get(SoulAshCampfireBlock.SIGNAL_FIRE), false);
				}
			}

			int l = this.getBlockState().get(SoulAshCampfireBlock.FACING).getHorizontalIndex();

			for(int j = 0; j < this.inventory.size(); ++j) {
				if (!this.inventory.get(j).isEmpty() && random.nextFloat() < 0.2F) {
					Direction direction = Direction.byHorizontalIndex(Math.floorMod(j + l, 4));
					float f = 0.3125F;
					double d0 = (double)blockpos.getX() + 0.5D - (double)((float)direction.getXOffset() * 0.3125F) + (double)((float)direction.rotateY().getXOffset() * 0.3125F);
					double d1 = (double)blockpos.getY() + 0.5D;
					double d2 = (double)blockpos.getZ() + 0.5D - (double)((float)direction.getZOffset() * 0.3125F) + (double)((float)direction.rotateY().getZOffset() * 0.3125F);

					for(int k = 0; k < 4; ++k) {
						world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
					}
				}
			}

		}
	}

	public NonNullList<ItemStack> getInventory() {
		return this.inventory;
	}

	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.inventory.clear();
		ItemStackHelper.loadAllItems(nbt, this.inventory);
		if (nbt.contains("CookingTimes", 11)) {
			int[] aint = nbt.getIntArray("CookingTimes");
			System.arraycopy(aint, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, aint.length));
		}

		if (nbt.contains("CookingTotalTimes", 11)) {
			int[] aint1 = nbt.getIntArray("CookingTotalTimes");
			System.arraycopy(aint1, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, aint1.length));
		}

	}

	public CompoundNBT write(CompoundNBT compound) {
		this.writeItems(compound);
		compound.putIntArray("CookingTimes", this.cookingTimes);
		compound.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
		return compound;
	}

	private CompoundNBT writeItems(CompoundNBT compound) {
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.inventory, true);
		return compound;
	}

	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
	}

	public CompoundNBT getUpdateTag() {
		return this.writeItems(new CompoundNBT());
	}

	public Optional<CampfireCookingRecipe> findMatchingRecipe(ItemStack itemStackIn) {
		return this.inventory.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(itemStackIn), this.world);
	}

	public boolean addItem(ItemStack itemStackIn, int cookTime) {
		for(int i = 0; i < this.inventory.size(); ++i) {
			ItemStack itemstack = this.inventory.get(i);
			if (itemstack.isEmpty()) {
				this.cookingTotalTimes[i] = cookTime;
				this.cookingTimes[i] = 0;
				this.inventory.set(i, itemStackIn.split(1));
				this.inventoryChanged();
				return true;
			}
		}

		return false;
	}

	private void inventoryChanged() {
		this.markDirty();
		this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public void clear() {
		this.inventory.clear();
	}

	public void dropAllItems() {
		if (this.world != null) {
			if (!this.world.isRemote) {
				InventoryHelper.dropItems(this.world, this.getPos(), this.getInventory());
			}

			this.inventoryChanged();
		}

	}
}*/
