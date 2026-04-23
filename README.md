# RaidPunishUI

Клиентский Fabric-мод для Minecraft **1.21.11**, который делает ники игроков в чате кликабельными и открывает GUI для выдачи наказаний.

## Что делает
- клик по нику игрока в чате;
- окно выбора действия: **/mute**, **/warn**, **/ban**, **/ipban**;
- отдельное окно ввода срока (`d` — дни, `h` — часы);
- окно выбора причины;
- встроенные пресеты правил **2.1–3.10** для личного использования;
- при выдаче команда отправляется сразу в чат;
- команда также копируется в буфер обмена.

## Формат команды
- `/mute ник время причина`
- `/warn ник время причина`
- `/ban ник время причина`
- `/ipban ник время причина`

## Формат срока
- `1d`
- `12h`
- `2d 6h`

## Важные замечания
1. Это **клиентский** мод, он не добавляет серверные команды — он только помогает быстро сформировать и отправить уже существующие команды сервера.
2. Для клика по нику используется клиентская подмена текста чата: если в сообщении найден ник игрока из таба, он становится кликабельным.
3. Из-за различий в чат-форматерах конкретного сервера и возможных изменений маппингов Yarn/Fabric для **1.21.11** может понадобиться мелкая правка mixin-перехватов.
4. В текущей версии `warn` тоже использует формат с временем, как ты и попросил.

## Сборка
Используй Gradle с Java 21:

```bash
./gradlew build
```

Готовый jar будет в `build/libs/`.

## Файлы, которые важнее всего
- `ClickableChatFormatter.java` — делает ники в чате кликабельными
- `ScreenMixin.java` — ловит клик по нику
- `ActionSelectScreen.java` — выбор типа наказания
- `DurationInputScreen.java` — ввод срока
- `ReasonSelectScreen.java` — выбор шаблона причины и отправка команды


---

## GitHub release structure
This repository already includes:
- `.github/workflows/build.yml` for GitHub Actions build
- issue and pull request templates
- `CHANGELOG.md`
- `CONTRIBUTING.md`
- `SECURITY.md`
- `RELEASE.md`
- `releases/` release notes templates

## Quick publish
```bash
git init
git add .
git commit -m "Initial release"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/raidpunishui.git
git push -u origin main
```

## Create a release
```bash
git tag v0.1.0
git push origin v0.1.0
```
Then create a GitHub Release and upload the jar from `build/libs/`.
