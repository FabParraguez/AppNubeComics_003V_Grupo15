# NubeComics Backend - Microservicio Spring Boot

Microservicio REST para gestión de cómics desarrollado con Spring Boot y base de datos H2 (en memoria).

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6+

## 🚀 Ejecución

### Opción 1: Con Maven Wrapper (recomendado)

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Opción 2: Con Maven instalado

```bash
mvn spring-boot:run
```

El servidor arrancará en **http://localhost:8080**

## 📡 Endpoints disponibles

### Comics CRUD

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/comics` | Obtener todos los comics |
| GET | `/api/comics/{id}` | Obtener un comic por ID |
| POST | `/api/comics` | Crear un nuevo comic |
| PUT | `/api/comics/{id}` | Actualizar un comic |
| DELETE | `/api/comics/{id}` | Eliminar un comic |

### Búsquedas

| Método | Endpoint | Parámetro | Descripción |
|--------|----------|-----------|-------------|
| GET | `/api/comics/search/autor` | `?nombre=` | Buscar por autor |
| GET | `/api/comics/search/genero` | `?tipo=` | Buscar por género |
| GET | `/api/comics/search/titulo` | `?texto=` | Buscar por título |

## 📝 Ejemplos de uso

### Obtener todos los comics
```bash
curl http://localhost:8080/api/comics
```

### Crear un nuevo comic
```bash
curl -X POST http://localhost:8080/api/comics \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Batman: The Dark Knight Returns",
    "autor": "Frank Miller",
    "anioPublicacion": 1986,
    "editorial": "DC Comics",
    "genero": "Superhéroes",
    "descripcion": "Historia oscura de Batman en su vejez"
  }'
```

### Actualizar un comic
```bash
curl -X PUT http://localhost:8080/api/comics/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Watchmen (Edición Deluxe)",
    "autor": "Alan Moore",
    "anioPublicacion": 1986,
    "editorial": "DC Comics",
    "genero": "Superhéroes"
  }'
```

### Eliminar un comic
```bash
curl -X DELETE http://localhost:8080/api/comics/1
```

## 🗄️ Base de datos H2

Puedes acceder a la consola de H2 para ver los datos:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:comicsdb`
- **Usuario**: `sa`
- **Password**: *(dejar vacío)*

## 📦 Modelo de datos (Comic)

```json
{
  "id": 1,
  "titulo": "Watchmen",
  "autor": "Alan Moore",
  "anioPublicacion": 1986,
  "editorial": "DC Comics",
  "genero": "Superhéroes",
  "descripcion": "Una obra maestra del cómic...",
  "portadaUrl": null,
  "fechaCreacion": "2025-11-23T10:30:00",
  "fechaActualizacion": "2025-11-23T10:30:00"
}
```

## 🔧 Configuración

La configuración principal está en `src/main/resources/application.properties`:

- Puerto del servidor: `8080`
- Base de datos: H2 en memoria
- CORS habilitado para cualquier origen (desarrollo)

## 🧪 Pruebas

```bash
mvn test
```

## 📝 Datos de prueba

Al arrancar la aplicación, se cargan automáticamente 4 cómics de ejemplo:
- Watchmen (Alan Moore)
- Maus (Art Spiegelman)
- Saga (Brian K. Vaughan)
- Sandman (Neil Gaiman)

## 🔗 Integración con App Móvil

Este microservicio está listo para integrarse con tu aplicación móvil Android. 

**Pasos siguientes:**
1. Ejecuta el backend localmente
2. Actualiza la app móvil para consumir `http://10.0.2.2:8080/api/comics` (emulador) o tu IP local (dispositivo físico)
3. Configura Retrofit con la nueva base URL

## 👥 Autores

- Grupo 15 - DSY1105 Desarrollo de Aplicaciones Móviles
