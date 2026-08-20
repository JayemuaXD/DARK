package com.jayemuaxd.dark.core.entity.ai;

import com.jayemuaxd.dark.core.entity.TitanEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 * Variante de acecho cercano: el Titán se agacha y avanza "a cuatro apoyos" (manos en el piso)
 * para acercarse mucho al jugador y observarlo de cerca, sin atacar.
 *
 * Nota de implementación: esta clase controla la LÓGICA (cuándo entra en este modo, a qué distancia
 * se detiene). La pose visual a cuatro apoyos (animación) se define después en el modelo/renderer
 * del Titán (Blockbench + Entity Renderer), no aquí - este goal solo expone isCrouchWalking()
 * en la entidad para que el renderer sepa qué animación reproducir.
 */
public class TitanCrouchApproachGoal extends Goal {

    private static final double TRIGGER_CHANCE_PER_TICK = 1.0D / 400.0D; // ocurre ocasionalmente, no siempre
    private static final double CLOSE_OBSERVE_DISTANCE_SQR = 3.5D * 3.5D;
    private static final double MAX_START_DISTANCE_SQR = 20.0D * 20.0D;

    private final TitanEntity titan;
    private Player target;
    private int duration;

    public TitanCrouchApproachGoal(TitanEntity titan) {
        this.titan = titan;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!titan.isActiveNow()) return false;
        Player nearby = titan.level().getNearestPlayer(titan, 24.0D);
        if (nearby == null) return false;
        if (titan.distanceToSqr(nearby) > MAX_START_DISTANCE_SQR) return false;

        // No siempre se agacha a acercarse: es un comportamiento ocasional para que no
        // todos los Titanes hagan lo mismo todo el tiempo.
        return titan.getRandom().nextDouble() < TRIGGER_CHANCE_PER_TICK;
    }

    @Override
    public boolean canContinueToUse() {
        return titan.isActiveNow() && target != null && target.isAlive() && duration > 0;
    }

    @Override
    public void start() {
        this.target = titan.level().getNearestPlayer(titan, 24.0D);
        this.duration = 100 + titan.getRandom().nextInt(100); // dura unos segundos y luego vuelve a stalking normal
        titan.setCrouchWalking(true);
    }

    @Override
    public void tick() {
        duration--;
        if (target == null) return;

        titan.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double distSqr = titan.distanceToSqr(target);
        if (distSqr > CLOSE_OBSERVE_DISTANCE_SQR) {
            // Se acerca más despacio que en el stalking normal: es deliberado, casi curioso.
            titan.getNavigation().moveTo(target, 0.6D);
        } else {
            titan.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.duration = 0;
        titan.setCrouchWalking(false);
    }
}
