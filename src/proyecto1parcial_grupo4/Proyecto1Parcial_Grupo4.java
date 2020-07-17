/*
 * Proyecto primer parcial "EL PROBLEMA DE FLAVIO JOSEFO"
 * GRUPO 4
 */
package proyecto1parcial_grupo4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 * @author Grupo 4
 */
public class Proyecto1Parcial_Grupo4 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("\\Recursos_Graficos\\warrior1.png"));
        stage.setTitle("PROBLEMA DE FLAVIO JOSEFO ~ GRUPO 4");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        launch(args);
    }   
}
