# H20 Store Backend

Backend-приложение для магазина компьютерной техники и комплектующих.

## Стек

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway
- Lombok
- Bean Validation
- Swagger / OpenAPI
- Maven
- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers

## Доменная модель

Все товары имеют общие поля:

- серийный номер;
- производитель;
- цена;
- количество на складе.

Дополнительные поля зависят от типа товара:

- настольный компьютер: форм-фактор;
- ноутбук: размер экрана;
- монитор: диагональ;
- жесткий диск: объем.

Базовый URL:

```text
/api/products
```

### Создать товар

```http
POST /api/products
```

### Получить товар по id

```http
GET /api/products/{id}
```

### Получить товары по типу

```http
GET /api/products?type=LAPTOP
```

## База Данных

Настройки по умолчанию:

```text
DB_URL=jdbc:postgresql://localhost:5432/h20_store
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

Схема базы создается через Flyway-миграцию:

```text
src/main/resources/db/migration/V1__create_products_table.sql
```

## Swagger

После запуска приложения Swagger UI доступен здесь:

```text
http://localhost:8080/swagger-ui/index.html
```

## Запуск Проекта

### 1. Запустить PostgreSQL

```bash
docker compose up -d
```

### 2. Запустить приложение

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```



## Тесты

Что покрыто:

- `ProductServiceTest` проверяет бизнес-сценарии сервиса;
- `ProductFactoryTest` проверяет создание нужных subtype товаров;
- `ProductMapperTest` проверяет преобразование Entity в Response DTO;
- `ProductControllerTest` проверяет REST API через MockMvc;
- `ProductRepositoryTest` проверяет JPA и ограничения PostgreSQL через Testcontainers;
- `ProductApiIntegrationTest` проверяет happy path API: создать, получить, обновить, отфильтровать.


## Обработка Ошибок

Ошибки возвращаются в едином формате:

```json
{
  "timestamp": "2026-05-05T19:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/products",
  "fieldErrors": {
    "price": "must be greater than or equal to 0.01"
  }
}
```
- `400 Bad Request` — ошибка валидации или некорректный тип товара;
- `404 Not Found` — товар не найден;
- `409 Conflict` — конфликт на уровне базы, например повторяющийся `serialNumber`;
- `500 Internal Server Error` — неожиданная ошибка сервера.
