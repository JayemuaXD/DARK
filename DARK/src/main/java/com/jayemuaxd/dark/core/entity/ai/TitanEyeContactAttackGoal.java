package com.jayemuaxd.dark.core.entity.ai;

import com.jayemuaxd.dark.core.entity.TitanEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * El Titán solo ataca cuando hay contacto visual DIRECTO con el jugador:
 * - el jugador está mirando hacia los ojos del Titán (dentro de un cono de visión estrecho), Y
 * - el Titán tiene línea de visión despejada hacia el jugador, Y
 * - están a distancia de golpe.
 *
 * Si el jugador mira hacia otro lado (aunque el Titán esté cerca y lo esté mirando a él),
 * no hay ataque. Esto es lo que separa a este goal de un ataque cuerpo a cuerpo normal.
 */
public class TitanEyeContactAttackGoal extends Goal {

    // Coseno del ángulo máximo de desviación entre la mirada del jugador y la dirección hacia
    // los ojos del Titán para que cuente como "contacto visual". ~25 grados de cono.
    private static final double EYE_CONTACT_COS_THRESHOLD = Math.cos(Math.toRadians(25.0D));
    private static final double ATTACK_RANGE = 6.0D; // el Titán es gigante, su alcance de golpe es mayor que un mob normal
    private static final int ATTACK_COOLDOWN_TICKS = 30;

    private final TitanEntity titan;
    private Player target;
    private int attackCooldown = 0;

    public TitanEyeContactAttackGoal(TitanEntity titan) {
        this.titan = titan;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!titan.isActiveNow()) return false;

        Player nearby = titan.level().getNearestPlayer(titan, ATTACK_RANGE);
        if (nearby == null) return false;

        return hasEyeContact(nearby);
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        this.target = titan.level().getNearestPlayer(titan, ATTACK_RANGE);
    }

    @Override
    public void tick() {
        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }
        if (target == null || !target.isAlive()) return;

        if (hasEyeContact(target)) {
            titan.setTarget(target);
            titan.doHurtTarget(target);
            attackCooldown = ATTACK_COOLDOWN_TICKS;
        }
    }

    @Override
    public void stop() {
        this.target = null;
        titan.setTarget(null);
    }

    /**
     * true si el jugador está mirando hacia los ojos del Titán Y el Titán tiene línea de
     * visión despejada hacia el jugador (no hay bloques/obstáculos entre ambos).
     */
    private boolean hasEyeContact(Player player) {
        if (!titan.hasLineOfSight(player)) return false;

        Vec3 playerEyePos = player.getEyePosition();
        Vec3 titanEyePos = titan.getEyePosition();

        Vec3 playerToTitan = titanEyePos.subtract(playerEyePos).normalize();
        Vec3 playerLook = player.getLookAngle().normalize();

        double dot = playerLook.dot(playerToTitan);
        return dot >= EYE_CONTACT_COS_THRESHOLD;
    }
}
