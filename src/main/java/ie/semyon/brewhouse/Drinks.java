package ie.semyon.brewhouse;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

// drinks are plain potions so the vanilla brewing stand, bottles and tooltips do all the work
public final class Drinks {
	public static final Holder<Potion> WASH = register("wash");
	public static final Holder<Potion> STOUT = register("stout",
		new MobEffectInstance(MobEffects.STRENGTH, 1800),
		new MobEffectInstance(MobEffects.NAUSEA, 200));
	public static final Holder<Potion> POITIN = register("poitin",
		new MobEffectInstance(MobEffects.STRENGTH, 600, 1),
		new MobEffectInstance(MobEffects.NAUSEA, 600),
		new MobEffectInstance(MobEffects.BLINDNESS, 100));
	public static final Holder<Potion> WHISKEY = register("whiskey",
		new MobEffectInstance(MobEffects.RESISTANCE, 3600),
		new MobEffectInstance(MobEffects.REGENERATION, 400),
		new MobEffectInstance(MobEffects.NAUSEA, 200));

	// matches custom_color in the whiskey brewing data so barrel-aged bottles look the same
	public static final int WHISKEY_COLOR = 0xC67A1E;

	private Drinks() {
	}

	private static Holder<Potion> register(String name, MobEffectInstance... effects) {
		// the potion name drives the translation key: item.minecraft.potion.effect.brewhouse.<name>
		return Registry.registerForHolder(BuiltInRegistries.POTION, Brewhouse.id(name), new Potion(Brewhouse.MOD_ID + "." + name, effects));
	}

	static void init() {
	}
}
