# NubeComics APP - DSY1105

Aplicación Android para gestión de mangas desarrollada con Kotlin, Jetpack Compose y arquitectura MVVM. Incluye integración con API externa JSONPlaceholder y microservicio Spring Boot para CRUD de mangas.

## 👥 Integrantes del Equipo - Grupo 15

- **Irina Martínez Anufriew** 
- **Fabian Parraguez**

## 📱 Funcionalidades Implementadas

### 1. Arquitectura MVVM con Jetpack Compose
- Interfaz moderna con Material Design 3
- Navegación con Navigation Drawer
- ViewModels con StateFlow para manejo de estado
- Repository pattern para abstracción de datos

### 2. Integración con API Externa (JSONPlaceholder)
- **Endpoint**: https://jsonplaceholder.typicode.com/posts
- Consumo de posts mediante Retrofit 2.11.0
- Visualización de posts con título, cuerpo y autor
- Gestión de estados de carga y errores
- **Archivos**: `Post.kt`, `PostApiService.kt`, `PostRepository.kt`, `PostViewModel.kt`, `PostScreen.kt`

### 3. Microservicio Spring Boot - Gestión de Mangas
- **Base URL**: http://localhost:8080/api/mangas
- CRUD completo de mangas (Crear, Leer, Actualizar, Eliminar)
- Base de datos H2 en memoria
- CORS habilitado para comunicación con app Android
- 7 mangas precargados en la base de datos

#### Endpoints del Microservicio:
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/mangas` | Obtener todos los mangas |
| GET | `/api/mangas/{id}` | Obtener un manga por ID |
| POST | `/api/mangas` | Crear un nuevo manga |
| PUT | `/api/mangas/{id}` | Actualizar un manga existente |
| DELETE | `/api/mangas/{id}` | Eliminar un manga |
| GET | `/api/mangas/search/autor?autor={nombre}` | Buscar por autor |
| GET | `/api/mangas/search/genero?genero={genero}` | Buscar por género |
| GET | `/api/mangas/search/titulo?titulo={titulo}` | Buscar por título |

### 4. Integración App-Microservicio
- Comunicación mediante Retrofit con el microservicio local
- Pantalla de gestión de mangas con interfaz Material 3
- Formularios para crear y editar mangas
- Confirmación de eliminación
- Actualización automática de la lista después de operaciones CRUD
- Manejo de errores y estados de carga
- **Archivos**: `Manga.kt`, `MangaApiService.kt`, `MangaRepository.kt`, `MangaViewModel.kt`, `MangaScreen.kt`

### 5. Pruebas Unitarias (>80% cobertura)
- **Android**: 15 tests unitarios
  - `PostRepositoryTest.kt`: 5 tests
  - `MangaRepositoryTest.kt`: 5 tests
  - `MangaViewModelTest.kt`: 10 tests
- **Backend**: 10 tests unitarios
  - `MangaServiceTest.java`: 10 tests con Mockito
- Uso de coroutines test para testing asíncrono
- Repositorios fake para aislamiento de tests

### 6. Configuración de Seguridad
- Network Security Config para permitir tráfico HTTP local
- Comunicación con emulador mediante 10.0.2.2:8080

## 🛠️ Tecnologías Utilizadas

### Android App
- **Lenguaje**: Kotlin 1.9.0
- **UI**: Jetpack Compose con Material3
- **Arquitectura**: MVVM
- **Min SDK**: 33 / Target SDK: 36
- **Networking**: Retrofit 2.11.0 + Gson Converter
- **Testing**: JUnit 4, kotlinx-coroutines-test 1.8.1, Espresso
- **Build System**: Gradle 8.13

### Backend (Microservicio)
- **Framework**: Spring Boot 3.2.0
- **Lenguaje**: Java 17
- **Base de Datos**: H2 (en memoria)
- **ORM**: Spring Data JPA
- **Testing**: JUnit 5 + Mockito
- **Build System**: Maven

## 📋 Requisitos Previos

- **Android Studio**: Hedgehog o superior
- **JDK**: 17 o superior
- **Maven**: 3.6+ (para el backend)
- **Emulador Android**: API 36 o dispositivo físico con Android 13+

## 🚀 Pasos para Ejecutar el Proyecto

### Paso 1: Iniciar el Microservicio (Backend)

1. Navega a la carpeta del backend:
   ```powershell
   cd C:\Users\irina\Desktop\AppNubeComics-Backend
   ```

2. Compila el proyecto con Maven:
   ```powershell
   mvn clean package -DskipTests
   ```

3. Ejecuta el JAR generado:
   ```powershell
   java -jar target\nubecomics-backend-1.0.0.jar
   ```

4. Verifica que el servicio esté corriendo:
   ```powershell
   curl http://localhost:8080/api/mangas
   ```
   
   Deberías ver un JSON con 7 mangas precargados.

### Paso 2: Ejecutar la Aplicación Android

1. Abre Android Studio y carga el proyecto desde:
   ```
   C:\Users\irina\Desktop\AppNubeComics_003V_Grupo15-main
   ```

2. Sincroniza el proyecto con Gradle (puede tomar unos minutos la primera vez)

3. Inicia un emulador Android o conecta un dispositivo físico

4. Ejecuta la aplicación presionando el botón ▶️ Run o con:
   ```powershell
   .\gradlew.bat installDebug
   ```

5. La aplicación se abrirá mostrando el menú principal con las siguientes opciones:
   - **2.1.1 API Externa**: Ver posts de JSONPlaceholder
   - **Gestión de Mangas (CRUD)**: Administrar mangas del microservicio

### Paso 3: Ejecutar las Pruebas Unitarias

**Tests Android:**
```powershell
.\gradlew.bat :app:testDebugUnitTest
```

**Tests Backend:**
```powershell
cd C:\Users\irina\Desktop\AppNubeComics-Backend
mvn test
```

## 📂 Estructura del Proyecto

### Aplicación Android
```
app/src/main/java/com/vivitasol/carcasamvvm/
├── data/
│   ├── model/
│   │   ├── Post.kt                    # Modelo de datos para posts
│   │   └── Manga.kt                   # Modelo de datos para mangas
│   ├── network/
│   │   ├── PostApiService.kt          # API JSONPlaceholder
│   │   ├── MangaApiService.kt         # API del microservicio
│   │   └── RetrofitInstance.kt        # Configuración Retrofit
│   └── repository/
│       ├── PostRepository.kt          # Repositorio de posts
│       └── MangaRepository.kt         # Repositorio de mangas
├── viewmodels/
│   ├── PostViewModel.kt               # ViewModel para posts
│   └── MangaViewModel.kt              # ViewModel para mangas
└── views/
    ├── MainActivity.kt                # Activity principal
    ├── MenuShellView.kt               # Menú de navegación
    ├── PostScreen.kt                  # Pantalla de posts
    └── MangaScreen.kt                 # Pantalla de gestión de mangas

