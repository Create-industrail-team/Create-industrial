
package net.createindustrialteam.createindustrial;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CICreativeModeTabs {


    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateIndustrial.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_INDUSTRIAL_TAB =
            REGISTER.register("create_industrial_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("creativeTab." + CreateIndustrial.MODID + ".create_industrial"))
                            .icon(() -> new ItemStack(CIItems.STEEL_INGOT.get()))
                            .displayItems(new RegistrateDisplayItemsGenerator(true, CICreativeModeTabs.CREATE_INDUSTRIAL_TAB))
                            .build()
            );

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    // Based on "AllCreativeModeTabs" class in the Create Mod
    private static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

        private final boolean addItems;
        private final DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter;

        private RegistrateDisplayItemsGenerator(boolean addItems, DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter) {
            this.addItems = addItems;
            this.tabFilter = tabFilter;
        }

        private static Function<Item, ItemStack> makeStackFunc() {
            Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();

            // Add item to modify it's itemStack added to the creative mode tab
            // (For example: backtanks having full air)
            Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>> simpleFactories = Map.of(
            );

            simpleFactories.forEach((entry, factory) -> {
                factories.put(entry.asItem(), factory);
            });

            return item -> {
                Function<Item, ItemStack> factory = factories.get(item);
                if (factory != null) {
                    return factory.apply(item);
                }
                return new ItemStack(item);
            };
        }

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            CIItems.ITEMS.getEntries().forEach(item ->
                    output.accept(item.get())
            );
            //outputAll(output, collectBlocks(), makeStackFunc(), null);
            //output.accept(CIBlocks.LASER_ENGRAVER.get());

        }

        private List<Item> collectBlocks() {
            List<Item> items = new ReferenceArrayList<>();
            System.out.println("Register Create Industrial items:");
            for (RegistryEntry<Block, Block> entry : CreateIndustrial.registrate().getAll(Registries.BLOCK)) {
                if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                    continue;
                Item item = entry.get()
                        .asItem();
                if (item == Items.AIR)
                    continue;
                items.add(item);
                System.out.println(entry.get().getName());
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }
    }

    private static void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, ItemStack> stackFunc, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
        for (Item item : items) {
            output.accept(stackFunc.apply(item));
        }
    }
}
