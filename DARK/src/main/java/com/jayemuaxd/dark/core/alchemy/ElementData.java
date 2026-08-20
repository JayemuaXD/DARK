package com.jayemuaxd.dark.core.alchemy;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Corazón del sistema de Alquimia: resuelve qué elemento(s) da cada material
 * al extraerlo en el Caldero (ver roadmap-modpack.md, "Alquimia — lista de
 * arranque: material → elemento(s)" y sesión 72 para la cascada).
 *
 * CASCADA DE RESOLUCIÓN (sesión 72), en orden — se detiene en el primer paso
 * que encuentre algo:
 *   1. Lista explícita (este archivo, MATERIAL_ELEMENTS) — materiales fuente
 *      con asignación manual, la mayoría del contenido de este archivo.
 *   2. Herencia por crafteo — un ítem crafteado hereda el elemento combinado
 *      de sus ingredientes (ver roadmap sesión 52). TODO: requiere consultar
 *      el RecipeManager en tiempo de ejecución; no tiene sentido implementarlo
 *      todavía porque no existe el Caldero ni ningún recipe type propio.
 *      Queda como stub explícito hasta que se programe el Caldero.
 *   3. Fallback por categoría/tag — madera/piedra/flor no listada explícita,
 *      mobs por MobCategory, Nether/End por namespace heurístico.
 *   4. Fallback universal — Tierra x1, para que NADA quede sin elemento
 *      (incluidos ítems de mods de contenido futuros del propio modpack).
 *
 * Regla de base del roadmap: los materiales comunes solo dan los 4 elementos
 * clásicos (Fuego/Agua/Tierra/Aire) — Vacío/Orden nunca salen de esta lista
 * salvo las vías especiales explícitas (Ender Pearl, Ender Dragon, etc.).
 */
public final class ElementData {

    private ElementData() {
    }

    // --- Paso 1: lista explícita, materiales ITEM/BLOCK ---
    private static final Map<ResourceLocation, List<ElementAmount>> MATERIAL_ELEMENTS = new HashMap<>();

    // --- Paso 1 (variante entidades): mobs con elemento propio asignado ---
    private static final Map<ResourceLocation, List<ElementAmount>> ENTITY_ELEMENTS = new HashMap<>();

    static {
        bootstrapMaterials();
        bootstrapEntities();
    }

    // =========================================================================
    // API PÚBLICA
    // =========================================================================

    /**
     * Resuelve qué elemento(s) da un ItemStack al meterlo en el Caldero,
     * siguiendo la cascada completa de 4 pasos. Nunca devuelve una lista vacía:
     * en el peor caso, cae al fallback universal (Tierra x1).
     */
    public static List<ElementAmount> resolve(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return List.of();
        }
        return resolve(stack.getItem());
    }

    public static List<ElementAmount> resolve(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);

        // Paso 1: lista explícita
        List<ElementAmount> explicit = MATERIAL_ELEMENTS.get(id);
        if (explicit != null) {
            return explicit;
        }

        // Paso 2: herencia por crafteo (stub — ver TODO de la clase)
        List<ElementAmount> fromCrafting = resolveByCraftingInheritance(item);
        if (fromCrafting != null) {
            return fromCrafting;
        }

        // Paso 3: fallback por categoría/tag
        List<ElementAmount> byCategory = resolveByCategory(item, id);
        if (byCategory != null) {
            return byCategory;
        }

        // Paso 4: fallback universal — nunca se queda sin elemento
        return universalFallback();
    }

    /** Igual que resolve(Item), pero para el elemento asociado a un mob vivo. */
    public static List<ElementAmount> resolveEntity(EntityType<?> type) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);

        List<ElementAmount> explicit = ENTITY_ELEMENTS.get(id);
        if (explicit != null) {
            return explicit;
        }

        // Fallback por categoría de mob (paso 3 aplicado a entidades)
        MobCategory category = type.getCategory();
        if (category == MobCategory.MONSTER) {
            return List.of(ElementAmount.of(Element.TIERRA, 1), ElementAmount.of(Element.AIRE, 1));
        }
        // CREATURE, AMBIENT, WATER_CREATURE, WATER_AMBIENT, AXOLOTLS, etc. -> pasivo genérico
        return List.of(ElementAmount.of(Element.TIERRA, 1), ElementAmount.of(Element.AGUA, 1));
    }

    // =========================================================================
    // PASO 2: herencia por crafteo (STUB)
    // =========================================================================

    /**
     * TODO(Caldero): implementar cuando exista el Caldero y su recipe type
     * propio. La idea (roadmap sesión 52): consultar el RecipeManager del
     * servidor, encontrar la receta de crafteo vanilla/modded que produce
     * este ítem, resolver recursivamente el elemento de cada ingrediente,
     * y sumar sus ElementAmount. No se puede probar/tiene sentido conectar
     * esto todavía porque no hay ningún consumidor real (Caldero) para
     * disparar la resolución. Devuelve null a propósito para que la cascada
     * siga al paso 3.
     */
    private static List<ElementAmount> resolveByCraftingInheritance(Item item) {
        return null;
    }

    // =========================================================================
    // PASO 3: fallback por categoría/tag
    // =========================================================================

    private static List<ElementAmount> resolveByCategory(Item item, ResourceLocation id) {
        Block block = Block.byItem(item); // Blocks.AIR si el item no es un bloque
        boolean isBlock = block != null && block != net.minecraft.world.level.block.Blocks.AIR;

        if (isBlock) {
            var state = block.defaultBlockState();
            if (state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS)) {
                return List.of(ElementAmount.of(Element.TIERRA, 2), ElementAmount.of(Element.AIRE, 1));
            }
            if (state.is(BlockTags.FLOWERS)) {
                return List.of(ElementAmount.of(Element.TIERRA, 1), ElementAmount.of(Element.AIRE, 1));
            }
            if (state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.STONE_ORE_REPLACEABLES)) {
                return List.of(ElementAmount.of(Element.TIERRA, 2));
            }
            if (state.is(BlockTags.LEAVES)) {
                return List.of(ElementAmount.of(Element.AIRE, 1));
            }
            if (state.is(BlockTags.WOOL)) {
                return List.of(ElementAmount.of(Element.AIRE, 1), ElementAmount.of(Element.TIERRA, 1));
            }
        }

        // Heurística por namespace/path para Nether y End cuando no matchea ningún tag de arriba
        String path = id.getPath();
        if (id.getNamespace().equals("minecraft")) {
            if (path.contains("nether")) {
                return List.of(ElementAmount.of(Element.FUEGO, 2), ElementAmount.of(Element.TIERRA, 1));
            }
            if (path.contains("end") && !path.equals("ender_pearl") && !path.equals("ender_eye")) {
                // ender_pearl/ender_eye van en la lista explícita con Vacío; el resto de "end_*" es mundano
                return List.of(ElementAmount.of(Element.TIERRA, 2), ElementAmount.of(Element.AIRE, 1));
            }
        }

        return null; // no matchea ninguna categoría conocida -> sigue al paso 4
    }

    // =========================================================================
    // PASO 4: fallback universal
    // =========================================================================

    private static List<ElementAmount> universalFallback() {
        return List.of(ElementAmount.of(Element.TIERRA, 1));
    }

    // =========================================================================
    // REGISTRO — helpers
    // =========================================================================

    private static void register(String minecraftId, ElementAmount... amounts) {
        register("minecraft", minecraftId, amounts);
    }

    private static void register(String namespace, String path, ElementAmount... amounts) {
        MATERIAL_ELEMENTS.put(ResourceLocation.fromNamespaceAndPath(namespace, path), List.of(amounts));
    }

    private static void registerEntity(String minecraftId, ElementAmount... amounts) {
        ENTITY_ELEMENTS.put(ResourceLocation.fromNamespaceAndPath("minecraft", minecraftId), List.of(amounts));
    }

    private static ElementAmount e(Element element, int amount) {
        return ElementAmount.of(element, amount);
    }

    // =========================================================================
    // BOOTSTRAP: lista de arranque completa (roadmap "Alquimia — lista de arranque")
    // =========================================================================

    private static void bootstrapMaterials() {
        // --- 🔥 Fuego ---
        register("lava_bucket", e(Element.FUEGO, 4));
        register("blaze_rod", e(Element.FUEGO, 4));
        register("blaze_powder", e(Element.FUEGO, 4));
        register("coal", e(Element.FUEGO, 3));
        register("charcoal", e(Element.FUEGO, 3));
        register("netherrack", e(Element.FUEGO, 3));
        register("magma_block", e(Element.FUEGO, 3));
        register("fire_charge", e(Element.FUEGO, 3));
        register("gunpowder", e(Element.FUEGO, 2));
        register("tnt", e(Element.FUEGO, 2));
        register("campfire", e(Element.FUEGO, 2));

        // --- 💧 Agua ---
        register("water_bucket", e(Element.AGUA, 4));
        register("prismarine", e(Element.AGUA, 3));
        register("prismarine_shard", e(Element.AGUA, 3));
        register("kelp", e(Element.AGUA, 2));
        register("sponge", e(Element.AGUA, 2));
        register("ice", e(Element.AGUA, 2));
        register("snowball", e(Element.AGUA, 1));
        register("cod", e(Element.AGUA, 1));
        register("salmon", e(Element.AGUA, 1));
        register("pufferfish", e(Element.AGUA, 2));
        register("tube_coral", e(Element.AGUA, 3));
        register("brain_coral", e(Element.AGUA, 3));
        register("bubble_coral", e(Element.AGUA, 3));
        register("fire_coral", e(Element.AGUA, 3));
        register("horn_coral", e(Element.AGUA, 3));

        // --- 🌍 Tierra ---
        register("stone", e(Element.TIERRA, 3));
        register("raw_iron", e(Element.TIERRA, 3));
        register("raw_gold", e(Element.TIERRA, 3));
        register("raw_copper", e(Element.TIERRA, 3));
        register("emerald_ore", e(Element.TIERRA, 3));
        register("diamond_ore", e(Element.TIERRA, 3));
        register("gravel", e(Element.TIERRA, 2));
        register("dirt", e(Element.TIERRA, 2));
        register("clay_ball", e(Element.TIERRA, 2));
        register("bone", e(Element.TIERRA, 2));
        register("bone_meal", e(Element.TIERRA, 2));
        register("leather", e(Element.TIERRA, 1));
        register("wheat_seeds", e(Element.TIERRA, 1));
        register("carrot", e(Element.TIERRA, 1));
        register("potato", e(Element.TIERRA, 1));

        // --- 💨 Aire ---
        register("feather", e(Element.AIRE, 2));
        register("vine", e(Element.AIRE, 1));
        register("white_wool", e(Element.AIRE, 1));

        // --- Materiales con dos elementos a la vez ---
        register("obsidian", e(Element.FUEGO, 2), e(Element.AGUA, 2));
        register("sand", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("snow", e(Element.AGUA, 2), e(Element.AIRE, 1));
        register("red_mushroom", e(Element.TIERRA, 2), e(Element.AGUA, 1));
        register("brown_mushroom", e(Element.TIERRA, 2), e(Element.AGUA, 1));
        register("cactus", e(Element.TIERRA, 2), e(Element.FUEGO, 1));
        register("beef", e(Element.TIERRA, 1), e(Element.AGUA, 1));
        register("porkchop", e(Element.TIERRA, 1), e(Element.AGUA, 1));
        register("egg", e(Element.AIRE, 1), e(Element.TIERRA, 1));
        register("quartz", e(Element.FUEGO, 2), e(Element.TIERRA, 2));
        register("nether_wart", e(Element.TIERRA, 1), e(Element.AGUA, 2));

        // --- Excepciones: compuesto directo ---
        register("totem_of_undying", e(Element.VIDA, 1));
        register("rotten_flesh", e(Element.MUERTE, 1));
        register("fermented_spider_eye", e(Element.OSCURIDAD, 1));
        register("wither_rose", e(Element.MUERTE, 1));

        // --- Madera por tipo ---
        register("oak_log", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("spruce_log", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("birch_log", e(Element.TIERRA, 1), e(Element.AIRE, 2));
        register("acacia_log", e(Element.TIERRA, 2), e(Element.AIRE, 1), e(Element.FUEGO, 1));
        register("jungle_log", e(Element.TIERRA, 2), e(Element.AIRE, 1), e(Element.AGUA, 1));
        register("mangrove_log", e(Element.TIERRA, 2), e(Element.AGUA, 2));
        register("dark_oak_log", e(Element.TIERRA, 3));
        register("cherry_log", e(Element.TIERRA, 1), e(Element.AIRE, 2));

        // --- Flores ---
        register("poppy", e(Element.TIERRA, 1), e(Element.FUEGO, 1));
        register("dandelion", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        register("blue_orchid", e(Element.TIERRA, 1), e(Element.AGUA, 1));
        register("allium", e(Element.AIRE, 1));
        register("azure_bluet", e(Element.AGUA, 1), e(Element.AIRE, 1));
        register("red_tulip", e(Element.TIERRA, 1), e(Element.FUEGO, 1));
        register("orange_tulip", e(Element.TIERRA, 1), e(Element.FUEGO, 1));
        register("white_tulip", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        register("pink_tulip", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        register("oxeye_daisy", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        register("cornflower", e(Element.AGUA, 1), e(Element.AIRE, 1));
        register("lily_of_the_valley", e(Element.AIRE, 1));
        register("sunflower", e(Element.FUEGO, 1), e(Element.AIRE, 1));
        register("lilac", e(Element.AIRE, 1), e(Element.TIERRA, 1));
        register("rose_bush", e(Element.FUEGO, 1), e(Element.TIERRA, 2));
        register("peony", e(Element.TIERRA, 1), e(Element.AGUA, 1));

        // --- Piedra y variantes ---
        register("andesite", e(Element.TIERRA, 2));
        register("diorite", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("granite", e(Element.TIERRA, 2), e(Element.FUEGO, 1));
        register("basalt", e(Element.FUEGO, 2), e(Element.TIERRA, 2));
        register("calcite", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("tuff", e(Element.TIERRA, 2));
        register("bricks", e(Element.TIERRA, 2), e(Element.FUEGO, 1));

        // --- Metales y minerales avanzados ---
        register("iron_ingot", e(Element.TIERRA, 3));
        register("copper_ingot", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("gold_ingot", e(Element.TIERRA, 2), e(Element.FUEGO, 2));
        register("diamond", e(Element.TIERRA, 3));
        register("emerald", e(Element.TIERRA, 2), e(Element.AIRE, 1));
        register("redstone", e(Element.FUEGO, 1), e(Element.AIRE, 2));
        register("lapis_lazuli", e(Element.AGUA, 2), e(Element.AIRE, 1));
        register("amethyst_shard", e(Element.AIRE, 2), e(Element.AGUA, 1));
        register("netherite_ingot", e(Element.FUEGO, 2), e(Element.TIERRA, 3));

        // --- El End (vía especial confirmada) ---
        register("ender_pearl", e(Element.VACIO, 2));
        register("chorus_fruit", e(Element.AIRE, 1));
        register("end_stone", e(Element.TIERRA, 2), e(Element.AIRE, 1));
    }

    private static void bootstrapEntities() {
        // --- Pasivos ---
        registerEntity("cow", e(Element.TIERRA, 1), e(Element.AGUA, 1));
        registerEntity("pig", e(Element.TIERRA, 2));
        registerEntity("sheep", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        registerEntity("chicken", e(Element.AIRE, 2));
        registerEntity("rabbit", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        registerEntity("horse", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        registerEntity("wolf", e(Element.TIERRA, 2));
        registerEntity("cat", e(Element.AIRE, 1));
        registerEntity("bee", e(Element.AIRE, 1), e(Element.FUEGO, 1));
        registerEntity("squid", e(Element.AGUA, 2));
        registerEntity("cod", e(Element.AGUA, 2));
        registerEntity("turtle", e(Element.AGUA, 1), e(Element.TIERRA, 1));
        registerEntity("frog", e(Element.AGUA, 1), e(Element.TIERRA, 1));

        // --- Hostiles del Overworld ---
        registerEntity("zombie", e(Element.TIERRA, 2)); // mundano a propósito
        registerEntity("skeleton", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        registerEntity("spider", e(Element.TIERRA, 1), e(Element.AIRE, 1));
        registerEntity("creeper", e(Element.FUEGO, 2), e(Element.TIERRA, 1));

        // --- Hostiles del Nether ---
        registerEntity("blaze", e(Element.FUEGO, 3));
        registerEntity("ghast", e(Element.AIRE, 1), e(Element.FUEGO, 2));
        registerEntity("wither_skeleton", e(Element.TIERRA, 1), e(Element.FUEGO, 2));
        registerEntity("piglin", e(Element.TIERRA, 1), e(Element.FUEGO, 1));
        registerEntity("hoglin", e(Element.TIERRA, 1), e(Element.FUEGO, 1));
        registerEntity("strider", e(Element.FUEGO, 2));

        // --- Bosses/especiales ---
        registerEntity("wither", e(Element.MUERTE, 2));
        registerEntity("ender_dragon", e(Element.VACIO, 3)); // candidato a Equilibrio, sin decidir todavía
    }
}
