# 🔥 XSS уязвимость в проекте Vulnerable SPA

## Общие сведения об XSS

**XSS (Cross-Site Scripting)** — это тип уязвимости веб-приложений, при котором злоумышленный код (JavaScript) встраивается в страницу и выполняется на стороне браузера пользователя.

**Цели XSS-атак:**
- Угон сессий (кук)
- Подмена содержимого страницы
- Перенаправление на фишинговые сайты
- Встройка клавиатурных шпионов и криптоджакеров

**Основные типы XSS:**
- Stored XSS (с сохранением на сервере)
- Reflected XSS (во время обработки запроса)
- DOM-based XSS (через DOM-модификации на клиенте)

---

## Где и как XSS реализуется в проекте

В проекте **Vulnerable SPA** XSS-уязвимость возникает при работе:
- с содержимым заметок (`content`)
- с результатами поиска

Пользовательский ввод вставляется в DOM без экранирования.

---

## Места в коде возникновения XSS

### Frontend (React)

**src/components/NoteList.js**
```jsx
<div className="note-content">
  {note.content}
</div>
```

**src/components/SearchResults.js**
```jsx
<div className="search-result">
  {result.content}
</div>
```

**Причина:** текст напрямую рендерится без очистки или экранирования.

### Backend (Spring Boot)

**src/main/java/com/example/vulnerablespa/controller/NoteController.java**
```java
@PostMapping("/api/notes")
public Note createNote(@RequestBody Note note) {
    return noteRepository.save(note);
}
```

**Причина:** нет валидации и фильтрации пользовательских данных.

---

## Как защититься от XSS

### Frontend

- Использовать экранирование HTML-символов.
- Подключить библиотеки очистки (например, `DOMPurify`):
- Избегать `dangerouslySetInnerHTML`, если это возможно.

### Backend

- Валидировать все входные данные через Bean Validation (`@SafeHtml`, кастомные валидаторы).
- Очищать все строки, содержащие потенциальный HTML.

---