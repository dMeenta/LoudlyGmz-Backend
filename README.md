# LoudlyGmz-Backend

🚀 **LoudlyGmz-Backend** es una aplicación de backend desarrollada en Java, utilizando Maven como ecosistema. Se busca que el proyecto siga patrones MVC, DAO y permitiendo demostrar lo aprendido durante mi experiencia como programador backend. Haciendolo lo más escalable y robusto posible.

## 🧰 Tecnologías Utilizadas

- **Lenguaje de Programación:** Java
- **Gestor de Dependencias:** Maven
- **Estructura del Proyecto:** Basada en el estándar de Maven (`src/main/java`, `src/test/java`)
- **Control de Versiones:** Git

## 📁 Estructura del Proyecto

```
LoudlyGmz-Backend/
├── .mvn/                                            # Archivos de configuración de Maven Wrapper
├── src/
│   ├── main/
│   │   └── java/com/example/loudlygmz               # Código fuente principal
│   │                             └── config/        # Archivos de configuración (Firebase, CORS)
│   │                             └── controller/    # Endpoints
│   │                             └── DAO/           # Data Access Objects
│   │                             └── entity/        # Clases que conectan con Tablas de la BD
│   │                             └── seed/          # Inserción de datos en Games y Categories
│   │                             └── services/
│   │                             └── utils/         # Utilidades adicionales
│   └── test/
│       └── java/com/example/loudlygmz               # Pruebas unitarias
├── .gitignore                                       # Archivos y carpetas ignorados por Git
├── mvnw                                             # Script para ejecutar Maven Wrapper en Unix
├── mvnw.cmd                                         # Script para ejecutar Maven Wrapper en Windows
└── pom.xml                                          # Archivo de configuración de Maven
```

## 🚀 Instalación y Ejecución

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/dMeenta/LoudlyGmz-Backend.git
   cd LoudlyGmz-Backend
   ```

2. **Configuración del proyecto:**

   ### Obtener el `API_WEB_KEY` de firebase:

   1. Ve a [https://console.firebase.google.com/](https://console.firebase.google.com/)
   2. Selecciona tu proyecto o crea uno nuevo.
   3. Agregar Authentication (por correo y contraseña) + Firestore Database
   4. Dirigirse a la configuración del proyecto (ícono de engranaje).
   5. En la pestaña **General**, busca el campo **Clave de API Web**.
   6. Copia el valor mencionado en el archivo `launch.json` como `API_WEB_KEY` dentro de `"env"`.

   ### Obtener el serviceAccountKey.json:

   1. Desde la consola de Firebase, entra en **Configuración del proyecto**.
   2. Dirigete a la pestaña **Cuentas de servicio**.
   3. Haz clic en **Generar nueva clave privada**.
   4. Se descargará un archivo `.json`. Renómbralo como `secretsApiKey.json`.
   5. Coloca el archivo en la carpeta `resources`.

3. **Variables de entorno:**

   ### Cambiar las respectivas variables dependiendo de su sistema

4. **Ejecutar la aplicación:**

   > ⚠️ **IMPORTANTE:** Tener en cuenta que el proyecto generará automaticamente la base de datos al ser compilado (si encuentra una tabla dentro de la base de datos con información no procederá a generar las semillas)

## ✅ Pruebas

Para ejecutar las pruebas unitarias:

```bash
./mvnw test
```

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si deseas mejorar este proyecto, por favor sigue estos pasos:

1. **Fork** el repositorio.
2. Crea una nueva rama: `git checkout -b feature/nueva-funcionalidad`.
3. Realiza tus cambios y haz commit: `git commit -m 'Agrega nueva funcionalidad'`.
4. Sube tus cambios a tu fork: `git push origin feature/nueva-funcionalidad`.
5. Abre un **Pull Request** detallando tus cambios.

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## 📫 Contacto

Para preguntas o sugerencias, por favor contacta a [dMeenta](https://github.com/dMeenta).
