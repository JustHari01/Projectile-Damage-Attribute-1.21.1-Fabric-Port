package net.projectile_damage.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.IProjectileWeapon;
import net.projectile_damage.api.RangedWeaponKind;
import net.projectile_damage.internal.Constants;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(RangedWeaponItem.class)
abstract class RangedWeaponItemMixin extends Item implements IProjectileWeapon {
    private List<EquipmentSlot> allowedSlots = List.of(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    private Double customLaunchVelocity = null;
    private RangedWeaponKind rangedWeaponKind = RangedWeaponKind.from(this);

    // Helper, not actual source of truth
    private double projectileDamage = 0;

    RangedWeaponItemMixin(Settings settings) {
        super(settings);
    }

    public void setRangedWeaponKind(RangedWeaponKind kind) {
        this.rangedWeaponKind = kind;
        if (projectileDamage == 0) {
            // If no scaling was configured just yet, use default damage
            this.setProjectileDamage(kind.damage());
        }
    }

    @Override
    public RangedWeaponKind getRangeWeaponKind() {
        return this.rangedWeaponKind;
    }

    public void setProjectileDamage(double value, boolean mainHand, boolean offHand) {
        allowedSlots = new ArrayList<>();
        if (mainHand) {
            allowedSlots.add(EquipmentSlot.MAINHAND);
        }
        if (offHand) {
            allowedSlots.add(EquipmentSlot.OFFHAND);
        }
        projectileDamage = value;
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    @Override
    public boolean hasProjectileDamageModifier(EquipmentSlot slot) {
        return allowedSlots.contains(slot);
    }

    @Override
    public void setCustomLaunchVelocity(Double value) {
        customLaunchVelocity = value;
    }

    @Override
    public Double getCustomLaunchVelocity() {
        return customLaunchVelocity;
    }
}
