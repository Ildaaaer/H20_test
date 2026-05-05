# H20 Store Backend

Backend-приложение для магазина компьютерной техники и комплектующих.

Проект реализует REST API для работы с товарами четырех типов:

- настольные компьютеры;
- ноутбуки;
- мониторы;
- жесткие диски.

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

## Доменная Модель

Все товары имеют общие поля:

- серийный номер;
- производитель;
- цена;
- количество единиц продукции на складе.

Дополнительные поля зависят от типа товара:

- настольный компьютер: форм-фактор;
- ноутбук: размер экрана;
- монитор: диагональ;
- жесткий диск: объем.

В приложении используется одна таблица `products` и JPA-наследование `SINGLE_TABLE`.
Это позволяет хранить товары в простой структуре БД, но при этом работать в коде с отдельными классами товаров.

## REST API

Базовый URL:

```text
/api/products
```

### Создать товар

```http
POST /api/products
```

Пример тела запроса для ноутбука:

```json
{
  "type": "LAPTOP",
  "serialNumber": "LAP-001",
  "manufacturer": "Lenovo",
  "price": 1200.00,
  "quantity": 5,
  "laptopSize": 15
}
```

### Редактировать товар

```http
PUT /api/products/{id}
```

Пример тела запроса:

```json
{
  "type": "LAPTOP",
  "serialNumber": "LAP-001",
  "manufacturer": "Lenovo",
  "price": 1099.99,
  "quantity": 6,
  "laptopSize": 15
}
```

### Получить товар по id

```http
GET /api/products/{id}
```

### Получить товары по типу

```http
GET /api/products?type=LAPTOP
```

Доступные значения `type`:

- `DESKTOP_COMPUTER`
- `LAPTOP`
- `MONITOR`
- `HARD_DRIVE`

## База Данных

Для локального запуска используется PostgreSQL через Docker Compose.

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

Hibernate не создает таблицы автоматически. В проекте используется:

```yaml
spring.jpa.hibernate.ddl-auto: validate
```

Это значит, что структура БД должна быть создана миграциями, а Hibernate только проверяет ее соответствие Entity-классам.

## Конфигурирование

Параметры подключения к БД можно переопределить через переменные окружения:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
SERVER_PORT
```

Например, для Windows PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/h20_store"
$env:DB_USERNAME="h20_user"
$env:DB_PASSWORD="h20_password"
```

Основные настройки приложения находятся в файле:

```text
src/main/resources/application.yaml
```

## Сборка

Windows PowerShell:

```powershell
.\mvnw.cmd clean package
```

Linux/macOS:

```bash
./mvnw clean package
```

## Локальное Развертывание

### 1. Запустить PostgreSQL

```bash
docker compose up -d
```

### 2. Запустить приложение через Maven

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

### 3. Альтернативный запуск через jar

Сначала собрать проект:

```powershell
.\mvnw.cmd clean package
```

Затем запустить jar:

```powershell
java -jar target/h20-store-0.0.1-SNAPSHOT.jar
```

## Swagger

После запуска приложения Swagger UI доступен здесь:

```text
http://localhost:8080/swagger-ui/index.html
```

## Тесты

Запуск тестов:

```powershell
.\mvnw.cmd test
```

Что покрыто:

- `ProductServiceTest` проверяет бизнес-сценарии сервиса;
- `ProductFactoryTest` проверяет создание нужных subtype товаров;
- `ProductMapperTest` проверяет преобразование Entity в Response DTO;
- `ProductControllerTest` проверяет REST API через MockMvc;
- `ProductRepositoryTest` проверяет JPA и ограничения PostgreSQL через Testcontainers;
- `ProductApiIntegrationTest` проверяет happy path API: создать, получить, обновить, отфильтровать.

Если Docker недоступен, Testcontainers-тесты будут пропущены.

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

Основные статусы:

- `400 Bad Request` — ошибка валидации или некорректный тип товара;
- `404 Not Found` — товар не найден;
- `409 Conflict` — конфликт на уровне базы, например повторяющийся `serialNumber`;
- `500 Internal Server Error` — неожиданная ошибка сервера.
