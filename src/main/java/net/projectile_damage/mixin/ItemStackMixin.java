package net.projectile_damage.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.IProjectileWeapon;
import net.projectile_damage.internal.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {
    @Inject(method = "applyAttributeModifiers", at = @At("TAIL"))
    private void projectile_damage$applyWeaponModifier(
            EquipmentSlot slot,
            BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer,
            CallbackInfo ci
    ) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.getItem() instanceof IProjectileWeapon weapon
                && weapon.getProjectileDamage() > 0.0
                && weapon.hasProjectileDamageModifier(slot)) {
            consumer.accept(
                    EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE,
                    new EntityAttributeModifier(
                            Constants.GENERIC_PROJECTILE_MODIFIER_ID,
                            weapon.getProjectileDamage(),
                            EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }
}
