package ie.semyon.brewhouse;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

// poitín left in a vanilla barrel turns into whiskey
public final class BarrelAging {
	// three in-game days, a nod to irish whiskey needing three years in the cask
	public static final int WHISKEY_AGE_TICKS = 3 * 24000;
	private static final int CHECK_INTERVAL = 20;

	// ticks spent in a barrel, so age only counts while the bottle is actually in one
	public static final DataComponentType<Integer> BARREL_AGE = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Brewhouse.id("barrel_age"),
		DataComponentType.<Integer>builder().persistent(Codec.INT).build());

	private BarrelAging() {
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BarrelBlockEntity barrel) {
		if (level.getGameTime() % CHECK_INTERVAL != 0) {
			return;
		}

		boolean changed = false;
		for (int slot = 0; slot < barrel.getContainerSize(); slot++) {
			ItemStack stack = barrel.getItem(slot);
			PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
			if (!stack.is(Items.POTION) || contents == null || !contents.is(Drinks.POITIN)) {
				continue;
			}

			int age = stack.getOrDefault(BARREL_AGE, 0) + CHECK_INTERVAL;
			if (age >= WHISKEY_AGE_TICKS) {
				barrel.setItem(slot, whiskey());
			} else {
				stack.set(BARREL_AGE, age);
			}
			changed = true;
		}

		if (changed) {
			barrel.setChanged();
		}
	}

	private static ItemStack whiskey() {
		ItemStack stack = new ItemStack(Items.POTION);
		stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.of(Drinks.WHISKEY), Optional.of(Drinks.WHISKEY_COLOR), List.of(), Optional.empty()));
		return stack;
	}

	static void init() {
	}
}
