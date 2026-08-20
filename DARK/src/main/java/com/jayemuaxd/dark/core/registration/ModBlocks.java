package com.jayemuaxd.dark.core.registration;

import com.jayemuaxd.dark.core.DarkCoreMod;
import com.jayemuaxd.dark.core.block.DarkCalderoBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkCoreMod.MODID);

    public static final RegistryObject<Block> CALDERO_ALQUIMICO = BLOCKS.register("caldero_alquimico",
            () -> new DarkCalderoBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).noOcclusion())
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
