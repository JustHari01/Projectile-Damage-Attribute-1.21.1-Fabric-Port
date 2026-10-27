package net.projectile_damage.api;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.projectile_damage.ProjectileDamageMod;

public class EntityAttributes_ProjectileDamage {
    public static final Identifier attributeId = Identifier.of(ProjectileDamageMod.ID, "generic");
    public static final String translationKey = "attribute.name.projectile_damage.generic";
    public static final RegistryEntry<EntityAttribute> GENERIC_PROJECTILE_DAMAGE = register(attributeId, new ClampedEntityAttribute(translationKey, 0, 0.0, 1024.0));

    private static RegistryEntry<EntityAttribute> register(Identifier id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, RegistryKey.of(RegistryKeys.ATTRIBUTE, id), attribute);
    }
}
