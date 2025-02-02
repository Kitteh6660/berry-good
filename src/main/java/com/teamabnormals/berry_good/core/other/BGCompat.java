package com.teamabnormals.berry_good.core.other;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.berry_good.core.BGConfig;
import com.teamabnormals.berry_good.core.registry.BGItems;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.Collections;
import java.util.function.Supplier;

public class BGCompat {

	public static void registerCompat() {
		registerAnimalFoods();
		registerCompostables();
		addGlowBerryEffects();
	}

	private static void registerAnimalFoods() {
		Chicken.FOOD_ITEMS = CompoundIngredient.of(Chicken.FOOD_ITEMS, Ingredient.of(BGItems.SWEET_BERRY_PIPS.get(), BGItems.GLOW_BERRY_PIPS.get()));
		Collections.addAll(Parrot.TAME_FOOD, BGItems.SWEET_BERRY_PIPS.get(), BGItems.GLOW_BERRY_PIPS.get());
	}

	private static void registerCompostables() {
		DataUtil.registerCompostable(BGItems.SWEET_BERRY_PIPS.get(), 0.30F);
		DataUtil.registerCompostable(BGItems.GLOW_BERRY_PIPS.get(), 0.30F);
	}

	private static void addGlowBerryEffects() {
		if (BGConfig.COMMON.glowBerriesGiveGlowing.get()) {
			Supplier<MobEffectInstance> instance = () -> new MobEffectInstance(MobEffects.GLOWING, 300);
			ObfuscationReflectionHelper.setPrivateValue(FoodProperties.class, Foods.GLOW_BERRIES, Collections.singletonList(Pair.of(instance, 1.0F)), "f_38728_");
		}
	}
}
