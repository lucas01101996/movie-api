# Movie API – Coding Test

RESTful API for managing a movie catalog with pagination, search, favorites, and basic authentication for admin operations.

---

## 🚀 Tech Stack
- Java 21
- Spring Boot
- Spring Web (MVC)
- Spring Data JPA
- H2 (in-memory database)
- Spring Security (Basic Authentication)
- Bean Validation (jakarta.validation)

---

## ▶️ How to Run

### Requirements
- Java 21
- Maven

### Run the application
```bash
mvn spring-boot:run
```

The application will start at:
```
http://localhost:8080
```

---

## 🗄️ Database (H2)

### H2 Console
```
http://localhost:8080/h2-console
```

Use the following settings:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: *(empty)*

> H2 console access is allowed by the security configuration.

### Seed data
The project initializes sample data using `data.sql`.

To disable automatic data initialization:
```properties
spring.sql.init.mode=never
```

---

## 🔐 Authentication (Basic Auth)

Authentication is handled via **Spring Security (in-memory users)**.

### Available users

| Username | Password | Role |
|---------|----------|------|
| user    | 123      | USER |
| admin   | 123      | ADMIN |

### Security rules (summary)
- `GET /movies/**` → Public
- `POST /movies/**` → ADMIN only
- `PUT /movies/**` → ADMIN only
- `DELETE /movies/**` → ADMIN only
- `/users/me/**` → Authenticated user
- `/h2-console/**` → Public

---

## 📌 API Endpoints

### 1️⃣ List movies (paginated)
**GET** `/movies`

Query parameters (optional):
- `page` (default: 0)
- `size` (default: 20)
- `sort` (e.g. `sort=title,asc`)

Example:
```bash
curl "http://localhost:8080/movies?page=0&size=5"
```

---

### 2️⃣ Search movies by title or overview (paginated)
**GET** `/movies/search?query=`

Behavior:
- If `query` is empty or not provided → returns all movies (paginated)
- If no movies match → returns an empty page

Example:
```bash
curl "http://localhost:8080/movies/search?query=matrix"
```

---

### 3️⃣ Movie details by ID
**GET** `/movies/{id}`

Example:
```bash
curl "http://localhost:8080/movies/1"
```

Responses:
- `200 OK` → movie found
- `404 Not Found` → movie does not exist

---

### 4️⃣ Create movie (ADMIN only)
**POST** `/movies`

Auth: `admin / 123`

Body example:
```json
{
  "title": "New Movie",
  "overview": "Movie overview",
  "runtimeMinutes": 120,
  "genres": ["Drama"],
  "releaseDate": "2020-01-01",
  "rating": 8
}
```

Response:
- `201 Created`

---

### 5️⃣ Update movie (ADMIN only)
**PUT** `/movies/{id}`

Auth: `admin / 123`

Body example:
```json
{
  "title": "The Matrix (Updated)",
  "overview": "Updated overview",
  "runtimeMinutes": 136,
  "genres": ["Action", "Sci-Fi"],
  "releaseDate": "1999-03-31",
  "rating": 9
}
```

Responses:
- `200 OK`
- `404 Not Found` if movie does not exist
- `400 Bad Request` if validation fails

---

### 6️⃣ Delete movie (ADMIN only)
**DELETE** `/movies/{id}`

Auth: `admin / 123`

Responses:
- `204 No Content`
- `404 Not Found` if movie does not exist

---

## ⭐ Favorites

Favorites are associated with the **authenticated user**.

### 7️⃣ List my favorite movies
**GET** `/users/me/favorites`

Auth: `user / 123` or `admin / 123`

Response:
- `200 OK`
- Returns a list of favorite movies

---

### 8️⃣ Add movie to favorites
**POST** `/users/me/favorites/{movieId}`

Auth: `user / 123`

Responses:
- `204 No Content`
- `404 Not Found` if movie does not exist

---

### 9️⃣ Remove movie from favorites
**DELETE** `/users/me/favorites/{movieId}`

Auth: `user / 123`

Response:
- `204 No Content`

---

## 🧪 API Testing (Postman)

A Postman collection is provided to simplify API testing.

### How to use
1. Open Postman
2. Click **Import**
3. Select the file:
   ```
   /postman/movie-api.postman_collection.json
   ```
4. Use Basic Auth when required:
    - user / 123
    - admin / 123

The collection contains examples for:
- Movie listing and search
- Movie details
- Create / update / delete movie (ADMIN)
- Favorites endpoints

---

## ⚠️ Error Handling

The API uses a global exception handler (`ApiExceptionHandler`) to return consistent error responses.

Example:
```json
{
  "timestamp": "2026-01-18T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Movie not found: 99"
}
```

---
