# Known Issues

## Alpha Scope

This release is a visual and structural baseline, not a feature-complete gameplay port.

Current high-level limitations:
- many weapons still require final transform polish in GUI, first-person, and third-person contexts
- some complex block-items and machine previews still need visual adjustment
- advanced block behavior is not fully restored for all migrated content
- gun logic, projectile systems, and machine gameplay loops are not yet feature-complete

## Visual Issues Being Actively Tracked

- weapon GUI transforms are still being normalized as a batch
- held-item presentation still needs a full consistency pass across all gun families
- complex block-item previews need another audit after the weapon pass is complete
- emissive and lighting presentation has only been partially reviewed

For the current visual checklist and batching strategy, see [docs/VISUAL_AUDIT.md](docs/VISUAL_AUDIT.md).
