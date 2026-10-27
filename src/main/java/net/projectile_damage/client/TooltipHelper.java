package net.projectile_damage.client;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.IProjectileWeapon;

import java.util.ArrayList;
import java.util.List;

import java.text.DecimalFormat;

public class TooltipHelper {
    private static final DecimalFormat MODIFIER_FORMAT = new DecimalFormat("#.##");

    public static void updateTooltipText(ItemStack itemStack, List<Text> lines) {
        if (itemStack.getItem() instanceof IProjectileWeapon weapon && weapon.getProjectileDamage() > 0.0) {
            mergeAttributeLines_MainHandOffHand(lines);
            replaceAttributeLines_BlueWithGreen(lines);
            appendProjectileDamageLine(weapon, lines);
        }
    }

    private static void appendProjectileDamageLine(IProjectileWeapon weapon, List<Text> lines) {
        boolean mainHand = weapon.hasProjectileDamageModifier(EquipmentSlot.MAINHAND);
        boolean offHand = weapon.hasProjectileDamageModifier(EquipmentSlot.OFFHAND);
        if (!mainHand && !offHand) {
            return;
        }

        if (!lines.isEmpty() && !lines.get(lines.size() - 1).getString().isEmpty()) {
            lines.add(Text.empty());
        }
        String handKey = mainHand && offHand
                ? "item.modifiers.both_hands"
                : mainHand ? "item.modifiers.mainhand" : "item.modifiers.offhand";
        lines.add(Text.translatable(handKey).formatted(Formatting.GRAY));
        lines.add(Text.literal(" ")
                .append(Text.translatable("attribute.modifier.equals." + EntityAttributeModifier.Operation.ADD_VALUE.getId(),
                        MODIFIER_FORMAT.format(weapon.getProjectileDamage()),
                        Text.translatable(EntityAttributes_ProjectileDamage.translationKey)))
                .formatted(Formatting.DARK_GREEN));
    }

    private static void mergeAttributeLines_MainHandOffHand(List<Text> tooltip) {
        List<Text> heldInHandLines = new ArrayList<>();
        List<Text> mainHandAttributes = new ArrayList<>();
        List<Text> offHandAttributes = new ArrayList<>();
        for (int i = 0; i < tooltip.size(); i++) {
            var line = tooltip.get(i);
            var content = line.getContent();
            if (content instanceof TranslatableTextContent translatableText) {
                if (translatableText.getKey().startsWith("item.modifiers")) {
                    heldInHandLines.add(line);
                }
                if (translatableText.getKey().startsWith("attribute.modifier")) {
                    if (heldInHandLines.size() == 1) {
                        mainHandAttributes.add(line);
                    }
                    if (heldInHandLines.size() == 2) {
                        offHandAttributes.add(line);
                    }
                }
            }
        }
        if(heldInHandLines.size() == 2) {
            var mainHandLine = tooltip.indexOf(heldInHandLines.get(0));
            var offHandLine = tooltip.indexOf(heldInHandLines.get(1));
            tooltip.remove(mainHandLine);
            tooltip.add(mainHandLine, Text.translatable("item.modifiers.both_hands").formatted(Formatting.GRAY));
            tooltip.remove(offHandLine);
            for (var offhandAttribute: offHandAttributes) {
                if(mainHandAttributes.contains(offhandAttribute)) {
                    tooltip.remove(tooltip.lastIndexOf(offhandAttribute));
                }
            }

            var lastIndex = tooltip.size() - 1;
            var lastLine = tooltip.get(lastIndex);
            if (lastLine.getString().isEmpty()) {
                tooltip.remove(lastIndex);
            }
        }
    }

    private static void replaceAttributeLines_BlueWithGreen(List<Text> tooltip) {
        var attributeTranslationKey = EntityAttributes_ProjectileDamage.translationKey;
        for (int i = 0; i < tooltip.size(); i++)  {
            var line = tooltip.get(i);
            var content = line.getContent();
//            System.out.println(i + ": " + content + " " + line.getClass());
            if (content instanceof TranslatableTextContent translatable) {
                var isProjectileAttributeLine = false;
                var attributeValue = 0.0;
//                System.out.println("Is translatable content");
                if (translatable.getKey().startsWith("attribute.modifier.plus.0")) { // `.0` suffix for addition
//                    System.out.println("Is attribute line");
                    for (var arg: translatable.getArgs()) {
//                        System.out.println("Sub-content type: " + arg.getClass());
                        if (arg instanceof String string) {
                            try {
                                var number = Double.valueOf(string);
                                attributeValue = number;
                            } catch (Exception ignored) { }
                        }
                        if (arg instanceof Text attributeText) {
                            if (attributeText.getContent() instanceof TranslatableTextContent attributeTranslatable) {
//                                System.out.println("Translatable sub-content: " + arg);
                                if (attributeTranslatable.getKey().startsWith(attributeTranslationKey)) {
//                                    System.out.println("Projectile attribute found");
                                    isProjectileAttributeLine = true;
                                }
                            }
                        }
                    }
                }

                if (isProjectileAttributeLine && attributeValue > 0) {
                    // The construction of this line is copied from ItemStack.class
                    var greenAttributeLine = Text.literal(" ")
                            .append(
                                    Text.translatable("attribute.modifier.equals." + EntityAttributeModifier.Operation.ADD_VALUE.getId(),
                                            new Object[]{ MODIFIER_FORMAT.format(attributeValue), Text.translatable(attributeTranslationKey)})
                            )
                            .formatted(Formatting.DARK_GREEN);
                    tooltip.set(i, greenAttributeLine);
                }
            }
        }
    }
}
