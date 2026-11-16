# Projectile Damage Attribute (UNOFFICIAL) — Fabric 1.21.1

An unofficial Fabric port of [Projectile Damage Attribute](https://modrinth.com/mod/projectile-damage-attribute) for Minecraft 1.21.1. It keeps the upstream mod ID, `projectile_damage`, so datapacks and integrations can continue to refer to the same attribute and effect IDs.

This port is maintained by JustHari01 and is not endorsed by or affiliated with the original author.

## Features

- Adds the `projectile_damage:generic` entity attribute.
- Adds the beneficial `projectile_damage:impact` status effect.
- Scales bow, crossbow, and compatible custom ranged-weapon projectile damage while preserving the weapon's charge, critical-hit, and velocity behavior.
- Provides `IProjectileWeapon` and `RangedWeaponKind` for modded ranged weapons, including optional launch-velocity compensation.
- Includes the upstream default bow/crossbow values and configurable fallback behavior for other ranged weapons.
- Shows the projectile damage value in item tooltips.
- Designed for Fabric clients and servers; install it on both sides.

## Validation status

The Minecraft 1.21.1 JAR built successfully with Java 21. In-game client and server behavior, projectile damage, the Impact effect, and the custom-weapon API have not been runtime-tested. The CurseForge release is marked Alpha.

## Installation

Install Fabric Loader for Minecraft 1.21.1 and Fabric API, then place the release JAR in the `mods` folder on the client and server.

## Configuration

Run the game once to create the `projectile_damage` configuration file. It retains the upstream options for Impact scaling, default bow and crossbow values, and optional fallback modifiers for other ranged weapons.

## Mod developer API

Use `IProjectileWeapon` to set a custom weapon kind and damage value. For an existing vanilla bow or crossbow item:

```java
((IProjectileWeapon) item).setProjectileDamage(10.0);
```

For a custom ranged weapon, set a `RangedWeaponKind` that matches its unscaled damage and typical launch velocity, then set its damage. Use `setCustomLaunchVelocity` when its projectile speed differs from the kind's normal speed.

The API is ported for Minecraft 1.21.1 and must be compiled against this version; binary compatibility with older Minecraft releases is not promised.

## Attribution and license

The original mod and its source are by Daedelus. This port is based on the upstream source at [ZsoltMolnarrr/ProjectileDamage](https://github.com/ZsoltMolnarrr/ProjectileDamage), branch `1.20`. The upstream project is licensed under GPL-3.0-only; this port retains that license and includes the original icon and effect texture. See [LICENSE](LICENSE).

This is an unofficial community port. The upstream author retains credit for the original project; this repository's maintainer is responsible for the 1.21.1 port.

AI tools assisted with parts of the compatibility port and its release materials.
