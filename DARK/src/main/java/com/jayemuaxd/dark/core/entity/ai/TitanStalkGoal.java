package com.jayemuaxd.dark.core.entity.ai;

import com.jayemuaxd.dark.core.entity.TitanEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 * El Titán detecta al jugador y lo persigue a distancia, sin acercarse a atacar directamente.
 * Mantiene una distancia "de observación" en vez de cerrar el hueco por completo.
 * Solo actúa si el Titán está activo (de día); de noche este goal no hace nada.
 */
public class TitanStalkGoal extends Goal {

    private static final double STALK_DISTANCE_SQR = 10.0D * 10.0D; // distancia que intenta mantener
    private static final double DETECTION_RANGE = 48.0D;

    private final TitanEntity titan;
    private Player target;

    public TitanStalkGoal(TitanEntity titan) {
        this.titan = titan;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!titan.isActiveNow()) return false;
        this.target = titan.level().getNearestPlayer(titan, DETECTION_RANGE);
        return this.target != null && !titan.isCrouchWalking();
    }

    @Override
    public boolean canContinueToUse() {
        return titan.isActiveNow() && target != null && target.isAlive()
                && titan.distanceToSqr(target) <= DETECTION_RANGE * DETECTION_RANGE
                && !titan.isCrouchWalking();
    }

    @Override
    public void tick() {
        if (target == null) return;

        double distSqr = titan.distanceToSqr(target);

        // Siempre mira hacia el jugador: parte central de lo perturbador, "te observa".
        titan.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (distSqr > STALK_DISTANCE_SQR) {
            // Se acerca lentamente, sin correr - transmite calma amenazante, no urgencia.
            titan.getNavigation().moveTo(target, 0.9D);
        } else {
            // Ya está a distancia de "observación": deja de avanzar y solo lo sigue con la mirada.
            titan.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.target = null;
    }
}
