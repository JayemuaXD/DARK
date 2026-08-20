package com.jayemuaxd.dark.core.registration;

import com.jayemuaxd.dark.core.DarkCoreMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DarkCoreMod.MODID);

    public static final RegistryObject<Item> CALDERO_ALQUIMICO_ITEM = ITEMS.register("caldero_alquimico",
            () -> new BlockItem(ModBlocks.CALDERO_ALQUIMICO.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
