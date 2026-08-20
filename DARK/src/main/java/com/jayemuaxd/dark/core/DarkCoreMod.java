package com.jayemuaxd.dark.core;

import com.jayemuaxd.dark.core.alchemy.ElementData;
import com.jayemuaxd.dark.core.registration.ModBlocks;
import com.jayemuaxd.dark.core.registration.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// El value acá debe coincidir con el modId en META-INF/mods.toml
@Mod(DarkCoreMod.MODID)
public class DarkCoreMod
{
    // Mod núcleo del modpack DARK. Define reglas de atmósfera y mecánicas centrales
    // (ver roadmap-modpack.md): terror de día / calma de noche.
    public static final String MODID = "darkcore";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DarkCoreMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register game content
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // TODO(Titán): ModEntities.java no existe todavía en el proyecto (solo
        // TitanEntity.java), así que el registro de entidades y sus atributos
        // se sacaron temporalmente para poder compilar. Se reintegra cuando
        // se reconstruya ModEntities con el DeferredRegister<EntityType<?>>.
        // modEventBus.addListener(this::registerAttributes);
        // ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("DARK Core: setup común completado");

        // Fuerza la carga de la clase (dispara el bloque static que llena
        // MATERIAL_ELEMENTS/ENTITY_ELEMENTS) y confirma en el log que el
        // sistema de Alquimia (material -> elemento) quedó listo.
        ElementData.resolve(net.minecraft.world.item.Items.STONE);
        LOGGER.info("DARK Core: sistema de Alquimia (ElementData) cargado");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("DARK Core: servidor iniciando");
    }
}
