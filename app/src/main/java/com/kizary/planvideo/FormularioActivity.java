package com.kizary.planvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kizary.planvideo.databinding.ActivityFormularioBinding;

public class FormularioActivity extends AppCompatActivity {

    public static final String CLAVE_VIDEO = "VIDEO";

    private ActivityFormularioBinding binding;

    // Duración sugerida para cada tipo (mismo orden que el Spinner)
    private int[] duracionesSugeridas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFormularioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        duracionesSugeridas = getResources().getIntArray(R.array.duraciones_tipo);

        // Al elegir un tipo en el Spinner se rellena la duración sugerida
        binding.spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.txtDuracion.setText(String.valueOf(duracionesSugeridas[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.btnGuardar.setOnClickListener(v -> guardar());

        // Cancelar: se cierra sin devolver ningún vídeo
        binding.btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void guardar() {
        String titulo = binding.txtTitulo.getText().toString().trim();
        String textoDuracion = binding.txtDuracion.getText().toString().trim();
        String gancho = binding.txtGancho.getText().toString().trim();
        String tipo = binding.spTipo.getSelectedItem().toString();

        if (titulo.isEmpty()) {
            binding.txtTitulo.setError(getString(R.string.error_titulo));
            return;
        }
        if (textoDuracion.isEmpty() || Integer.parseInt(textoDuracion) <= 0) {
            binding.txtDuracion.setError(getString(R.string.error_duracion));
            return;
        }

        Video video = new Video(titulo, tipo, Integer.parseInt(textoDuracion), gancho);

        // Enviamos el objeto de vuelta a MainActivity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLAVE_VIDEO, video);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
