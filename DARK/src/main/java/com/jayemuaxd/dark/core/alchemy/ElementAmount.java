package com.jayemuaxd.dark.core.alchemy;

/**
 * Una cantidad de un elemento — ej. "Fuego x3" al fundir Blaze Powder.
 * Un material puede dar uno o dos ElementAmount a la vez (ver roadmap:
 * "Un material puede dar uno o dos elementos a la vez cuando tiene sentido
 * temático", ej. Obsidiana = Fuego x2 + Agua x2).
 */
public record ElementAmount(Element element, int amount) {

    public ElementAmount {
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad de un ElementAmount debe ser positiva, recibido: " + amount);
        }
    }

    public static ElementAmount of(Element element, int amount) {
        return new ElementAmount(element, amount);
    }
}
