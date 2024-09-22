# 🍽️ API RESTful de Gestión de Recetas

Este proyecto es una API RESTful desarrollada en **Java 11+** con **Spring Boot** que permite gestionar recetas. Incluye funcionalidades para crear, leer, actualizar, eliminar recetas y votar por las mejores o peores recetas. La API utiliza una base de datos en memoria **H2** y está documentada con **Swagger** para facilitar su exploración y prueba.

---

## 📋 Características

- **CRUD de recetas**: Crear, obtener, actualizar y eliminar recetas.
- **Votación**: Los usuarios pueden votar positivamente (+1) o negativamente (-2) por las recetas.
- **Validaciones**:
    - El nombre de la receta debe tener entre 3 y 100 caracteres.
    - La lista de ingredientes no puede estar vacía.
    - El tiempo de preparación debe ser mayor a 1 minuto.
- **Persistencia**: Base de datos en memoria **H2**.
- **Documentación**: La API está documentada con **Swagger**.

---

## 🚀 Endpoints

### 📦 Recetas
- **POST /api/recetas**: Crear una nueva receta.
- **GET /api/recetas**: Obtener todas las recetas.
- **GET /api/recetas/{id}**: Obtener una receta por su ID.
- **PUT /api/recetas/{id}**: Actualizar una receta existente.
- **DELETE /api/recetas/{id}**: Eliminar una receta por su ID.

### 🔥 Votación
- **POST /api/recetas/{id}/{voto}**: Votar por una receta, donde `{voto}` puede ser `positivo` o `negativo`.

---

## 📊 Modelo de Receta

La receta está estructurada de la siguiente manera:

```json
{
  "id": Long,
  "nombre": String,
  "descripcion": String,
  "ingredientes": [String],
  "instrucciones": String,
  "tiempoPreparacion": Integer,
  "dificultad": String,
  "participante": String,
  "votos": Integer
}
```

🌱 Ejemplo de creación de receta (POST /api/recetas):

```json
{
"nombre": "Paella",
"descripcion": "Una deliciosa paella con mariscos",
"ingredientes": ["Arroz", "Mariscos", "Azafrán"],
"instrucciones": "1. Cocinar el arroz. 2. Añadir los mariscos. 3. Dejar reposar.",
"tiempoPreparacion": 60,
"dificultad": "Media",
"participante": "Chef Ana"
}
```

📜 Ejemplo de respuesta al obtener recetas (GET /api/recetas):

```json
[
  {
    "id": 1,
    "nombre": "Paella",
    "descripcion": "Una deliciosa paella con mariscos",
    "ingredientes": ["Arroz", "Mariscos", "Azafrán"],
    "instrucciones": "1. Cocinar el arroz. 2. Añadir los mariscos. 3. Dejar reposar.",
    "tiempoPreparacion": 60,
    "dificultad": "Media",
    "participante": "Chef Ana",
    "votos": 0
  }
]
```

🛠️ Requisitos

Java 11+
Maven 3.x

⚙️ Instalación y Ejecución

1.- Clona el repositorio:

```bash
git clone https://github.com/usuario/nombre-repositorio.git
```

2.- Dirígete al directorio del proyecto:

```bash
cd nombre-repositorio
```

3.- Compila el proyecto y ejecuta la aplicación:

```bash
mvn clean install
mvn spring-boot:run
```

🗄️ Acceso a la Consola H2

Puedes acceder a la consola H2 para ver la base de datos en memoria:

URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:recetasdb
Usuario: sa
Contraseña: (dejar en blanco)
📝 Documentación de la API con Swagger

La API está documentada con Swagger, lo que permite explorar los endpoints fácilmente. Puedes acceder a la interfaz de Swagger en:

URL: http://localhost:8080/swagger-ui.html

🧪 Pruebas

El proyecto incluye pruebas unitarias utilizando JUnit 5 y Mockito para la lógica de negocio de la API.

Para ejecutar las pruebas, usa el siguiente comando:

```bash
mvn test
```

⚗️ Datos de Prueba
Se incluyen datos de prueba que se pueden utilizar para validar la funcionalidad de la API. Estos datos de prueba incluyen varias recetas con distintos ingredientes, tiempos de preparación y dificultades.

🤝 Contribuir

¡Las contribuciones son bienvenidas! Si deseas mejorar el proyecto, por favor abre un issue o envía un pull request.

Realiza un fork del proyecto.
Crea una nueva rama (git checkout -b feature/nueva-funcionalidad).
Realiza los cambios necesarios y realiza un commit (git commit -m 'Añadir nueva funcionalidad').
Envía un push a la rama (git push origin feature/nueva-funcionalidad).
Abre un pull request.
📄 Licencia

Este proyecto está licenciado bajo la MIT License. Consulta el archivo LICENSE para más información.
```markdown
### Cambios agregados:

1. **Iconos en los títulos**: Los títulos están acompañados de iconos que refuerzan visualmente el contenido:
    - 🍽️ en el título principal.
    - 📋 para las características.
    - 🚀 para los endpoints.
    - 📊 para el modelo de la receta.
    - 🌱 y 📜 para ejemplos JSON.
    - 🛠️ para los requisitos técnicos.
    - ⚙️ para instalación y ejecución.
    - 🗄️ para la consola H2.
    - 📝 para la documentación con Swagger.
    - 🧪 y ⚗️ para las pruebas.
    - 🤝 para contribuciones.
    - 📄 para la licencia.

2. **Separadores visuales**: Se han incluido líneas horizontales `---` para separar visualmente las secciones.

Este formato visual hará que el `README.md` sea más atractivo y fácil de seguir, lo que puede ayudar a otros desarrolladores a entender y utilizar tu proyecto de manera más rápida.
```
