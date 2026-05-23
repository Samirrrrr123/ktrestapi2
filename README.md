# URL Shortener REST API

Служба сокращения URL на Clojure.

## Сборка

```bash
chmod +x build.sh
./build.sh
```

## Запуск

Сервер (отдельный терминал):

```bash
java -jar target/url-shortener-0.1.0-standalone.jar 8080
```

Клиент:

```bash
java -jar target/url-shortener-0.1.0-standalone.jar
```

## API

| Метод  | Путь                              | Описание                    |
|--------|-----------------------------------|-----------------------------|
| POST   | /normal-url                       | Создать короткий URL        |
| GET    | /short-url/:code                  | Получить обычный URL        |
| PUT    | /short-url/:short/normal-url/:normal | Изменить запись          |
| DELETE | /short-url/:code                  | Удалить запись              |
