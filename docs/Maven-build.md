# 🔧 Maven Build Flow для проекта Vulnerable SPA

## Основная последовательность сборки

```
[1] mvn install
        ↓
[2] frontend-maven-plugin: install-node-and-npm
        ↓
[3] frontend-maven-plugin: npm install
        ↓
[4] frontend-maven-plugin: npm run build
        ↓
[5] maven-resources-plugin: copy frontend build → static/
        ↓
[6] spring-boot-maven-plugin: build executable JAR
```

## Пояснения к этапам

| Шаг | Описание |
|:----|:---------|
| **[1] mvn install** | Запуск Maven для сборки всего проекта. |
| **[2] install-node-and-npm** | Установка Node.js и npm локально в проект. |
| **[3] npm install** | Установка зависимостей фронтенда. |
| **[4] npm run build** | Сборка оптимизированной версии фронтенда. |
| **[5] copy frontend build → static/** | Копирование собранного фронтенда в папку ресурсов Spring Boot. |
| **[6] build executable JAR** | Финальная сборка приложения в исполняемый `.jar` файл. |

---

## Вариант сборки без фронтенда (профиль `no-frontend`)

```
[1] mvn install -Pno-frontend
        ↓
[2] (Пропуск этапов установки Node/npm и сборки фронтенда)
        ↓
[3] spring-boot-maven-plugin: build executable JAR
```

Только backend будет собран в jar-файл без работы с фронтендом.

---