package party.lemons.biomemakeover.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import party.lemons.biomemakeover.init.BMEntities;

public class BMChestBoatEntity extends BMBoatEntity implements HasCustomInventoryScreen, ContainerEntity
{
	private static final int CONTAINER_SIZE = 27;
	private NonNullList<ItemStack> itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	@Nullable
	private ResourceLocation lootTable;
	private long lootTableSeed;

	public BMChestBoatEntity(EntityType<? extends Boat> entityType, Level level) {
		super(entityType, level);
	}

	public BMChestBoatEntity(Level level, double d, double e, double f) {
		this(BMEntities.BM_CHEST_BOAT.get(), level);
		this.setPos(d, e, f);
		this.xo = d;
		this.yo = e;
		this.zo = f;
	}

	@Override
	protected float getSinglePassengerXOffset() {
		return 0.15f;
	}

	@Override
	protected int getMaxPassengers() {
		return 1;
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		this.addChestVehicleSaveData(compoundTag);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.readChestVehicleSaveData(compoundTag);
	}

	@Override
	public void destroy(DamageSource damageSource) {
		super.destroy(damageSource);
		this.chestVehicleDestroyed(damageSource, this.level, this);
	}

	@Override
	public void remove(Entity.RemovalReason removalReason) {
		if (!this.level.isClientSide && removalReason.shouldDestroy()) {
			Containers.dropContents(this.level, this, (Container)this);
		}
		super.remove(removalReason);
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand interactionHand) {
		if (!this.canAddPassenger(player) || player.isSecondaryUseActive()) {
			return this.interactWithChestVehicle(this::gameEvent, player);
		}
		return super.interact(player, interactionHand);
	}

	@Override
	public void openCustomInventoryScreen(Player player) {
		player.openMenu(this);
		if (!player.level.isClientSide) {
			this.gameEvent(GameEvent.CONTAINER_OPEN, player);
			PiglinAi.angerNearbyPiglins(player, true);
		}
	}

	@Override
	public Item getDropItem()
	{
		return getNewBoatType().chestItem.get().asItem();
	}

	@Override
	public void clearContent() {
		this.clearChestVehicleContent();
	}

	@Override
	public int getContainerSize() {
		return CONTAINER_SIZE;
	}

	@Override
	public ItemStack getItem(int i) {
		return this.getChestVehicleItem(i);
	}

	@Override
	public ItemStack removeItem(int i, int j) {
		return this.removeChestVehicleItem(i, j);
	}

	@Override
	public ItemStack removeItemNoUpdate(int i) {
		return this.removeChestVehicleItemNoUpdate(i);
	}

	@Override
	public void setItem(int i, ItemStack itemStack) {
		this.setChestVehicleItem(i, itemStack);
	}

	@Override
	public SlotAccess getSlot(int i) {
		return this.getChestVehicleSlot(i);
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player player) {
		return this.isChestVehicleStillValid(player);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		if (this.lootTable == null || !player.isSpectator()) {
			this.unpackLootTable(inventory.player);
			return ChestMenu.threeRows(i, inventory, this);
		}
		return null;
	}

	public void unpackLootTable(@Nullable Player player) {
		this.unpackChestVehicleLootTable(player);
	}

	@Override
	@Nullable
	public ResourceLocation getLootTable() {
		return this.lootTable;
	}

	@Override
	public void setLootTable(@Nullable ResourceLocation resourceLocation) {
		this.lootTable = resourceLocation;
	}

	@Override
	public long getLootTableSeed() {
		return this.lootTableSeed;
	}

	@Override
	public void setLootTableSeed(long l) {
		this.lootTableSeed = l;
	}

	@Override
	public NonNullList<ItemStack> getItemStacks() {
		return this.itemStacks;
	}

	@Override
	public void clearItemStacks() {
		this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
	}
}
