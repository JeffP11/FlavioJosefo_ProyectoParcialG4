/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugador;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Clase Soldado que tiene como parametros la imagen correspondiente y una variable booleana que indica si
 * esta vivo o muerto.
 * @author Grupo 4
 */
public class Soldado {
    private ImageView imagen;//Guarda la imagen del soldado
    private boolean vivo;//indica si el soldado esta vivo (true) o muerto(false)

    public Soldado(ImageView imagen, boolean vivo) {
        this.imagen = imagen;
        this.vivo = vivo;
    }
    
    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen.setImage(imagen);
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    
    
}
