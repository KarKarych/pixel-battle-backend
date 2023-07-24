## Backend проекта Pixel Battle

### Запуск приложения
Вручную
1. Собрать jar файл с помощью Gradle `./gradlew build`
2. Запустить docker-compose `docker-compose up -d`

Примечание: на данный момент redis не работает, когда приложение запущено из docker-compose. Причина ещё не найдена.
При локальном запуске приложения подключение к redis происходит нормально.

При наличии утилиты `make`
1. Запустить команду `make start`

### API приложения
- Порт по умолчанию `8085`
- OpenAPI `/api/swagger`
