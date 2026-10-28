package ie.semyon.brewhouse.test;

import ie.semyon.brewhouse.BarrelAging;
import ie.semyon.brewhouse.Drinks;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

public class BrewhouseGameTests {
	private static final BlockPos POS = new BlockPos(0, 1, 0);

	@GameTest(maxTicks = 600)
	public void wheatBrewsWash(GameTestHelper helper) {
		assertBrews(helper, Potions.WATER, Items.WHEAT, Drinks.WASH);
	}

	@GameTest(maxTicks = 600)
	public void cocoaBrewsStout(GameTestHelper helper) {
		assertBrews(helper, Drinks.WASH, Items.COCOA_BEANS, Drinks.STOUT);
	}

	@GameTest(maxTicks = 600)
	public void copperDistilsPoitin(GameTestHelper helper) {
		assertBrews(helper, Drinks.WASH, Items.COPPER_INGOT, Drinks.POITIN);
	}

	@GameTest(maxTicks = 200)
	public void agedPoitinBecomesWhiskey(GameTestHelper helper) {
		ItemStack nearlyAged = potion(Drinks.POITIN);
		nearlyAged.set(BarrelAging.BARREL_AGE, BarrelAging.WHISKEY_AGE_TICKS - 40);
		BarrelBlockEntity barrel = barrelWith(helper, nearlyAged, potion(Potions.WATER));

		helper.succeedWhen(() -> {
			helper.assertTrue(isPotion(barrel.getItem(0), Drinks.WHISKEY), "aged poitín should turn into whiskey");
			helper.assertTrue(isPotion(barrel.getItem(1), Potions.WATER), "other potions should be left alone");
		});
	}

	@GameTest(maxTicks = 200)
	public void freshPoitinOnlyGainsAge(GameTestHelper helper) {
		BarrelBlockEntity barrel = barrelWith(helper, potion(Drinks.POITIN));

		helper.runAfterDelay(100, () -> {
			ItemStack stack = barrel.getItem(0);
			helper.assertTrue(isPotion(stack, Drinks.POITIN), "fresh poitín should not be whiskey yet");
			helper.assertTrue(stack.getOrDefault(BarrelAging.BARREL_AGE, 0) > 0, "poitín in a barrel should be aging");
			helper.succeed();
		});
	}

	private static void assertBrews(GameTestHelper helper, Holder<Potion> input, Item reagent, Holder<Potion> expected) {
		helper.setBlock(POS, Blocks.BREWING_STAND);
		BrewingStandBlockEntity stand = helper.getBlockEntity(POS, BrewingStandBlockEntity.class);
		stand.setItem(0, potion(input));
		stand.setItem(3, new ItemStack(reagent));
		stand.setItem(4, new ItemStack(Items.BLAZE_POWDER));

		helper.succeedWhen(() -> helper.assertTrue(isPotion(stand.getItem(0), expected), "expected " + expected.getRegisteredName()));
	}

	private static BarrelBlockEntity barrelWith(GameTestHelper helper, ItemStack... stacks) {
		helper.setBlock(POS, Blocks.BARREL);
		BarrelBlockEntity barrel = helper.getBlockEntity(POS, BarrelBlockEntity.class);
		for (int i = 0; i < stacks.length; i++) {
			barrel.setItem(i, stacks[i]);
		}
		return barrel;
	}

	private static ItemStack potion(Holder<Potion> potion) {
		return PotionContents.createItemStack(Items.POTION, potion);
	}

	private static boolean isPotion(ItemStack stack, Holder<Potion> potion) {
		PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
		return stack.is(Items.POTION) && contents != null && contents.is(potion);
	}
}
