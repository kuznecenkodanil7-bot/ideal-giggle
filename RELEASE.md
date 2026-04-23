# Release checklist

## Prepare
1. Update version in `gradle.properties`.
2. Update `CHANGELOG.md`.
3. Verify `fabric.mod.json` version matches the release.
4. Run local build:
   ```bash
   ./gradlew build
   ```

## GitHub release
1. Push all commits to `main`.
2. Create a tag:
   ```bash
   git tag v0.1.0
   git push origin v0.1.0
   ```
3. Open GitHub Releases.
4. Create release from the tag.
5. Upload `build/libs/*.jar`.
6. Paste notes from `releases/v0.1.0.md`.

## Recommended assets
- `raidpunishui-x.y.z.jar`
- source code zip (automatic by GitHub)
