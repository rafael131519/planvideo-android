# PlanVideo — App Android para planificar vídeos de YouTube

Aplicación **Android** en **Java + XML** para planificar las ideas de vídeo de un canal de videojuegos: lista de vídeos con resumen de minutos totales y formulario de alta con tipo de vídeo, duración y gancho inicial.

## Funcionalidad
- Pantalla principal con la lista de vídeos planificados y el resumen «N vídeos · N min en total».
- Formulario «Nuevo vídeo»: título, tipo (Spinner), duración y gancho de los primeros segundos.
- Al elegir el tipo se sugiere una duración (Análisis 15, Guía 20, Top 12, Short 1) y cada ficha toma el color de su tipo.

## Aspectos técnicos
- **2 actividades** (`MainActivity`, `FormularioActivity`) registradas en `AndroidManifest.xml`.
- Modelo propio `Video` (`Serializable`) con constructor, getters y setters.
- Paso de datos entre actividades con **ActivityResultLauncher** y `Bundle.putSerializable`; gestión de `RESULT_OK` / `RESULT_CANCELED`.
- Colección `ArrayList<Video>` y **vistas dinámicas**: `item_video.xml` inflado con `LayoutInflater` dentro de un `ScrollView`.
- Todos los textos y el `string-array` del Spinner en `res/values/strings.xml`.
- minSdk 24 · AndroidX AppCompat · Material Components · ConstraintLayout · Gradle (Kotlin DSL).

Pruebas de funcionamiento y detalle de requisitos en [`README.txt`](README.txt).

---
Rafael Izquierdo · Proyecto de DAM (Desarrollo de Aplicaciones Multiplataforma)
