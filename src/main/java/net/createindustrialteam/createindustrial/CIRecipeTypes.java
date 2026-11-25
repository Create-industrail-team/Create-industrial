package net.createindustrialteam.createindustrial;

import com.mojang.serialization.Codec;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipeParams;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe.Serializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.EngravingRecipe;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum CIRecipeTypes implements IRecipeTypeInfo, StringRepresentable {

    ENGRAVING(EngravingRecipe::new);

    public static final Predicate<RecipeHolder<?>> CAN_BE_AUTOMATED = r -> !r.id()
            .getPath()
            .endsWith("_manual_only");

    public final ResourceLocation id;
    public final Supplier<RecipeSerializer<?>> serializerSupplier;
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializerObject;
    @Nullable
    private final DeferredHolder<RecipeType<?>, RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    private boolean isProcessingRecipe;

    public static final Codec<CIRecipeTypes> CODEC = StringRepresentable.fromEnum(CIRecipeTypes::values);

    CIRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = CreateIndustrial.asResource(name);
        this.serializerSupplier = serializerSupplier;
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
        isProcessingRecipe = false;
    }

    CIRecipeTypes(StandardProcessingRecipe.Factory<?> processingFactory) {
        this(() -> new Serializer<>(processingFactory));
        isProcessingRecipe = true;
    }

    CIRecipeTypes(ProcessingRecipe.Factory<ItemApplicationRecipeParams, ? extends ItemApplicationRecipe> itmeApplicationFactory) {
        this(() -> new ItemApplicationRecipe.Serializer<>(itmeApplicationFactory));
        isProcessingRecipe = true;
    }

    @Internal
    public static void register(IEventBus modEventBus) {
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {
        return (RecipeType<R>) type.get();
    }

    public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> find(I inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    public static boolean shouldIgnoreInAutomation(RecipeHolder<?> recipe) {
        RecipeSerializer<?> serializer = recipe.value().getSerializer();
        if (serializer != null && AllTags.AllRecipeSerializerTags.AUTOMATION_IGNORE.matches(serializer))
            return true;
        return !CAN_BE_AUTOMATED.test(recipe);
    }

    @Override
    public @NotNull String getSerializedName() {
        return id.toString();
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateIndustrial.MODID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, CreateIndustrial.MODID);
    }
}
