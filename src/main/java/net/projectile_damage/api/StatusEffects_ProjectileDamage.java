package net.projectile_damage.api;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.projectile_damage.ProjectileDamageMod;

public class StatusEffects_ProjectileDamage {
    public static final String impactName = "impact";
    public static final Identifier impactId = Identifier.of(ProjectileDamageMod.ID, impactName);
    public static final Identifier impactModifierId = Identifier.of(ProjectileDamageMod.ID, "impact_projectile_damage");
    public static final StatusEffect IMPACT = (new ImpactStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAAFFDD))
            .addAttributeModifier(
                    EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE,
                    impactModifierId,
                    ProjectileDamageMod.configManager.value.status_effect_impact_multiplier_per_stack, // 0.2 by default
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
}
