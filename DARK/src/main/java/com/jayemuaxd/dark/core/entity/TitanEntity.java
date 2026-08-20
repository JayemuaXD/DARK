package com.jayemuaxd.dark.core.entity;

import com.jayemuaxd.dark.core.entity.ai.TitanCrouchApproachGoal;
import com.jayemuaxd.dark.core.entity.ai.TitanEyeContactAttackGoal;
import com.jayemuaxd.dark.core.entity.ai.TitanStalkGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * El Titán — mob emblemático del modpack DARK.
 *
 * Diseño (ver roadmap-modpack.md):
 * - Gigante, silueta completamente oscura, ojos vacíos con luz blanca interior (modelo/textura pendiente en Blockbench).
 * - No tiene sonido de pasos propio: camina en silencio, solo se nota por el sonido ambiental
 *   del terreno (hierba/hojas) que ya genera el motor del juego al pisar.
 * - Persigue al jugador a distancia sin atacar (TitanStalkGoal).
 * - Algunos se agachan y avanzan a cuatro apoyos para observar de cerca (TitanCrouchApproachGoal).
 * - Solo ataca si hay contacto visual directo jugador <-> Titán (TitanEyeContactAttackGoal).
 * - Solo está activo de día; de noche queda inmóvil/inofensivo (ver isActiveNow()).
 */
public class TitanEntity extends Monster {

    // true mientras el Titán se desplaza agachado en modo "acecho cercano" a cuatro apoyos
    private boolean crouchWalking = false;

    public TitanEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 0; // no da experiencia al morir: no es un mob para "farmear"
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D) // detecta/sigue al jugador desde muy lejos, es gigante
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void registerGoals() {
        // Orden de prioridad: acecho de cerca agachado > persecución a distancia > mirar al jugador (idle)
        this.goalSelector.addGoal(1, new TitanCrouchApproachGoal(this));
        this.goalSelector.addGoal(2, new TitanStalkGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 32.0F));

        // El ataque por contacto visual se maneja como target goal: decide CUÁNDO puede dañar,
        // no cómo se mueve (eso ya lo cubren los goals de arriba).
        this.targetSelector.addGoal(1, new TitanEyeContactAttackGoal(this));
    }

    /**
     * El Titán solo está "activo" (persigue, acecha, puede atacar) durante el día.
     * De noche entra en estado dormido: no reacciona al jugador.
     * Implementación actual: permanece en el mundo pero inmóvil/inofensivo de noche
     * (no desaparece ni se retira a una guarida - eso queda como posible mejora futura).
     */
    public boolean isActiveNow() {
        return this.level().isDay();
    }

    @Override
    public void tick() {
        super.tick();
        if (!isActiveNow()) {
            // Modo dormido: se detiene en seco y no persigue a nadie.
            this.getNavigation().stop();
            this.setTarget(null);
            this.crouchWalking = false;
        }
    }

    @Override
    public boolean isPushable() {
        return false; // un gigante no se deja empujar por el jugador ni por otros mobs
    }

    // --- Sonido silencioso al caminar ---
    // playStepSound se llama cada vez que la entidad pisa un bloque caminando; al dejarlo vacío,
    // el Titán no reproduce ningún sonido de pasos propio (a diferencia del resto de los mobs).
    @Override
    protected void playStepSound(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        // Intencionalmente vacío: el Titán camina en silencio.
    }

    @Override
    public boolean dampensVibrations() {
        return true; // no delata su posición a sensores tipo Sculk / vibraciones del mundo
    }

    public boolean isCrouchWalking() {
        return crouchWalking;
    }

    public void setCrouchWalking(boolean crouchWalking) {
        this.crouchWalking = crouchWalking;
    }
}
