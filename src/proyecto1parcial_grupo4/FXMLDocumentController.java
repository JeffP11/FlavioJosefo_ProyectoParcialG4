/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1parcial_grupo4;

import Jugador.Soldado;
import ListaDoblementeEnlazada.CircularDoublyLinkedList;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Clase Controlador de la clase principal.
 *
 * @author Grupo 4
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXSlider numeroPersonas;

    @FXML
    private JFXSlider numeroDesfase;

    @FXML
    private JFXSlider posicionComienzo;

    @FXML
    private JFXSlider cambioVelocidad;

    @FXML
    private JFXButton izquierda;

    @FXML
    private JFXButton derecha;

    @FXML
    private JFXButton iniciar;

    @FXML
    private StackPane PanelJuego;

    @FXML
    private Label labelCantidadPersonas;

    @FXML
    private Label labelDesface;

    @FXML
    private Label labelPosicionComienza;

    @FXML
    private Label labelVelocidad;

    String direccion;//Variable asignada para la orientacion del giro de la matanza

    public Image imagen_soldado_dorado;
    public Image imagen_soldado_azul;
    public Image imagen_soldado_muerto;

    public int posComienzoPersona;//Variable asignada para la posicion donde comienza la matanza
    public int desfase;//Variable asignada para el salto por matanza
    public CircularDoublyLinkedList<Soldado> listaEnlazada;//Lista circular doblemente enlazada de tipo SOLDADO que almacena los solados del juego
    public int cantidadPersonas;//Variable que asigna la cantidad de soldados
    public int personasVivas;//Variable necesaria para saber hasta cuando debe ejecutarse el recorrido de la lista
    public static Thread hilo;//Para manejar los hilos de ejecucion
    public boolean suspender;//Variable que verifica si el juego esta pausado o no
    public static Runnable hiloControl;
    public ListIterator<Soldado> iter;//Iterator propio de la CircularDoublyLinkedList para recorrer la lista enlazada
    public int velocidad;//Velocidad de ejecucion de la matanza

    /**
     * Metodo que se ejecuta al presionar el boton Pausar
     */
    @FXML
    private void pausarEjecucion() {
        suspender = true;
    }

    /**
     * Metodo que se ejecuta al presionar el boton Reanudar
     */
    @FXML
    synchronized void reanudarEjecucion() {
        suspender = false;
        System.out.println("Reanudado.");
    }

    /**
     * Este metodo se ejecuta cuando el usuario elige todos los parametros y
     * esta listo para empezar la simulacion haciendo click en el boton Iniciar.
     */
    @FXML
    private void iniciarSimulacion(ActionEvent event) {
        numeroPersonas.setDisable(true);
        posicionComienzo.setDisable(true);
        numeroDesfase.setDisable(true);
        izquierda.setDisable(true);
        derecha.setDisable(true);
        iter = listaEnlazada.listIteratorNode(posComienzoPersona);
        hilo.start();
        iniciar.setDisable(true);

    }

    /**
     * Metodo que se ejecuta al presionar el boton Reinicar, seteando los
     * parametros por defecto de la simulacion.
     */
    @FXML
    private void reiniciarSimulacion() {
        try {
            suspender = false;
            cambioVelocidad.setValue(500);
            velocidad = ((int) Math.round((double) cambioVelocidad.getValue()));
            iniciar.setDisable(false);
            hilo = new Thread(hiloControl);
            direccion = "Izquierda";
            numeroPersonas.setDisable(false);
            posicionComienzo.setDisable(false);
            numeroDesfase.setDisable(false);
            izquierda.setDisable(false);
            derecha.setDisable(false);
            posicionComienzo.setValue(1);
            posComienzoPersona = ((int) Math.round((double) posicionComienzo.getValue())) - 1;
            PanelJuego.getChildren().clear();//Antes de llenar el area de juegos con "n" personas, elimino todos los elementos que estaban en el arreglo anterior para llenarlos de nuevo con "n" personas
            listaEnlazada = new CircularDoublyLinkedList<Soldado>();
            numeroPersonas.setValue(12);
            cantidadPersonas = ((int) Math.round((double) numeroPersonas.getValue()));
            desfase = 2;//Por defecto se inicia con desfae 2
            labelCantidadPersonas.setText("Cantidad de personas: " + cantidadPersonas);
            labelDesface.setText("Desfase: " + desfase);
            labelPosicionComienza.setText("Posición de la persona que comienza: " + (posComienzoPersona + 1));
            labelVelocidad.setText("Velocidad (ms): " + velocidad);
            llenarJuego(listaEnlazada, cantidadPersonas);//Lleno el area de juegos con la cantidad de personas elejidas por el usuario
            System.out.println(" ~ Reiniciado ~ ");
        } catch (Exception e) {
            System.out.println("\n");
        }
    }

    /**
     * Metodo que se encarga de iniciar el juego en lo que respecta a cada
     * matanza segun el giro y el desfase asignado.
     */
    private void comenzarEnDireccionIndicada(ListIterator<Soldado> iter, String direccion) {
        personasVivas = cantidadPersonas - 1;
        System.out.println(" ~ Iniciado ~ ");
        if (direccion.equals("Izquierda")) {
            try {
                while (iter.hasNext()) {
                    Soldado soldadoParticipante = iter.next();
                    Soldado muerto = null;
                    if (soldadoParticipante.isVivo()) {
                        int desfaseTemp = desfase - 1;
                        while (desfaseTemp > 0) {
                            muerto = iter.next();
                            desfaseTemp--;
                        }
                        listaEnlazada.remove(muerto);
                        muerto.setImagen(imagen_soldado_muerto);
                        personasVivas -= 1;
                    }
                    Thread.sleep(velocidad);
                    synchronized (this) {
                        while (suspender) {
                            System.out.println("Esperando...");
                            wait(500);
                        }
                    }
                    if (personasVivas == 0) {
                        break;
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (direccion.equals("Derecha")) {
            try {
                while (iter.hasPrevious()) {
                    Soldado soldadoParticipante = iter.previous();
                    Soldado muerto = null;
                    if (soldadoParticipante.isVivo()) {
                        int desfaseTemp = desfase - 1;
                        while (desfaseTemp > 0) {
                            muerto = iter.previous();
                            desfaseTemp--;
                        }
                        listaEnlazada.remove(muerto);
                        muerto.setImagen(imagen_soldado_muerto);
                        personasVivas -= 1;
                    }
                    Thread.sleep(velocidad);
                    synchronized (this) {
                        while (suspender) {
                            System.out.println("Esperando...");
                            wait(500);
                        }
                    }
                    if (personasVivas == 0) {
                        break;
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Metodo que se ejecuta al presionar el boton Izquierda o Derecha y setea
     * la variable direccion.
     */
    @FXML
    private void asignarDireccion(ActionEvent event) {
        iniciar.setDisable(false);
        izquierda.setDisable(false);
        derecha.setDisable(false);
        JFXButton bt = (JFXButton) event.getSource();
        if (bt.getText().equals("Derecha")) {
            direccion = "Derecha";
            bt.setDisable(true);
        } else if (bt.getText().equals("Izquierda")) {
            direccion = "Izquierda";
            bt.setDisable(true);
        }

    }

    /**
     * Este metodo llena de 'n' personas el area de juegos
     */
    private void llenarJuego(CircularDoublyLinkedList<Soldado> soldados, int valor) {
        for (int i = 0; i < valor; i++) {
            ImageView actual = new ImageView(imagen_soldado_azul);
            actual.setFitWidth(200);
            actual.setFitHeight(90);
            actual.setPreserveRatio(false);//No preservo el ratio porque se me deforma la imagen que ya fue trabajada en Illustrator
            actual.setTranslateX(250 * Math.cos((((360 / (double) valor) * Math.PI) / 180) * i));//calculo distancia en x
            actual.setTranslateY(250 * Math.sin((((360 / (double) valor) * Math.PI) / 180) * i));//calculo distancia en y
            if (i == posComienzoPersona) {//Esto es para que la persona 1 empiece
                actual.setImage(imagen_soldado_dorado);
            }
            actual.setRotate((360 / (double) valor) * i);
            Soldado soldadito = new Soldado(actual, true);
            soldados.addLast(soldadito);
        }

        ListIterator<Soldado> iter = soldados.listIterator();
        while (iter.hasNext()) {
            PanelJuego.getChildren().add(iter.next().getImagen());//Agrego cada Soldado al panel.
        }
    }

    /**
     * Metodo que inicializa las variables por defecto.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        suspender = false;
        velocidad = ((int) Math.round((double) cambioVelocidad.getValue()));
        direccion = "Izquierda";
        imagen_soldado_dorado = new Image("\\Recursos_Graficos\\peleador.png");
        imagen_soldado_azul = new Image("\\Recursos_Graficos\\peleador_normal.png");
        imagen_soldado_muerto = new Image("\\Recursos_Graficos\\peleador_muerto.png");

        posComienzoPersona = ((int) Math.round((double) posicionComienzo.getValue())) - 1;//Por defecto se comienza en 0
        desfase = 2;//Por defecto se inicia con desfae 2
        listaEnlazada = new CircularDoublyLinkedList<Soldado>();//Inicializo la lista enlazada
        cantidadPersonas = 12;
        llenarJuego(listaEnlazada, cantidadPersonas);//Por defecto se inicia con 12 personas el juego

        labelCantidadPersonas.setText("Cantidad de personas: " + cantidadPersonas);
        labelDesface.setText("Desfase: " + desfase);
        labelPosicionComienza.setText("Posición de la persona que comienza: " + (posComienzoPersona + 1));
        labelVelocidad.setText("Velocidad (ms): " + velocidad);
        cambioNumeroPersonas();
        cambioDesfase();
        cambioComienzoPosicionPersona();
        cambioSpeed();

        hiloControl = new Runnable() {
            @Override
            public void run() {
                comenzarEnDireccionIndicada(iter, direccion);
            }
        };
        hilo = new Thread(hiloControl);
    }

    /**
     * Metodo que se ejecuta al manipular el slider de cantidad de personas.
     */
    public void cambioNumeroPersonas() {
        numeroPersonas.valueProperty().addListener(new ChangeListener<Number>() {//Este metodo se ejecuta cada vez que se mueva el slider donde elijes el numero de personas

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                posicionComienzo.setValue(1);
                posComienzoPersona = ((int) Math.round((double) posicionComienzo.getValue())) - 1;
                PanelJuego.getChildren().clear();//Antes de llenar el area de juegos con "n" personas, elimino todos los elementos que estaban en el arreglo anterior para llenarlos de nuevo con "n" personas
                listaEnlazada = new CircularDoublyLinkedList<Soldado>();
                cantidadPersonas = (int) Math.round((double) newValue);//este valor sale del slider
                labelCantidadPersonas.setText("Cantidad de personas: " + cantidadPersonas);
                llenarJuego(listaEnlazada, cantidadPersonas);//Lleno el area de juegos con la cantidad de personas elejidas por el usuario
            }
        });
    }

    /**
     * Metodo que se ejecuta al manipular el slider de cambio de desfase.
     */
    public void cambioDesfase() {
        numeroDesfase.valueProperty().addListener(new ChangeListener<Number>() {//Este metodo se ejecuta cada vez que se mueva el slider

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                labelDesface.setText("Desfase: " + ((int) Math.round((double) newValue)));
                desfase = ((int) Math.round((double) newValue));
            }
        });
    }

    /**
     * Metodo que se ejecuta al manipular el slider de la posicion del soldado
     * que empezara la matanza.
     */
    public void cambioComienzoPosicionPersona() {
        posicionComienzo.valueProperty().addListener(new ChangeListener<Number>() {//Este metodo se ejecuta cada vez que se mueva el slider

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                listaEnlazada.get(posComienzoPersona).setImagen(imagen_soldado_azul);

                posComienzoPersona = ((int) Math.round((double) newValue)) - 1;//este valor sale del slider

                if (posComienzoPersona < listaEnlazada.size()) {
                    labelPosicionComienza.setText("Posición de la persona que comienza: " + (posComienzoPersona + 1));
                    listaEnlazada.get(posComienzoPersona).setImagen(imagen_soldado_dorado);

                } else {
                    posComienzoPersona = 0;
                    posicionComienzo.setValue(0);
                }

            }
        });
    }

    /**
     * Metodo que se ejecuta al manipular el slider de velocidad.
     */
    public void cambioSpeed() {
        cambioVelocidad.valueProperty().addListener(new ChangeListener<Number>() {//Este metodo se ejecuta cada vez que se mueva el slider

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                velocidad = ((int) Math.round((double) newValue));
                labelVelocidad.setText("Velocidad (ms): " + velocidad);
            }
        });
    }

}
