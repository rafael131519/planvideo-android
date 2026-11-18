package com.kizary.planvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kizary.planvideo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Colección de objetos del modelo (se guarda en memoria mientras la app está abierta)
    private ArrayList<Video> listaVideos;

    // Launcher que abre el formulario y recibe el Video que devuelve
    private ActivityResultLauncher<Intent> launcherNuevoVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaVideos = new ArrayList<>();
        inicializarLauncher();

        binding.btnNuevoVideo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            launcherNuevoVideo.launch(intent);
        });

        mostrarVideos(); // estado inicial: lista vacía
    }

    private void inicializarLauncher() {
        launcherNuevoVideo = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Solo se añade si el usuario pulsó Guardar (RESULT_OK).
                    // Si canceló o pulsó atrás llega RESULT_CANCELED y no se hace nada.
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        if (bundle != null) {
                            Video video = (Video) bundle.getSerializable(FormularioActivity.CLAVE_VIDEO);
                            if (video != null) {
                                listaVideos.add(video);
                                mostrarVideos();
                            }
                        }
                    }
                });
    }

    /**
     * Recorre la colección, infla item_video.xml para cada vídeo
     * y lo añade al LinearLayout que está dentro del ScrollView.
     */
    private void mostrarVideos() {
        // Vaciamos el contenedor antes de pintar para evitar duplicados
        binding.contenedorVideos.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        int minutosTotales = 0;

        for (Video video : listaVideos) {
            View videoView = layoutInflater.inflate(R.layout.item_video, null);

            TextView lbTipo = videoView.findViewById(R.id.lbTipoVideoView);
            TextView lbTitulo = videoView.findViewById(R.id.lbTituloVideoView);
            TextView lbDuracion = videoView.findViewById(R.id.lbDuracionVideoView);
            TextView lbGancho = videoView.findViewById(R.id.lbGanchoVideoView);
            View barraColor = videoView.findViewById(R.id.barraColorVideoView);

            lbTipo.setText(video.getTipo());
            lbTitulo.setText(video.getTitulo());
            lbDuracion.setText(getString(R.string.formato_duracion, video.getDuracion()));

            if (video.getGancho().isEmpty()) {
                lbGancho.setVisibility(View.GONE);
            } else {
                lbGancho.setText(getString(R.string.formato_gancho, video.getGancho()));
            }

            // El tipo elegido en el Spinner decide el color de la ficha
            int color = ContextCompat.getColor(this, colorPorTipo(video.getTipo()));
            barraColor.setBackgroundColor(color);
            lbTipo.setTextColor(color);

            binding.contenedorVideos.addView(videoView);
            minutosTotales += video.getDuracion();
        }

        binding.lbResumen.setText(getString(R.string.formato_resumen, listaVideos.size(), minutosTotales));
        binding.lbVacio.setVisibility(listaVideos.isEmpty() ? View.VISIBLE : View.GONE);
    }

    /** Devuelve el color asociado a cada opción del Spinner (mismo orden que tipos_video). */
    private int colorPorTipo(String tipo) {
        List<String> tipos = Arrays.asList(getResources().getStringArray(R.array.tipos_video));
        switch (tipos.indexOf(tipo)) {
            case 0: return R.color.tipo_analisis;
            case 1: return R.color.tipo_guia;
            case 2: return R.color.tipo_top;
            case 3: return R.color.tipo_short;
            default: return R.color.tipo_analisis;
        }
    }
}
