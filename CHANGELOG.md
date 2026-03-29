# Changelog

## 0.1.0-alpha.2

Second public alpha update for Minecraft 1.20.1.

Highlights:
- stabilized the data-generation pipeline and fixed the `gaussrifle_legacy` datagen failure
- restored and corrected multiple legacy custom-render paths for armor, magazines, rockets, shields, and model-backed weapons
- improved GUI presentation for armor previews, ladders, charging station, shields, magazines, and rockets
- restored inventory and placed-world visual rendering for `ammo_press`, `chem_lab`, and `turret_base`
- fixed legacy UV behavior for migrated 3D Techguns models by matching old `ModelBox` texture mapping more closely
- cleaned up the recent machine rendering implementation and removed temporary debug instrumentation

Known limitations:
- this release is still focused on visual and structural parity rather than full gameplay restoration
- held weapon transforms still need a broader consistency pass across the entire gun roster
- several complex world blocks still need additional visual verification
- projectiles, gun logic, machine logic, and advanced interactions are still incomplete

## 0.1.0-alpha.1

Initial public alpha baseline for Minecraft 1.20.1.

Highlights:
- migrated a large portion of legacy Techguns item, block, and texture assets into the new resource layout
- registered the first major wave of old 1.12.2 items and blocks as proper 1.20.1 content entries
- restored a broad legacy item-render path for weapons, armor previews, magazines, shields, and selected model-backed items
- fixed missing texture and missing model issues that previously broke item and block rendering
- restored wearable armor rendering for migrated armor sets, including the riot coat path
- rebuilt initial block/item model generation and language generation for the new content baseline

Known limitations:
- this release is still visual-first and does not yet restore the full Techguns gameplay loop
- multiple weapon and item transforms still need additional polish in GUI, first-person, and third-person views
- machine logic, projectiles, doors, and many advanced interactions are still incomplete
