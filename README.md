¡Hola! Soy Miguel David Briceño Goncalves, el creador de **DevTrack Backend**. Este proyecto es el corazón de `DevTrack`, una aplicación diseñada para facilitar la gestión de proyectos, tareas y la colaboración en equipos. Mi objetivo principal al construir este backend fue crear una solución robusta, escalable y fácil de mantener.

Para lograrlo, elegí **Spring Boot** como mi framework principal, lo que me permitió desarrollar una API REST potente y eficiente. Además, apliqué una **arquitectura hexagonal**, una decisión de diseño crucial para asegurar una clara separación entre la lógica de negocio de la aplicación y sus dependencias externas. Esto se traduce en un código más limpio, más fácil de probar y mucho más sencillo de escalar a medida que el proyecto crece.

---

## Estructura del Proyecto

![image](https://github.com/user-attachments/assets/c579b543-cd1a-451b-9ee3-8e8365c26f75)

## Arbol

```
├───src
│   └───main
│       ├───java
│       │   └───com
│       │       └───devtrack
│       │           ├───auth
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───chat
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───completedby
│       │           │   ├───application
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───configuration
│       │           ├───email
│       │           │   ├───application
│       │           │   │   └───impl
│       │           │   └───domain
│       │           ├───notes
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───projects
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───tasks
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           ├───users
│       │           │   ├───application
│       │           │   │   ├───impl
│       │           │   │   └───mapper
│       │           │   │       ├───input
│       │           │   │       └───output
│       │           │   ├───domain
│       │           │   │   ├───entity
│       │           │   │   └───repository
│       │           │   └───infraestructure
│       │           │       └───controller
│       │           │           └───dto
│       │           │               ├───input
│       │           │               └───output
│       │           └───utils
│       │               ├───enums
│       │               └───exceptions
│       └───resources
│           ├───static
│           └───templates
└───target
    ├───classes
    │   └───com
    │       └───devtrack
    │           ├───auth
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───chat
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───completedby
    │           │   ├───application
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───configuration
    │           ├───email
    │           │   ├───application
    │           │   │   └───impl
    │           │   └───domain
    │           ├───notes
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───projects
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───tasks
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           ├───users
    │           │   ├───application
    │           │   │   ├───impl
    │           │   │   └───mapper
    │           │   │       ├───input
    │           │   │       └───output
    │           │   ├───domain
    │           │   │   ├───entity
    │           │   │   └───repository
    │           │   └───infraestructure
    │           │       └───controller
    │           │           └───dto
    │           │               ├───input
    │           │               └───output
    │           └───utils
    │               ├───enums
    │               └───exceptions
    ├───generated-sources
    │   └───annotations
    │       └───com
    │           └───devtrack
    │               ├───auth
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               ├───chat
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               ├───completedby
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               ├───notes
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               ├───projects
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               ├───tasks
    │               │   └───application
    │               │       └───mapper
    │               │           ├───input
    │               │           └───output
    │               └───users
    │                   └───application
    │                       └───mapper
    │                           ├───input
    │                           └───output
    ├───maven-archiver
    └───maven-status
        └───maven-compiler-plugin
            └───compile
                └───default-compile

```

* `.mvn/wrapper`: Contiene los scripts de **Maven Wrapper**, que son útiles para garantizar que cualquier persona que clone el proyecto pueda construirlo sin problemas, incluso si no tiene Maven instalado globalmente.
* `src/main`: Este es el directorio principal donde reside todo el **código fuente** de la aplicación Spring Boot. Aquí encontrarás la lógica de negocio, los controladores REST, la configuración, etc.
* `.gitattributes`: Un archivo de configuración de Git que ayuda a definir atributos por ruta.
* `.gitignore`: Fundamental para Git, este archivo especifica qué archivos y directorios deben ser ignorados por el control de versiones (por ejemplo, dependencias descargadas, archivos de configuración locales, etc.).
* `Dockerfile`: ¡Aquí es donde la magia de la contenerización sucede! Este archivo contiene las instrucciones para construir una **imagen Docker** de nuestra aplicación backend.
* `docker-compose.yml`: Este archivo es clave para la **orquestación de servicios**. Define cómo se levantan y conectan múltiples contenedores, como nuestra aplicación backend y, muy probablemente, la base de datos que utiliza.
* `mvnw`, `mvnw.cmd`: Son los ejecutables del Maven Wrapper para sistemas Unix/Linux y Windows, respectivamente. Te permiten ejecutar comandos Maven directamente sin necesidad de una instalación global.
* `pom.xml`: El corazón de cualquier proyecto Maven. Este archivo define todas las **dependencias** del proyecto, la versión de Java, la estructura de módulos y cómo se construye la aplicación.

---

## Tecnologías Utilizadas

Para este backend, seleccioné tecnologías que me ofrecieran estabilidad, rendimiento y facilidad de desarrollo:

* **Spring Boot**: Mi elección principal para construir APIs RESTful y manejar la lógica empresarial. Facilita enormemente el inicio y la configuración de aplicaciones Spring.
* **Java**: El lenguaje de programación sobre el que se construye todo el backend. Es robusto, maduro y muy utilizado en entornos empresariales.
* **Docker**: Esencial para la **contenerización**. Me permite empaquetar la aplicación y sus dependencias en un contenedor aislado, asegurando que funcione de la misma manera en cualquier entorno.

---

## Requisitos Previos

Para que puedas poner en marcha DevTrack Backend, necesitas lo siguiente en tu sistema:

* **Java Development Kit (JDK) 17+**: Necesario para compilar y ejecutar el código Java.
* **Docker Desktop**: Imprescindible si quieres aprovechar la facilidad de despliegue con contenedores. Incluye Docker Engine y Docker Compose.
* **Git**: Para clonar este repositorio.

---

## Cómo Ejecutar el Proyecto

Te guiaré paso a paso para que puedas levantar el backend de DevTrack usando Docker, que es la forma más sencilla y recomendada.

1.  **Clonar el repositorio:**
    Primero, abre tu terminal o línea de comandos y clona este repositorio en tu máquina local:
    ```bash
    git clone https://github.com/bricenocode/DevTrack_backend.git
    cd DevTrack_backend
    ```

2.  **Construir y Levantar con Docker Compose:**
    Una vez dentro del directorio `DevTrack_backend`, utiliza `docker-compose` para construir la imagen de la aplicación y levantar todos los servicios definidos (la aplicación backend y cualquier base de datos que configuremos en el `docker-compose.yml`).

    ```bash
    docker-compose up --build
    ```
    Este comando hará lo siguiente:
    * `--build`: Forzará la reconstrucción de la imagen Docker de la aplicación si hay cambios en el código o en el `Dockerfile`. Es bueno usarlo la primera vez o cuando haces cambios significativos.
    * **Construirá la imagen del backend**: Utilizará el `Dockerfile` para crear la imagen de tu aplicación Java.
    * **Levantará los servicios**: Iniciará el contenedor del backend y cualquier otro servicio (como MongoDB) que esté definido en el `docker-compose.yml`.

    Una vez que el proceso finalice, verás los logs de Spring Boot en tu terminal. Esto indica que la aplicación está corriendo.

3.  **Acceder a la API:**
    La API de DevTrack Backend debería estar accesible en el puerto por defecto de Spring Boot, que es `8080`. Puedes probarla abriendo tu navegador o una herramienta como Postman/Insomnia y navegando a:
    ```
    http://localhost:8080
    ```
    Si todo va bien, deberías ver una respuesta (quizás un error 404 si no accedes a un endpoint específico, lo cual es normal si no has definido un endpoint raíz).
