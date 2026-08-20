package com.jayemuaxd.dark.core.alchemy;

/**
 * Los elementos del sistema de Alquimia de DARK (ver roadmap-modpack.md,
 * secciones "Alquimia — detalle mecánico" y "Alquimia — lista de arranque").
 *
 * PRIMARIOS (6): lo único que un material "fuente" puede dar directamente
 * en la lista de materiales. Los compuestos (Vapor, Luz, Escarcha, etc.)
 * NO se asignan a materiales — se obtienen mezclando primarios en el
 * Caldero o en la Mesa de Mezclas, nunca extrayéndolos directo de un bloque.
 *
 * EXCEPCIONES (4): unos pocos materiales muy temáticos SÍ dan un compuesto
 * directo, saltándose la mezcla, como hallazgo raro/especial (ej. Totem of
 * Undying → Vida). Están acá solo para esos casos puntuales, documentados
 * en ElementData — no se puede usar cualquier compuesto de los 45, solo
 * estos 4 tienen esa excepción confirmada en el roadmap.
 */
public enum Element {

    // --- Primarios: lo que da un material fuente al extraerlo en el Caldero ---
    FUEGO,
    AGUA,
    TIERRA,
    AIRE,
    VACIO,  // especial/avanzado — nunca sale de un material vanilla común, solo de vías especiales (ver ElementData)
    ORDEN,  // especial/avanzado — mismo criterio que Vacío, contraparte de caos/estructura

    // --- Compuestos con asignación DIRECTA excepcional (no via mezcla) ---
    VIDA,       // Totem of Undying
    MUERTE,     // Wither Rose, Wither (candidato fuerte)
    OSCURIDAD,  // Ojo de araña fermentado (candidato)
    EQUILIBRIO; // Ender Dragon (candidato, Vacío+Orden)

    public boolean isPrimario() {
        return this == FUEGO || this == AGUA || this == TIERRA || this == AIRE || this == VACIO || this == ORDEN;
    }
}
