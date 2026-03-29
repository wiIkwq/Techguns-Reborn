# Visual Audit

## Goal

Lock a stable visual baseline before weapon logic, projectiles, and machines are rebuilt in depth.

## Audit Sources

Content groups are currently defined in:
- [TGItemCatalog.java](../src/main/java/com/wiik_wq/techguns/common/content/TGItemCatalog.java)
- [TGBlockCatalog.java](../src/main/java/com/wiik_wq/techguns/common/content/TGBlockCatalog.java)

## Current Status

### 1. Weapons

Status: `in progress`

What is already in place:
- legacy gun rendering pipeline is restored for the current weapon batch
- model-backed weapon rendering is connected into the same general item-render path
- broad GUI transform normalization has already started

Open work:
- complete a weapon-by-weapon GUI transform pass
- complete a first-person held transform pass
- complete a third-person held transform pass
- verify scale, horizontal alignment, vertical alignment, and rotation consistency

Recommended next batch:
- all gun items from `TGItemCatalog.GUN_ITEMS`

### 2. Armor

Status: `partially stabilized`

What is already in place:
- wearable armor rendering is restored for migrated armor sets
- riot coat rendering path is restored
- armor preview rendering in GUI is functional again

Open work:
- finish consistency checks across all armor sets, not just the manually corrected previews
- verify held preview brightness and slot fit for all legacy armor item renders
- spot-check visual parity between worn armor and item preview for each armor family

Recommended next batch:
- steam armor
- t3 power armor
- t4 power armor
- remaining tactical/combat/exo/praetor sets

### 3. Handheld and Simple Items

Status: `needs audit`

Open work:
- verify handheld tools and melee items in GUI and held contexts
- verify all flat/simple item icons for texture correctness, atlas inclusion, and language mapping
- identify any legacy items that still need a custom render path instead of flat item rendering

Recommended next batch:
- `TGItemCatalog.HANDHELD_ITEMS`
- `TGItemCatalog.SIMPLE_ITEMS`
- shield items

### 4. Block Items

Status: `mostly stabilized`

What is already in place:
- first registration pass for migrated block-items is complete
- several machine/block-item templates are already generated
- charging station has already received a dedicated model-path correction
- machine block-items now have dedicated special render paths instead of incorrect cube fallback

Open work:
- verify all generated block-item display models in GUI
- verify scale/orientation for machine block-items
- verify special door and panel-like block-items against legacy presentation

Recommended next batch:
- basic machines
- charging station
- bunker and hangar door items
- camouflage net and ladder family

### 5. World Blocks

Status: `in progress`

Open work:
- verify cutout blocks in-world
- verify lighting and emissive presentation
- verify directional and horizontal special models
- verify complex blocks that previously relied on more custom behavior
- continue the placed-machine visual pass beyond the currently restored batch

Recommended next batch:
- doors
- hangar doors
- camonet family
- sandbags
- bioblob
- lamps and neon blocks

## Execution Order

1. Weapons
2. Armor consistency pass
3. Handheld/simple items
4. Block-items
5. World blocks

## Exit Criteria For The Visual Milestone

- no missing-model or missing-texture warnings related to migrated Techguns content
- all currently registered weapons have acceptable GUI, first-person, and third-person transforms
- armor previews and worn armor are visually consistent across the migrated sets
- complex block-items are readable and correctly framed in inventory
- world blocks in the current migrated set no longer have obvious presentation regressions
