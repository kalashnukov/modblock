# ModBlock

Отдельный минимальный плагин: только блокировка модов по сетевым каналам.
Ничего лишнего — без проверок, комбат-лога и т.п.

## Сборка (через GitHub Actions, без установки чего-либо на компьютер)

1. Создай новый репозиторий на github.com (например `ModBlock`)
2. Add file → Upload files → перетащи содержимое этой папки целиком
   (pom.xml, README.md, папки src и .github)
3. Commit changes
4. Вкладка Actions — дождись зелёной галочки
5. Открой этот запуск → внизу Artifacts → ModBlock-jar → скачай, распакуй

Готовый `ModBlock.jar` кидай в `plugins/` на хостинге.

## Настройка (plugins/ModBlock/config.yml)

```yaml
enabled: true
action: kick   # kick | alert
blocked-channels:
  - "freecam"
  - "xray"
  - "litematica"
  - "baritone"
  - "wurst"
log-all-channels: false
```

Если хочешь посмотреть, какие каналы вообще шлют игроки (чтобы точнее
подобрать список) — поставь `log-all-channels: true` и смотри в консоль
сервера при заходе игроков.
