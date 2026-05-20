# 🥖 Panadería Martínez - Sistema de Inventario

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple)](https://kotlinlang.org)
[![Firebase](https://img.shields.io/badge/Firebase-Latest-orange)](https://firebase.google.com)
[![Android](https://img.shields.io/badge/Android-Studio-green)](https://developer.android.com/studio)
[![License](https://img.shields.io/badge/License-MIT-blue)](#licencia)

---

## 📋 Descripción del Proyecto

**Panadería Martínez** es una aplicación Android nativa desarrollada en **Kotlin** que implementa un completo **sistema de gestión de inventario** para panaderías. La aplicación permite registrar, monitorear y controlar el stock de productos, facilitando la administración eficiente de la panadería.

### Propósito
- ✅ Gestionar inventario de productos en tiempo real
- ✅ Registrar entradas y salidas de productos
- ✅ Monitorear niveles de stock críticos
- ✅ Generar reportes de inventario
- ✅ Sincronizar datos en la nube de forma segura

---

## 🛠️ Tecnologías Utilizadas

### Lenguaje de Programación
- **Kotlin** (100%) - Lenguaje moderno, seguro y expresivo para Android

### Backend y Base de Datos
- **Firebase Realtime Database** - Base de datos NoSQL en tiempo real
- **Firebase Authentication** - Autenticación segura de usuarios
- **Firebase Cloud Storage** - Almacenamiento de archivos en la nube

### Herramienta de Desarrollo
- **Android Studio** - IDE oficial para desarrollo Android
- **Android SDK** - Kit de desarrollo de Android

### Arquitectura y Patrones
- **MVVM** (Model-View-ViewModel) - Patrón arquitectónico recomendado
- **Jetpack Components** - Librerías modernas de Android

---

## 📁 Estructura del Proyecto

```
Panaderia_Martinez/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/panaderia/martinez/
│   │   │   │       ├── models/           # Modelos de datos
│   │   │   │       ├── views/            # Activities y Fragments
│   │   │   │       ├── viewmodels/       # ViewModels (MVVM)
│   │   │   │       ├── repositories/     # Acceso a datos
│   │   │   │       ├── utils/            # Utilidades y extensiones
│   │   │   │       └── MainActivity.kt   # Actividad principal
│   │   │   ├── res/                      # Recursos (layouts, strings, colores)
│   │   │   │   ├── layout/               # Archivos XML de UI
│   │   │   │   ├── values/               # Recursos de cadenas y colores
│   │   │   │   └── drawable/             # Imágenes y vectores
│   │   │   └── AndroidManifest.xml       # Configuración de la aplicación
│   │   └── test/                         # Pruebas unitarias
│   └── build.gradle.kts                  # Dependencias del proyecto
└── README.md                              # Este archivo
```

---

## 🎯 Funcionalidades Principales

### 1. **Gestión de Productos**
   - Crear, actualizar y eliminar productos del inventario
   - Asignar categorías y precios
   - Visualizar listado completo de productos

### 2. **Control de Stock**
   - Registrar entradas de productos
   - Registrar salidas/ventas de productos
   - Visualizar cantidad disponible en tiempo real
   - Alertas de stock bajo

### 3. **Reportes y Estadísticas**
   - Generar reportes de movimiento de inventario
   - Estadísticas de ventas
   - Historial de transacciones

### 4. **Sincronización en Tiempo Real**
   - Datos sincronizados automáticamente con Firebase
   - Acceso desde múltiples dispositivos
   - Recuperación automática en caso de desconexión

### 5. **Autenticación de Usuarios**
   - Login seguro con correo electrónico
   - Registro de nuevos usuarios
   - Recuperación de contraseña

---

## 🚀 Instrucciones de Instalación y Ejecución

### Requisitos Previos
- **Android Studio** versión 2021.1 o superior
- **JDK 11** o superior
- **Kotlin** 1.9 o superior
- Emulador de Android o dispositivo físico (mínimo Android 8.0)
- Cuenta de Firebase activa

### Pasos de Instalación

#### 1. Clonar el Repositorio
```bash
git clone https://github.com/vc242100091-droid/Panaderia_Martinez.git
cd Panaderia_Martinez
```

#### 2. Configurar Firebase
- Acceder a [Firebase Console](https://console.firebase.google.com)
- Crear un nuevo proyecto o usar uno existente
- Registrar la aplicación Android
- Descargar el archivo `google-services.json`
- Colocar `google-services.json` en la carpeta `app/`

**Estructura esperada:**
```
Panaderia_Martinez/app/google-services.json
```

#### 3. Abrir en Android Studio
- Abrir Android Studio
- Seleccionar **File → Open** → Navegar a la carpeta del proyecto
- Android Studio sincronizará automáticamente las dependencias

#### 4. Configurar Emulador o Dispositivo
- **Emulador**: Usar Android Virtual Device Manager en Android Studio
- **Dispositivo físico**: Conectar por USB con depuración habilitada

#### 5. Ejecutar la Aplicación
```bash
# Opción 1: Desde Android Studio
- Seleccionar Build → Run 'app'
- O presionar Shift + F10

# Opción 2: Desde línea de comandos
./gradlew installDebug
```

### Verificación de la Instalación
✓ La aplicación debería iniciarse mostrando la pantalla de login
✓ Podría registrarse un nuevo usuario o usar credenciales existentes
✓ Acceder al panel principal de gestión de inventario

---

## 📖 Guía de Uso

### Primeros Pasos
1. **Registrarse** - Crear una nueva cuenta con correo electrónico
2. **Iniciar Sesión** - Acceder con las credenciales creadas
3. **Agregar Productos** - Comenzar a registrar productos
4. **Gestionar Inventario** - Registrar entradas/salidas

### Ejemplo de Uso Básico
```kotlin
// Crear un nuevo producto
val producto = Producto(
    nombre = "Pan Integral",
    cantidad = 50,
    precio = 2.50,
    categoria = "Panes"
)

// Guardar en Firebase
productRepository.guardarProducto(producto)
```

---

## 🔧 Configuración de Dependencias

Las dependencias principales se encuentran en `build.gradle.kts`:

```gradle
dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:latest"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    
    // Android Jetpack
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx")
    
    // UI
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
}
```

---

## 🐛 Solución de Problemas

### Problema: No se sincroniza con Firebase
**Solución**: 
- Verificar conexión a internet
- Confirmar que `google-services.json` está correctamente ubicado
- Revisar permisos en Firebase Console

### Problema: Errores de compilación
**Solución**:
- Ejecutar `./gradlew clean`
- Ir a **File → Invalidate Caches → Invalidate and Restart**
- Sincronizar Gradle nuevamente

### Problema: Aplicación se cierra al iniciar
**Solución**:
- Revisar logcat en Android Studio para errores
- Verificar que el emulador/dispositivo ejecuta Android 8.0 mínimo
- Confirmar que Firebase está correctamente configurado

---

## 📝 Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulte el archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Autor

**vc242100091-droid**
- GitHub: [@vc242100091-droid](https://github.com/vc242100091-droid)
- Repositorio: [Panadería Martínez](https://github.com/vc242100091-droid/Panaderia_Martinez)

**Fecha de Creación**: Mayo 2026

---

## 📞 Soporte y Contribuciones

¿Tienes preguntas o sugerencias? No dudes en:
- Crear un [issue](https://github.com/vc242100091-droid/Panaderia_Martinez/issues)
- Enviar un [pull request](https://github.com/vc242100091-droid/Panaderia_Martinez/pulls)

---

## 🙏 Agradecimientos

Gracias por utilizar **Panadería Martínez**. Esperamos que esta aplicación facilite la gestión de tu negocio.

---

**Última actualización**: Mayo 2026
