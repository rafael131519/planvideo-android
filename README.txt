PLANIFICADOR DE VÍDEOS (PlanVideo)
Autor: Rafael Izquierdo
Proyecto 1ª Evaluación - Desarrollo de una aplicación Android (Java + XML)

------------------------------------------------------------
QUÉ PERMITE HACER Y CÓMO SE UTILIZA
------------------------------------------------------------
Aplicación para planificar las ideas de vídeo de un canal de YouTube de videojuegos.
1. En la pantalla principal se ve la lista de vídeos planificados y un resumen
   con el número de vídeos y los minutos totales.
2. Al pulsar "Nuevo vídeo" se abre un formulario: título, tipo de vídeo (Spinner),
   duración en minutos y gancho de los primeros segundos (opcional).
3. Al elegir el tipo en el Spinner se rellena automáticamente una duración sugerida
   (Análisis 15, Guía 20, Top 12, Short 1), que el usuario puede modificar.
4. "Guardar" devuelve el vídeo a la pantalla principal, donde aparece como una ficha
   con el color de su tipo. "Cancelar" o el botón atrás vuelven sin añadir nada.

------------------------------------------------------------
DÓNDE SE CUMPLE CADA REQUISITO TÉCNICO
------------------------------------------------------------
1. Dos actividades: MainActivity (lista y resumen) y FormularioActivity (alta de vídeo).
   Registradas en AndroidManifest.xml. Diseños activity_main.xml y activity_formulario.xml.
2. Controles: EditText txtTitulo, txtDuracion y txtGancho; botones btnNuevoVideo,
   btnGuardar y btnCancelar; Spinner spTipo. El tipo elegido en el Spinner fija la
   duración sugerida (FormularioActivity, onItemSelected) y el color de la ficha
   (MainActivity, colorPorTipo).
3. Modelo propio: clase Video (titulo, tipo, duracion, gancho), con constructor,
   getters, setters e implements Serializable.
4. Intercambio de datos: MainActivity usa un ActivityResultLauncher
   (launcherNuevoVideo) para abrir FormularioActivity. Al guardar, el formulario
   devuelve el Video en un Bundle con putSerializable y setResult(RESULT_OK).
   Al cancelar o pulsar atrás se devuelve RESULT_CANCELED y MainActivity no añade nada.
5. Colección: ArrayList<Video> listaVideos en MainActivity; se añade un elemento
   cada vez que se guarda un vídeo.
6. Vistas dinámicas: item_video.xml es el diseño reutilizable de una ficha.
   El método mostrarVideos() hace removeAllViews() para evitar duplicados, recorre
   la colección, infla item_video.xml con LayoutInflater, rellena sus TextView y
   añade cada vista al LinearLayout contenedorVideos, que está dentro de un ScrollView.
7. Recursos de texto: todos los textos fijos y las opciones del Spinner
   (string-array tipos_video) están en res/values/strings.xml.

------------------------------------------------------------
COMPROBACIONES DE FUNCIONAMIENTO
------------------------------------------------------------
1. Estado inicial: la app abre con el mensaje "Aún no hay vídeos planificados"
   y el resumen "0 vídeos · 0 min en total". Resultado: correcto.
2. Incorporar tres objetos: se añaden un Análisis (15 min), una Guía (20 min)
   y un Short (1 min). Aparecen 3 fichas con colores azul, verde y rojo y el
   resumen muestra "3 vídeos · 36 min en total". Resultado: correcto.
3. Cancelar: se rellena el formulario y se pulsa "Cancelar"; después se repite
   pulsando el botón atrás. En ambos casos siguen apareciendo 3 fichas y el
   resumen no cambia. Resultado: correcto.
4. Navegación y actualización: se añade un cuarto vídeo (Top, 12 min). Aparecen
   4 fichas, se conservan las 3 anteriores sin duplicarse y el resumen pasa a
   "4 vídeos · 48 min en total". Resultado: correcto.
