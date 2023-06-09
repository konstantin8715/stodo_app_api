# Документация к API
API для приложения stodo - приложения для планирования задач
студентов, со следующей функциональностью:
+ Аутентификация и авторизация пользователей с помощью JWT-токена
+ Сброс пароля
+ Создание, редактирование и удаление разделов обучения
+ Создание, редактирование и удаление предметов обучения
+ Создание, редактирование и удаление задач обучения
+ Экспорт/Импорт разделов обучения посредством файла
<br>

## Разделы документации
1. [Аутентификация и авторизация пользователя](#1-аутентификация-и-авторизация-пользователя)
2. [Сброс пароля пользователя](#2-сброс-пароля-пользователя)
3. [Создание, удаление и редактирование разделов обучения пользователем](#3-Создание-удаление-и-редактирование-разделов-обучения-пользователем)
4. [Создание, удаление и редактирование предметов обучения пользователем](#4-Создание-удаление-и-редактирование-предметов-обучения-пользователем)
5. [Создание, удаление и редактирование задач пользователем](#5-Создание-удаление-и-редактирование-задач-пользователем)
6. [Экспорт/Импорт разделов обучения посредством файла](#6-ЭкспортИмпорт-разделов-обучения-посредством-файла)

## 1. Аутентификация и авторизация пользователя
### Регистрация пользователя:
***Метод запроса***
```console
POST
```

***URL***
```console
http://localhost:8080/api/auth/signup
```
***Тело запроса***
```json
{
  "email": "bagrovichh@gmail.com",
  "password": "test123",
  "confirmPassword": "test123"
}
```
**Описание:**
<br>
Регистрирует пользователя в системе и отправляет на email ссылку
для подтверждения почты.
<br>

***Тело ответа***
```json
{
  "message": "User registered successfully! Please check your email!"
}
```

### Верификация регистрации пользователя:
***Метод запроса***
```console
GET
```

***URL***
```console
http://localhost:8080/api/auth/verifyRegistration?token=[token]
```
***Тело запроса***
```console
Нет
```
**Описание:**
<br>
Подтверждает почту пользователя и открывает ему доступ к
ресурсам приложения.
<br>

***Тело ответа***
```json
{
  "message": "User verification successfully!"
}
```

### Аутентификация пользователя:
***Метод запроса***
```console
POST
```

***URL***
```console
http://localhost:8080/api/auth/signin
```
***Тело запроса***
```json
{
  "email": "bagrovichh@gmail.com",
  "password": "newtest123"
}
```
**Описание:**
<br>
Возвращает JWT-токен для доступа к ресурсам приложения.
<br>

***Тело ответа***
```json
{
  "success": true,
  "token": "Bearer []"
}
```

## 2. Сброс пароля пользователя
### Отправка запроса на сброс пароля:
***Метод запроса***
```console
POST
```

***URL***
```console
http://localhost:8080/api/auth/forgotPassword
```
***Тело запроса***
```json
{
  "email": "bagrovichh@gmail.com"
}
```
**Описание:**
<br>
Отправляет на указанную почту код для сброса пароля.
<br>

***Тело ответа***
```json
{
  "message": "Reset password code send to your email"
}
```

### Сброс пароля:
***Метод запроса***
```console
POST
```

***URL***
```console
http://localhost:8080/api/auth/resetPassword
```
***Тело запроса***
```json
{
  "email": "bagrovichh@gmail.com",
  "code": "9280",
  "password": "newtest123",
  "confirmPassword": "newtest123"
}
```
**Описание:**
<br>
Меняет пароль пользователя.
<br>

***Тело ответа***
```json
{
  "message": "Password change successfully"
}
```

## 3. Создание, удаление и редактирование разделов обучения пользователем
### Создание раздела обучения:
***Метод запроса***
```console
POST
```
***URL***
```console
http://localhost:8080/api/semester
```
***Тело запроса***
```json
{
  "title": "Семестр 1"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```
**Описание:**
<br>
Создает раздел обучения.
<br>

***Тело ответа***
```json
{
  "id": 43,
  "title": "Семестр 1",
  "createdAt": "2023-05-26T13:54:46.023188804"
}
```

### Получение всех разделов обучения для пользователя:
***Метод запроса***
```console
GET
```

***URL***
```console
http://localhost:8080/api/semester
```
***Тело запроса***
```console
Нет
```
***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Возвращает все разделы обучения для пользователя.
<br>

***Тело ответа***
```json
[
  {
    "id": 29,
    "title": "Семестр 1",
    "createdAt": "2023-05-22T12:47:30.457111"
  },
  {
    "id": 30,
    "title": "Семестр 1",
    "createdAt": "2023-05-22T12:56:31.927216"
  },
  {
    "id": 31,
    "title": "Семестр 1",
    "createdAt": "2023-05-22T15:51:45.162282"
  }
]
```

### Изменение раздела обучения:
***Метод запроса***
```console
PATCH
```

***URL***
```console
http://localhost:8080/api/semester/{semesterId}
```

***Тело запроса***
```json
{
  "title": "Семестр 1 (измененный)"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Изменяет раздел обучения пользователя.
<br>

***Тело ответа***
```json
{
  "id": 33,
  "title": "Семестр 1 (измененный)",
  "createdAt": "2023-05-22T15:55:12.925929"
}
```

### Удаление раздела обучения:
***Метод запроса***
```console
DELETE
```

***URL***
```console
http://localhost:8080/api/semester/{semesterId}
```

***Тело запроса***
```console
Нет
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Удаляет раздел обучения пользователя.
<br>

***Тело ответа***
```json
{
  "id": 33,
  "title": "Семестр 1 (измененный)",
  "createdAt": "2023-05-22T15:55:12.925929"
}
```

## 4. Создание, удаление и редактирование предметов обучения пользователем
### Создание предмета обучения:
***Метод запроса***
```console
POST
```
***URL***
```console
http://localhost:8080/api/subject/{semesterId}
```
***Тело запроса***
```json
{
  "title": "Метрология"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```
**Описание:**
<br>
Создает предмет обучения.
<br>

***Тело ответа***
```json
{
  "id": 144,
  "title": "Метрология",
  "createdAt": "2023-05-26T14:11:38.011404243"
}
```

### Получение всех предметов обучения для пользователя:
***Метод запроса***
```console
GET
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}
```
***Тело запроса***
```console
Нет
```
***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Возвращает все предметы обучения для пользователя.
<br>

***Тело ответа***
```json
[
  {
    "id": 124,
    "title": "БЖД",
    "createdAt": "2023-05-22T20:58:49.674324"
  },
  {
    "id": 125,
    "title": "Метрология",
    "createdAt": "2023-05-22T20:58:49.680073"
  },
  {
    "id": 126,
    "title": "Физика",
    "createdAt": "2023-05-22T20:58:49.68175"
  }
]
```

### Изменение предмета обучения:
***Метод запроса***
```console
PATCH
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}/{subjectId}
```

***Тело запроса***
```json
{
  "title": "Геометрическое моделирование (измененно)"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Изменяет предмет обучения пользователя.
<br>

***Тело ответа***
```json
{
  "id": 144,
  "title": "Геометрическое моделирование (измененно)",
  "createdAt": "2023-05-26T14:11:38.011404"
}
```

### Удаление предмета обучения:
***Метод запроса***
```console
DELETE
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}/{subjectId}
```

***Тело запроса***
```console
Нет
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Удаляет предмет обучения пользователя.
<br>

***Тело ответа***
```json
{
  "id": 144,
  "title": "Геометрическое моделирование (измененно)",
  "createdAt": "2023-05-26T14:11:38.011404"
}
```

## 5. Создание, удаление и редактирование задач пользователем
### Создание задачи:
***Метод запроса***
```console
POST
```
***URL***
```console
http://localhost:8080/api/task/{semesterId}/{subjectId}
```
***Тело запроса***
```json
{
  "title": "Лабораторная работа 3",
  "deadlineDate": "2023-05-21T17:37:55.532Z"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```
**Описание:**
<br>
Создает задачу.
<br>

***Тело ответа***
```json
{
  "id": 167,
  "title": "Лабораторная работа 3",
  "createdAt": "2023-05-26T14:48:00.762533748",
  "deadlineDate": "2023-05-21T17:37:55.532",
  "done": false
}
```

### Получение всех задач для пользователя:
***Метод запроса***
```console
GET
```
***URL***
```console
http://localhost:8080/api/task
```
***Тело запроса***
```console
Нет
```
***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Возвращает все задачи для пользователя.
<br>

***Тело ответа***
```json
[
  {
    "id": 93,
    "title": "Лабораторная работа 1",
    "createdAt": "2023-05-22T12:50:00.416069",
    "deadlineDate": "2023-05-21T17:37:55.532",
    "done": false
  },
  {
    "id": 95,
    "title": "Лабораторная работа 3",
    "createdAt": "2023-05-22T12:50:52.240231",
    "deadlineDate": "2023-05-21T17:37:55",
    "done": false
  },
  {
    "id": 120,
    "title": "ИДЗ 1",
    "createdAt": "2023-05-22T19:26:14.318936",
    "deadlineDate": "2023-05-24T21:00:00",
    "done": false
  }
]
```

### Получение всех задач для конкретного раздела обучения и предмета:
***Метод запроса***
```console
GET
```

***URL***
```console
http://localhost:8080/api/task/{semesterId}/{subjectId}
```
***Тело запроса***
```console
Нет
```
***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Возвращает все задачи для конкретного раздела обучения и предмета.
<br>

***Тело ответа***
```json
[
  {
    "id": 93,
    "title": "Лабораторная работа 1",
    "createdAt": "2023-05-22T12:50:00.416069",
    "deadlineDate": "2023-05-21T17:37:55.532",
    "done": false
  },
  {
    "id": 95,
    "title": "Лабораторная работа 3",
    "createdAt": "2023-05-22T12:50:52.240231",
    "deadlineDate": "2023-05-21T17:37:55",
    "done": false
  },
  {
    "id": 120,
    "title": "ИДЗ 2",
    "createdAt": "2023-05-22T19:26:14.318936",
    "deadlineDate": "2023-05-24T21:00:00",
    "done": false
  }
]
```

### Изменение задач обучения:
***Метод запроса***
```console
PATCH
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}/{subjectId}/{taskId}/u
```

***Тело запроса***
```json
{
  "title": "Лабораторная работа 1 (измененно)",
  "deadlineDate": "2023-05-21T17:37:55.532Z"
}
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Изменяет задачу пользователя.
<br>

***Тело ответа***
```json
{
  "id": 93,
  "title": "Лабораторная работа 1 (измененно)",
  "createdAt": "2023-05-22T12:50:00.416069",
  "deadlineDate": "2023-05-21T17:37:55.532",
  "done": false
}
```

### Изменение статуса задачи обучения:
***Метод запроса***
```console
PATCH
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}/{subjectId}/{taskId}
```

***Тело запроса***
```console
Нет
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Изменяет статус задачи пользователя.
<br>

***Тело ответа***
```json
{
  "id": 93,
  "title": "Лабораторная работа 1 (измененно)",
  "createdAt": "2023-05-22T12:50:00.416069",
  "deadlineDate": "2023-05-21T17:37:55.532",
  "done": true
}
```

### Удаление задачи:
***Метод запроса***
```console
DELETE
```

***URL***
```console
http://localhost:8080/api/subject/{semesterId}/{subjectId}/{taskId}
```

***Тело запроса***
```console
Нет
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```

***Описание:***
<br>
Удаляет задачу пользователя.
<br>

***Тело ответа***
```json
{
  "id": 93,
  "title": "Лабораторная работа 1 (измененно)",
  "createdAt": "2023-05-22T12:50:00.416069",
  "deadlineDate": "2023-05-21T17:37:55.532",
  "done": true
}
```

## 6. Экспорт/Импорт разделов обучения посредством файла
### Экспорт раздела как файла .json:
***Метод запроса***
```console
GET
```
***URL***
```console
http://localhost:8080/api/share/dump/{semesterId}
```
***Тело запроса***
```console
Нет
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```
**Описание:**
<br>
Экспортирует выбранный раздел в файл.
<br>

***Тело ответа***
```console
Файл .json
```

### Импорт раздела из файла .json":
***Метод запроса***
```console
POST
```
***URL***
```console
http://localhost:8080/api/share/upload
```
***Тело запроса***
```console
Файл .json
```

***Заголовок запроса***
```console
Authorization: Bearer [token]
```
**Описание:**
<br>
Импортирует раздел обучения из файла.
<br>

***Тело ответа***
```console
Файл .json
```