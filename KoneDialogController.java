/**
 * 
 */
package application;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rekisteri.*;

/**
 * Dialogin controlleri
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class KoneDialogController implements ModalControllerInterface<Kone>, Initializable {

    
    @FXML private TextField editTyokone;
    @FXML private TextField editRek;
    @FXML private TextField editYtunnus;
    @FXML private TextField editPuhelin;
    @FXML private TextField editTehtava;
    @FXML private TextField editTila;
    @FXML private TextField editTunnit;
    @FXML private TextArea editlisatietoa;
    @FXML private Label labelVirhe;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();       
    }
    
    
    @FXML private void handleOK() {
        if ( koneKohdalla != null ) {
            for (TextField kentta : edit) {
                if(kentta.getText().length() == 0) {
                    naytaVirhe("Ei tyhji‰ kentti‰");
                    return;
                }
            }    
        }
        
        try {
            onkoUusia();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Uusien lis‰‰misess‰ oli ongelmia!" + e.getMessage());
        }    
                
        ModalController.closeStage(labelVirhe);
    }
    
    
    @FXML private void handleCancel() {
        koneKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
   
    
   //=====================================================================================================    

    
    private Kone koneKohdalla;
    private static Rekisteri rek;
    private TextField[] edit;
    
    /**
     * @param rekisteri Rekisteri olio
     */
    public void setRekisteri(Rekisteri rekisteri) {
        KoneDialogController.rek = rekisteri;
    }
    
    
    private void naytaVirhe(String virhe) {
    
        if ( virhe == null || virhe.isEmpty() ) {
             labelVirhe.setText("");
             labelVirhe.getStyleClass().removeAll("virhe");
             return;
        }
        
         labelVirhe.setText(virhe);
         labelVirhe.getStyleClass().add("virhe");
    }
    
    
    /**
    * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentist‰ tulevan
    * tapahtuman menem‰‰n kasitteleMuutosKoneeseen-metodiin ja vie sille
    * kent‰nnumeron parametrina.
    */
    protected void alusta() {
       edit = new TextField[] {editTyokone, editRek, editYtunnus, editPuhelin, editTehtava, editTila, editTunnit};
       int i = 0;
       for (TextField kentta : edit) {
           final int k = ++i;
           kentta.setOnKeyReleased( e -> kasitteleMuutosKoneeseen(k, (TextField)(e.getSource())));
       }
       editlisatietoa.setOnKeyReleased( e -> kasitteleLisatieto());
    }
    
    
    /**
     * Dialogin sulkemisen j‰lkeen takaisin p‰‰ikkunalle palautetettava olio
     */
    @Override
    public Kone getResult() {
        return koneKohdalla;
    }

    
    /**
    * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
    */
    @Override
    public void handleShown() {
        editTyokone.requestFocus();
        
    }

    
    @Override
    public void setDefault(Kone oletus) {
        koneKohdalla = oletus;
        naytaKone(koneKohdalla);
        
    }
    
    
    /**
     *  Tarkastaa onko k‰ytt‰j‰ luonout uusia olioita, jotka tulisi lis‰t‰ tietorakenteisiin
     *   @throws SailoException jos virheit‰ uusien lis‰‰misess‰
     */
    private void onkoUusia() throws SailoException {
        
        if (!editTyokone.getText().contentEquals(rek.annaTyokone(koneKohdalla.getTyokone()))) {
            Tyokone uusi = new Tyokone(editTyokone.getText());
            uusi.rekisteroi();
            try {
            rek.lisaa(uusi);
            } catch (SailoException e) {
                throw new SailoException("Virhe uuden tyˆkoneen lis‰‰misess‰");
            }
            koneKohdalla.setTyokone(uusi.getTunnusNro());
        }
        
        if(!editTehtava.getText().contentEquals(rek.annaTyotehtava(koneKohdalla.getTyotehtava()))) {
            Tyotehtava uusi = new Tyotehtava(editTehtava.getText());
            uusi.rekisteroi();
            try {
                rek.lisaa(uusi);
            } catch (SailoException e) {
                throw new SailoException("Virhe uuden tyˆteht‰v‰n lis‰‰misess‰");
            }
            koneKohdalla.setTyotehtava(uusi.getTunnusNro());
        }
        
        if(!editTila.getText().contentEquals(rek.annaTila(koneKohdalla.getTila()))) {
            Tila uusi = new Tila(editTila.getText());
            uusi.rekisteroi();
            try {
                rek.lisaa(uusi);
            } catch (SailoException e) {
                throw new SailoException("Virhe uuden tilan lis‰‰misess‰");
            }
            koneKohdalla.setTila(uusi.getTunnusNro());
        }
        
    }
    
    
    /**
     * N‰ytet‰‰n koneen tiedot TextFieldeiss‰
     * @param edits taulukko, jossa tekstikent‰t
     * @param kone n‰ytett‰v‰ kone
     */
    public static void naytaKone(TextField[] edits, Kone kone) {
        if (kone == null) return;
        edits[0].setText(rek.annaTyokone(kone.getTyokone())); 
        edits[1].setText(kone.getReknro());
        edits[2].setText(kone.getYtunnus());
        edits[3].setText(kone.getPuhnro());
        edits[4].setText(rek.annaTyotehtava(kone.getTyotehtava()));
        edits[5].setText(rek.annaTila(kone.getTila()));
        edits[6].setText(kone.getTunnit());
        
    }
    
    
    /**
     * K‰sitell‰‰n koneeseen tullut muutos
     * @param kentta on muuttunut kentt‰
     * @throws SailoException 
     */
    private void kasitteleMuutosKoneeseen(int k, TextField kentta) {
        
        if (koneKohdalla == null) return;
        String s = kentta.getText();
        String virhe = null;
        int d;
        switch (k) {
           case 1 : 
               d = rek.getTyokoneetLkm();
               for (int i = 1; i < d+1; i++) {
                   if (rek.annaTyokone(i).contentEquals(kentta.getText())) {
                       virhe = koneKohdalla.setTyokone(i);
                   }
               } break;
                                      
           case 2 : virhe = koneKohdalla.setReknro(s); break;
           
           case 3 : virhe = koneKohdalla.setYtunnus(s); break;
           
           case 4 : virhe = koneKohdalla.setPuhnro(s); break;
           
           case 5 : 
              d = rek.getTehtLkm();                                                   
              for (int i = 1; i < d+1; i++) {                                           
                  if (rek.annaTyotehtava(i).equals(kentta.getText())) { 
                      virhe = koneKohdalla.setTyotehtava(i);                                                                    
                  } 
              } break;                                                                     
                                                                                      
               
           case 6 :
               d = rek.getTilatLkm();
               for (int i = 0; i < d+1; i++) {
                   if (rek.annaTila(i).equals(kentta.getText())) {
                       virhe = koneKohdalla.setTila(i);
                   }
               } break;
           
           case 7 : virhe = koneKohdalla.setTunnit(s); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(kentta,"");
            kentta.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(kentta,virhe);
            kentta.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
        
    }
    
    
    /**
     * K‰sittelee koneen lis‰tietojen muokkaukset.
     */
    private void kasitteleLisatieto() {
       koneKohdalla.setLisatieto(editlisatietoa.getText());
    }
    
    
    /**
     * N‰ytt‰‰ koneen tiedot 
     * @param kone , jonka tiedot halutaan n‰ytt‰‰
     */
    public void naytaKone(Kone kone) {
        naytaKone(edit, kone);
        editlisatietoa.setText(kone.getLisatieto());
    }
    
    
    /**
     * Luodaan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @return null jos painetaan Peruuta, muuten t‰ytetty tietue
     */
    public Kone kysyKone(Stage modalityStage, Kone oletus) {
        return ModalController.showModal(KoneDialogController.class.getResource("KoneDialogView.fxml"), "Rekisteri", modalityStage, oletus, null);
    }

   

}
