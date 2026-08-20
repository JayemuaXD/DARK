package com.jayemuaxd.dark.core.registration;

import com.jayemuaxd.dark.core.DarkCoreMod;
import com.jayemuaxd.dark.core.block.DarkCalderoBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkCoreMod.MODID);

    public static final RegistryObject<Block> CALDERO_ALQUIMICO = BLOCKS.register("caldero_alquimico",
            () -> {
                // Build BlockBehaviour.Properties reflectively to avoid compile-time mapping issues
                try {
                    Class<?> propertiesClass = Class.forName("net.minecraft.world.level.block.state.BlockBehaviour$Properties");

                    // Find a static factory method named "of" or "copy" with 1 parameter
                    Method factory = null;
                    for (Method m : propertiesClass.getMethods()) {
                        if (Modifier.isStatic(m.getModifiers()) && ("of".equals(m.getName()) || "copy".equals(m.getName())) && m.getParameterCount() == 1) {
                            factory = m;
                            break;
                        }
                    }

                    Object props = null;
                    if (factory != null) {
                        Class<?> paramType = factory.getParameterTypes()[0];
                        Object arg = null;

                        // Try to obtain a suitable argument for the factory (Material or a Block/BlockBehaviour)
                        try {
                            if ("net.minecraft.world.level.material.Material".equals(paramType.getName())) {
                                Class<?> matClass = Class.forName("net.minecraft.world.level.material.Material");
                                try {
                                    Field f = matClass.getField("STONE");
                                    arg = f.get(null);
                                } catch (NoSuchFieldException ignored) {
                                    // give up on Material.STONE
                                    arg = null;
                                }
                            } else {
                                // Try Blocks.STONE reflectively
                                Class<?> blocksClass = Class.forName("net.minecraft.world.level.block.Blocks");
                                Field stoneField = blocksClass.getField("STONE");
                                arg = stoneField.get(null);
                            }
                        } catch (Throwable ignored) {
                            arg = null;
                        }

                        try {
                            props = factory.invoke(null, arg);
                        } catch (IllegalArgumentException iae) {
                            // argument type mismatch — try invoking with null
                            props = factory.invoke(null, new Object[]{null});
                        }

                        // Chain noOcclusion() if available
                        try {
                            Method noOcc = propertiesClass.getMethod("noOcclusion");
                            props = noOcc.invoke(props);
                        } catch (Throwable ignored) {
                        }

                        // Chain strength(float) if available
                        try {
                            Method strength = propertiesClass.getMethod("strength", float.class);
                            props = strength.invoke(props, 2.0f);
                        } catch (Throwable ignored) {
                        }

                        return new DarkCalderoBlock((BlockBehaviour.Properties) props);
                    }

                    // If no factory found, try to instantiate the Properties via constructor
                    Constructor<?>[] ctors = propertiesClass.getDeclaredConstructors();
                    if (ctors.length > 0) {
                        Constructor<?> ctor = ctors[0];
                        ctor.setAccessible(true);
                        Object[] args = new Object[ctor.getParameterCount()];
                        // fill args with nulls; some constructors may accept Material — null may be tolerated at runtime
                        for (int i = 0; i < args.length; i++) args[i] = null;
                        Object inst = ctor.newInstance(args);

                        // try to call noOcclusion() and strength(float)
                        try {
                            Method noOcc = propertiesClass.getMethod("noOcclusion");
                            inst = noOcc.invoke(inst);
                        } catch (Throwable ignored) {
                        }
                        try {
                            Method strength = propertiesClass.getMethod("strength", float.class);
                            inst = strength.invoke(inst, 2.0f);
                        } catch (Throwable ignored) {
                        }

                        return new DarkCalderoBlock((BlockBehaviour.Properties) inst);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                // As a last resort, attempt a naive fallback using a minimal property via Blocks.STONE copy reflectively
                try {
                    Class<?> propertiesClass = Class.forName("net.minecraft.world.level.block.state.BlockBehaviour$Properties");
                    Class<?> blocksClass = Class.forName("net.minecraft.world.level.block.Blocks");
                    Object stone = blocksClass.getField("STONE").get(null);
                    // try to find a copy factory that accepts the object's class or its superclass
                    Method copyMethod = null;
                    for (Method m : propertiesClass.getMethods()) {
                        if (Modifier.isStatic(m.getModifiers()) && "copy".equals(m.getName()) && m.getParameterCount() == 1) {
                            copyMethod = m;
                            break;
                        }
                    }
                    if (copyMethod != null) {
                        Object props = copyMethod.invoke(null, stone);
                        try {
                            Method noOcc = propertiesClass.getMethod("noOcclusion");
                            props = noOcc.invoke(props);
                        } catch (Throwable ignored) {}
                        try {
                            Method strength = propertiesClass.getMethod("strength", float.class);
                            props = strength.invoke(props, 2.0f);
                        } catch (Throwable ignored) {}
                        return new DarkCalderoBlock((BlockBehaviour.Properties) props);
                    }
                } catch (Throwable ignored) {}

                throw new RuntimeException("Unable to construct BlockBehaviour.Properties reflectively for DarkCalderoBlock");
            }
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
