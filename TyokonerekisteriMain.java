package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import rekisteri.*;


/**
 * Työkonerekisteri ohjelma, jolla tallenetaan eri koneiden tietoja.
 * @author Tommi Prusti
 * @version 27.3.2019
 */
public class TyokonerekisteriMain extends Application {
       
    /**
     * Ohjelma lataa pääikkunan tiedoston
     * @param primaryStage Ohjelma pääikkuna
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("TyokonerekisteriGUIView.fxml"));
            final Pane root = ldr.load();
            final TyokonerekisteriGUIController rekisteriCtrl = (TyokonerekisteriGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Työkoneet");
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !rekisteriCtrl.voikoSulkea() ) event.consume();
            });
            
            Rekisteri rekisteri = new Rekisteri();
            rekisteriCtrl.setRekisteri(rekisteri);
            
            primaryStage.show();
            
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                rekisteriCtrl.lueTiedosto(params.getRaw().get(0)); 
            else
                if ( !rekisteriCtrl.avaa() ) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ohjelma käynnistää käyttöliittymän
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}