## Backend проекта Pixel Battle

### Запуск приложения
Вручную
1. Собрать jar файл с помощью Gradle `./gradlew build`
2. Запустить docker-compose `docker-compose up -d`

При наличии утилиты `make`
1. Запустить команду `make start`

⚠️Ошибка подключения к redis внутри докер контейнера была исправлена. Запущенное приложение внутри докер контейнера работает исправно⚠️

### API приложения
- Порт по умолчанию `8085`
- OpenAPI `/api/swagger`
