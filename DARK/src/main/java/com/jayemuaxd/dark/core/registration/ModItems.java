package com.jayemuaxd.dark.core.registration;

import com.jayemuaxd.dark.core.DarkCoreMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DarkCoreMod.MODID);

    // No especificamos CreativeModeTab para evitar constantes que cambian entre versiones/mappings
    public static final RegistryObject<Item> CALDERO_ALQUIMICO_ITEM = ITEMS.register("caldero_alquimico",
            () -> new BlockItem(ModBlocks.CALDERO_ALQUIMICO.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
