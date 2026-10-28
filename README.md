# Brewhouse

Irish brewing for Minecraft 26.3 (Fabric), built almost entirely from vanilla pieces. No new blocks or items: the drinks are potions, the still is the brewing stand, and the cask is the barrel.

## How to brew

| Step | Brewing stand | Result |
|---|---|---|
| Mash | Water Bottle + Wheat | Wash |
| Brew | Wash + Cocoa Beans | Stout: Strength, a little Nausea |
| Distil | Wash + Copper Ingot | Poitín: Strength II, Nausea, a moment of Blindness |
| Age | Leave Poitín in a barrel for 3 in-game days | Whiskey: Resistance, Regeneration, a little Nausea |

Poitín only ages while it's in a barrel in a loaded chunk. Taking it out pauses aging rather than resetting it.

## Develop

Needs JDK 25+.

```sh
./gradlew runClient     # dev client with the mod loaded
./gradlew runGameTest   # headless game tests for brewing and aging
./gradlew build         # jar lands in build/libs/
```

Brewing recipes are data in `src/main/resources/data/brewhouse/recipe/`. Versions live in `gradle.properties`; check https://fabricmc.net/develop when bumping Minecraft.