app/src/test/java/com/vivitasol/carcasamvvm/
├── PostRepositoryTest.kt              # Tests del repositorio de posts
├── MangaRepositoryTest.kt             # Tests del repositorio de mangas
└── MangaViewModelTest.kt              # Tests del ViewModel de mangas
```

### Backend (Microservicio)
```
src/main/java/com/vivitasol/comics/
├── model/
│   └── Manga.java                     # Entidad JPA de Manga
├── repository/
│   └── MangaRepository.java           # JpaRepository para Manga
├── service/
│   └── MangaService.java              # Lógica de negocio
├── controller/
│   └── MangaController.java           # REST Controller
└── config/
    └── DataInitializer.java           # Datos iniciales (7 mangas)

src/test/java/com/vivitasol/comics/
└── MangaServiceTest.java              # Tests unitarios con Mockito
```

## 📸 Capturas de Pantalla

### APK Firmado y Keystore
[Las capturas se agregarán después de generar el APK firmado]

## 🔧 Configuración Adicional

### Network Security Config
El archivo `network_security_config.xml` permite la comunicación HTTP con el microservicio local:
```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
        <domain includeSubdomains="true">localhost</domain>
    </domain-config>
</network-security-config>
```

### Datos Precargados en el Microservicio
- Chainsaw Man - $10.990
- Heaven Officials Blessing - $25.990
- JoJo's Bizarre Adventure: Phantom Blood - $15.990
- Kaguya-sama: Love Is War - $10.990
- Made in Abyss - $12.990
- Tokyo Revengers - $10.990
- Yona of the Dawn - $10.990

## 📝 Notas Importantes

- El emulador Android usa `10.0.2.2` para acceder a `localhost` de la máquina host
- El microservicio debe estar corriendo antes de usar la funcionalidad de gestión de mangas
- La base de datos H2 es en memoria, los datos se pierden al reiniciar el servicio
- Los tests unitarios cubren más del 80% del código lógico

## 📄 Licencia

Proyecto académico - DSY1105 - DuocUC

---

**Nota**: Este proyecto fue desarrollado como evaluación final para el curso DSY1105 - Programación de Aplicaciones Móviles.

