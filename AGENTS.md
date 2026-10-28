# Brewhouse

attribution: on

Fabric mod, Minecraft 26.3, Java 25, Loom `net.fabricmc.fabric-loom` (unobfuscated Mojang names, no mappings).

- design rule: as vanilla as possible, fewest new mechanics and items. drinks are registered potions, brewing is data-driven `minecraft:brewing` recipes, aging reuses the vanilla barrel via `BarrelBlockMixin`
- common code in `src/main`, client-only code in `src/client`, game tests in `src/gametest`
- package `ie.semyon.brewhouse`, mod id `brewhouse`
- verify with `./gradlew build runGameTest`; use `./gradlew runClient` to check in game
- decompiled sources: `./gradlew genSources`, then read the `-sources.jar` under `.gradle/loom-cache/minecraftMaven`
