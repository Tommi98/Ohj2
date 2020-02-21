/**
 * 
 */
package rekisteri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Tilat luokka, joka osaa lisätä mm. uuden tilan
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Tilat {
    
    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    private int lkm;
    
    private final Collection<Tila> alkiot = new ArrayList<Tila>();  
    
    
    /**
     *  Tilojen alustaminen, tässä vaiheessa luodaan esimerkeiksi muutama tila
     */
    public Tilat() {
        Tila tila1 = new Tila("Töissä");
        tila1.rekisteroi();
        Tila tila2 = new Tila("Poissa");
        tila2.rekisteroi();
        Tila tila3 = new Tila("Huollossa");
        tila3.rekisteroi();
        lisaa(tila1);
        lisaa(tila2);
        lisaa(tila3);
   
    }
    
    
    /**
     * Lisää uuden tilan tietorakenteeseen
     * @param tila lisättävä tila
     */
    public void lisaa(Tila tila) {
        
        alkiot.add(tila);
        lkm++;
        muutettu = true;
    
    }
    
    
    /**
     * Etsitään tietorakenteesta tilan nimi
     * @param id , jolla etsitään tietorakenteesta
     * @return palauttaa tilan nimen 
     */
    public String annaTila(int id) {
        
        for (Tila tila : alkiot) {
            if (tila.getTunnusNro() == id) return tila.getTila();   
        }
        
        return "";
    
    }
    
    
    
    /**
     * @param s , jolla etsitöön
     * @return tilan id:t tietorakenteessa, jotka sisältävät s
     */
    public List<Integer> annaTila(String s) {
        List<Integer> tilat = new ArrayList<Integer>();
        for (Tila tila : alkiot) {
            if (tila.getTila().contains(s)) tilat.add(tila.getTunnusNro());
        }
        return tilat;
    }
    
    
    /**
     * Tallentaa tilat tiedostoon.  
     * Tiedoston muoto: dat
     * <pre>
     * 1   |  töissä  
     * 2   |  poissa
     * 3   |  huollossa
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
    
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");
    
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath(), false))) {
            for (Tila tila : alkiot) {
                fo.println(tila.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
            muutettu = false;
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    
    
    /**
     * Lukee tilat tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import rekisteri.Tila;
     *
     * Tilat tilat = new Tilat();
     * Tila tila1 = new Tila("Töissä"), tila2 = new Tila("Poissa");
     * String hakemisto = "testitilat";
     * String tiedNimi = hakemisto+"/tilat";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * tilat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * tilat.lisaa(tila1);
     * tilat.lisaa(tila2);
     * tilat.tallenna();
     * Tilat tilat2 = new Tilat();  
     * tilat2.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     * tilat.annaTila(0).toString().equals(tilat2.annaTila(0).toString()) === true;
     * tilat.annaTila(1).toString().equals(tilat2.annaTila(1).toString()) === true;
     * ftied.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
     public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (Scanner fi = new Scanner(new File(getTiedostonNimi()))) {
            alkiot.clear();
            String rivi;   
            while ( fi.hasNext()) {
                rivi = fi.nextLine().trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Tila tila = new Tila();
                tila.parse(rivi); // voisi olla virhekäsittely
                lisaa(tila);
            }
        }
        catch (FileNotFoundException ex) {           
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        }
            
     }

    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Asettaa tiedoston perusnimen
     * @param tie tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tie) {
        tiedostonPerusNimi = tie;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**                                          
     * Palauttaa tiedoston nimen, jota käytetään 
     * @return tallennustiedoston nimi           
     */                                          
    public String getTiedostonNimi() {           
        return tiedostonPerusNimi + ".dat";      
    }                                            
                                                 
                                                 
    /**                                          
     * Palauttaa varakopiotiedoston nimen        
     * @return varakopiotiedoston nimi           
     */                                          
    public String getBakNimi() {                 
        return tiedostonPerusNimi + ".bak";      
    }                                            
    
    
    /**
     * Testi pääohjelma, jolla testataan tilat luokan toimintaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Tilat tilat = new Tilat();
        
        System.out.println("================ Tilojen testi ===================");
        
        for (Tila t : tilat.alkiot) {
            t.tulosta(System.out);
        }
          
    }


    /**
     * @return tietorankenteen koko
     */
    public int getTilatLkm() {
        return lkm;
    }

}
