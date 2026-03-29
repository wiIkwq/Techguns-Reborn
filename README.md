# Techguns Reborn

Techguns Reborn is an alpha port of Techguns from Minecraft 1.12.2 to Forge 1.20.1.

The current project state focuses on:
- migrating legacy assets into a clean 1.20.1 content structure
- restoring item, block, armor, and machine rendering paths on modern Forge
- locking visual parity milestones before the gameplay layer is rebuilt

Current release:
- Version: `0.1.0-alpha.2`
- Minecraft: `1.20.1`
- Loader: `Forge 47.x`
- Distribution: [Modrinth](https://modrinth.com/mod/techguns-reborn)

## Status

This is an early alpha. The mod currently prioritizes visual restoration and content registration over full gameplay parity.

Working baseline:
- old 1.12.2 assets are migrated into the modern resource structure
- core items and blocks are registered on 1.20.1
- a large portion of legacy item and armor rendering is restored
- machine block-items and placed machine visuals are restored for the current migrated batch
- the game starts cleanly without the earlier missing-model and missing-texture failures

Still in progress:
- weapon first-person, third-person, and GUI transform polish
- final visual consistency across complex items, block-items, and world blocks
- projectile logic, weapon behavior, and reload systems
- machine behavior, doors, interactions, and advanced block logic

## Development Notes

Key project references:
- [CHANGELOG.md](CHANGELOG.md)
- [KNOWN_ISSUES.md](KNOWN_ISSUES.md)
- [docs/VISUAL_AUDIT.md](docs/VISUAL_AUDIT.md)

## Immediate Roadmap

1. Finish the current visual polish pass for weapons, armor previews, held transforms, and remaining world blocks.
2. Lock a stable visual milestone for the alpha branch.
3. Rebuild the gun, projectile, and machine gameplay framework on top of the cleaned-up 1.20.1 structure.
