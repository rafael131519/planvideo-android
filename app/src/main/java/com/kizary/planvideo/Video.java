package com.kizary.planvideo;

import java.io.Serializable;

/**
 * Modelo de datos: representa una idea de vídeo para el canal.
 * Implementa Serializable para poder enviarse entre actividades dentro de un Bundle.
 */
public class Video implements Serializable {

    private String titulo;
    private String tipo;
    private int duracion; // en minutos
    private String gancho; // frase de los primeros segundos

    public Video(String titulo, String tipo, int duracion, String gancho) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.duracion = duracion;
        this.gancho = gancho;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public String getGancho() { return gancho; }
    public void setGancho(String gancho) { this.gancho = gancho; }
}
