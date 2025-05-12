

# 📚 Vulnerable SPA — Документация проекта

## Оглавление
- [Описание проекта](#описание-проекта)
- [Структура репозитория](#структура-репозитория)
- [Как запустить проект](#как-запустить-проект)
- [Функциональность](#функциональность)
- [Известные уязвимости](#известные-уязвимости)
- [Контакты](#контакты)

---

## Описание проекта

**Vulnerable SPA** — это учебное одностраничное приложение (SPA - Single Page Applciation) с преднамеренно допущенными уязвимостями.

**Стек технологий:**
- **Frontend**: React.js
- **Backend**: Java 17, Spring Boot
- **База данных**: H2 (in-memory)

Цели проекта:
- Демонстрация уязвимостей из OWASP Top 10
- Отработка навыков анализа защищённости WEB приложений
- Практика защищённой разработки и DevSecOps

---

## Структура репозитория

```plaintext
vulnerable-spa/
├── backend/            # Backend на Java + Spring Boot
│   ├── src/main/java/
│   │   └── com/example/vulnerablespa/
│   │       ├── config/             # Конфигурации
│   │       ├── controller/         # Контроллеры REST API
│   │       ├── dto/                # 
│   │       ├── model/              # Модели данных (Entity)
│   │       ├── repository/         # Репозитории JPA
│   │       └── security/           # Механизмы безопасности
│   └── src/main/resources/
│       ├── db
│       │   └── migration/          # Скрипты миграции БД
│       ├── staitc/                 # Контент для сайта
│       └── application.properties  # Настройки springboot приложения          
├────── pom.xml   # Файл конфигурации модуля backend 
├── frontend/           # Frontend на React
│   ├── public/
│   └── src/
│       ├── components/
│       ├── pages/
│       └── utils/
├── Dockerfile
├── .gitlab-ci.yml
├── pom.xml                         # Файл конфигурации проекта Maven
└── README.md                       # Документация проекта 
```
---
**Схемы**

- [Схема сборки](docs/Maven-vuild.md)

---

## Как запустить проект

### 1. Требования
- Java 17+
- Maven 3.8+
- Node.js 20+, npm 9+
- Docker

### 2. Запуск через Docker

```bash
git clone https://gitlab.com/alexander.kremlev/vulnerable-spa.git
cd vulnerable-spa
docker build -t vulnerable-spa .
docker run -e WAF_LEVEL=ADVANCED --rm -p 8080:8080 vulnerable-spa
```
WAF_LEVEL = NONE, BASIC, ADVANCED

Приложение доступно по адресу:
- http://localhost:8080/

### 3. Запуск вручную

mvn clean install
mvn spring-boot:run

---

## Функциональность

- Вход пользователей (пока только 2 admin/admin и user/user)
- CRUD заметок
- Поиск заметок
- Сессии через cookies

---

## Известные уязвимости

> ⚠️ Эти уязвимости оставлены для обучения!

- **XSS** [XSS](docs/XSS.md)
- **Broken Authentication** [BAC](docs/Broken-Authentication.md)
- **SQL injection** [SQLI](docs/SQL-Injection.md)

---

## Контакты

**Разработчик**: Alexander Kremlev  
[Профиль GitLab](https://gitlab.com/alexander.kremlev)

---