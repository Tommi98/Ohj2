package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rekisteri.*;


/**
 * Luokka Tyokonerekisterin k‰yttˆliittym‰n tapahtumien hoitamiseksi.
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class TyokonerekisteriGUIController implements Initializable {
    
    
    @FXML private TextField hakukentta;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelError;
    @FXML private ScrollPane panelKone;
    @FXML private ListChooser<Kone> chooserKoneet;
    @FXML private TextField editTyokone;
    @FXML private TextField editRek;
    @FXML private TextField editYtunnus;
    @FXML private TextField editPuhelin;
    @FXML private TextField editTehtava;
    @FXML private TextField editTila;
    @FXML private TextField editTunnit;
    @FXML private TextArea editlisatietoa;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();     
    }
    

    @FXML void handleTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleHakuehto() {
        hae(0);
    }
    
    
    @FXML void handleMuokkaa() {
        muokkaa();
    }
    
    
    @FXML void handleLisaa() {
        uusiKone();
    }
    

    @FXML void handlePoista() {
        poistaKone();
    }
    
    
    @FXML void handleApua() {
        help();
    }
    
    
    @FXML void handleAvaa() {
        tallenna();
        avaa();
       
        if (rekisteri.getKoneita() < 1) tyhjennaKentat();        
    }
    

    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }


// ==============================================================================

    
    private Rekisteri rekisteri;
    private KoneDialogController muok = new KoneDialogController();
    private Kone koneKohdalla;
    private String rekisterinimi = "Tyomaa";
    private TextField[] kentat;
    private static Kone apukone = new Kone();
    
    
    /**
    * Tekee tarvittavat muut alustukset
    */
    protected void alusta() {
        
        panelKone.setFitToHeight(true);
        chooserKoneet.clear();
        chooserKoneet.addSelectionListener(e -> naytaKone());
        
        cbKentat.clear(); 
        for (int k = apukone.ekaKentta(); k < apukone.getKenttia(); k++) 
            cbKentat.add(apukone.getKysymys(k), null); 
        cbKentat.getSelectionModel().select(0);
        
        kentat = new TextField[] {editTyokone, editRek, editYtunnus, editPuhelin, editTehtava, editTila, editTunnit};
        for (int i = 0; i < kentat.length; i++) {
            kentat[i].setOnMouseClicked(e -> muokkaa());
        }
        editlisatietoa.setOnMouseClicked(e -> muokkaa());
        
    }
    
    
   /**
    * N‰ytt‰‰ listasta valitun koneen tiedot
    */
    private void naytaKone() {
    koneKohdalla = chooserKoneet.getSelectedObject();
    if (koneKohdalla == null) return;
    KoneDialogController.naytaKone(kentat, koneKohdalla);
    editlisatietoa.setText(koneKohdalla.getLisatieto());
    
    
    }
    
    
    /**
     * Hoitaa koneen muokkaamisen viem‰ll‰ uuteen dialogiin kloonin olioista, jota sitten k‰ytt‰j‰ p‰‰see muokkaamaan
     */
    private void muokkaa() {
       if (koneKohdalla == null) return;
       muok.setRekisteri(rekisteri);
       try {
           Kone kone = muok.kysyKone(null, koneKohdalla.clone());
           if ( kone == null ) return;
           rekisteri.korvaaTaiLisaa(kone);
           hae(kone.getTunnusNro());
       } catch( CloneNotSupportedException e ) {
           //
       } catch( SailoException e ) {
           Dialogs.showMessageDialog(e.getMessage());
       }
      
    }
    
    
    /**
    * Luo uuden koneen jota aletaan editoimaan 
    */
     private void uusiKone() {
     try {
        Kone uusi = new Kone();
        uusi = muok.kysyKone(null, uusi);  
        if ( uusi == null ) return;
        if ( uusi.getTyokone() < 1) return;
        uusi.rekisteroi();
        rekisteri.lisaa(uusi);
        hae(uusi.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden koneen luomisessa " + e.getMessage());
            return;
        }
        
    }
    
    
    /**                                                                 
    * Hakee koneen tiedot listaan                                       
    * @param knro koneen numero, joka aktivoidaan haun j‰lkeen          
    */                                                                  
    private void hae(int knr) {
        int knro = knr; 
        List<Integer> ehdot = new ArrayList<Integer>();
        if ( knro <= 0 ) { 
            Kone kohdalla = koneKohdalla; 
            if ( kohdalla != null ) knro = kohdalla.getTunnusNro(); 
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apukone.ekaKentta(); 
        String ehto = hakukentta.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        if ( k == 1)  {
            if (ehto.length() <= 2) ehdot = rekisteri.annaTyokone("");
            if (ehto.length() > 2) ehdot = rekisteri.annaTyokone(ehto.substring(1,ehto.length()-1));
        }
        if ( k == 5) {
            if (ehto.length() <= 2) ehdot = rekisteri.annaTyotehtava("");
            if (ehto.length() > 2) ehdot = rekisteri.annaTyotehtava(ehto.substring(1,ehto.length()-1));
        }
        if ( k == 6) {
            if (ehto.length() <= 2) ehdot = rekisteri.annaTila("");
            if (ehto.length() > 2) ehdot = rekisteri.annaTila(ehto.substring(1,ehto.length()-1));
        }
        
        chooserKoneet.clear();
        
        int index = 0;
        try {
            if ((k == 1 || k == 5) || k == 6) {
            for (Integer e : ehdot) {
            ehto = Integer.toString(e);
            var koneet = rekisteri.etsi(ehto, k);
            int i = 0;
            for (Kone kone : koneet) {
                if (kone.getTunnusNro() == knro) index = i;
                chooserKoneet.add(rekisteri.annaTyokone(kone.getTyokone()), kone);
                i++;
            }
            }
            }
            else  {
                var koneet = rekisteri.etsi(ehto, k);
                int i = 0;
                for (Kone kone : koneet) {
                    if (kone.getTunnusNro() == knro) index = i;
                    chooserKoneet.add(rekisteri.annaTyokone(kone.getTyokone()), kone);
                    i++;
                }
            }           
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Koneen hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserKoneet.setSelectedIndex(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ j‰senen
    }
    
    
    /**
    * Tallentaa rekisterin tiedot tiedostoon
    */
    private String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
    * Avaa ohjelman suunnitelman erillisess‰ selaimessa.
    */
    private void help() {
         Desktop desktop = Desktop.getDesktop();
         try {
             URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/prusttxy");
             desktop.browse(uri);
         } catch (URISyntaxException e) {
             return;
         } catch (IOException e) {
             return;
         }
     }
    
    
    /**
     * Asetetaan oma viite k‰ytett‰v‰‰n rekisteriin, sek‰ vied‰‰n toiselle controlleri oliolle viite rekisteriin
     * @param rek k‰ytett‰v‰ rekisteri
     */
    public void setRekisteri(Rekisteri rek) {
        rekisteri = rek;
        muok.setRekisteri(rekisteri);
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /*
     * Poistetaan listalta valittu kone
     */
    private void poistaKone() {
        
        Kone kone = koneKohdalla;
        if ( kone == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko: " + rekisteri.annaTyokone(kone.getTyokone()) + ", Rekisterinumero: " + kone.getReknro(), "Kyll‰", "Ei") ) return;
        rekisteri.poista(kone);
        int index = chooserKoneet.getSelectedIndex();
        hae(0);
        chooserKoneet.setSelectedIndex(index);
        if (rekisteri.getKoneita() < 1) tyhjennaKentat();
        
    }
    
    
    /**
     * Tyhjent‰‰ p‰‰ikkunan teskti kent‰t
     */
    public void tyhjennaKentat() {
        for (TextField kentta : kentat) {
            kentta.clear();
        }
        editlisatietoa.clear();
    }

    
    /**
    * Alustaa rekisterin lukemalla sen valitun nimisest‰ tiedostosta
    * @param tiedoston_nimi tiedosto josta rekisterin tiedot luetaan
    * @return null jos onnistuu, muuten virhe tekstin‰
    */
    protected String lueTiedosto(String tiedoston_nimi) {
       rekisterinimi = tiedoston_nimi;
       try {
           rekisteri.lueTiedostosta(tiedoston_nimi);
           hae(0);
           return null;
       } catch (SailoException e) {
           hae(0);
           String virhe = e.getMessage(); 
           if ( virhe != null ) { 
               Dialogs.showMessageDialog(virhe);
           }
           return virhe;
       }
       
    }
    
    
    /**
     * Kysyt‰‰n tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = TyomaaNimiController.kysyNimi(null, rekisterinimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    

}