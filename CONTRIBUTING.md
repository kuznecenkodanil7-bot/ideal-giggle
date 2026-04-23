# Contributing

## Requirements
- Java 21
- Gradle wrapper
- Minecraft 1.21.11
- Fabric Loader + Fabric API matching `gradle.properties`

## Local build
```bash
./gradlew build
```

## Coding notes
- Keep the mod client-side only.
- Preserve command format compatibility with the target server.
- Prefer small focused commits.
- Update `CHANGELOG.md` for user-visible changes.

## Before opening a PR
- Verify the mod compiles.
- Test the chat click flow in-game.
- Test command output for all punishment types.
