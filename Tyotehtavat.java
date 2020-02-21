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
 * Työtehtävät luokka, joka osaa mm. lisätä uuden työtehtävän.
 * @author Tommi
 * @version 19.4.2019
 */
public class Tyotehtavat {

    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    private int lkm;
    
    private final Collection<Tyotehtava> alkiot = new ArrayList<Tyotehtava>();  
    
    
    /**
     *  Tyotehtävien alustaminen, tässä vaiheessa luodaan esimerkeiksi muutama Tyotehtava
     */
    public Tyotehtavat() {
        
        Tyotehtava tyotehtava1 = new Tyotehtava("Maansiirto");
        tyotehtava1.rekisteroi();
        Tyotehtava tyotehtava2 = new Tyotehtava("Maanmuokkaus");
        tyotehtava2.rekisteroi();
        Tyotehtava tyotehtava3 = new Tyotehtava("Tavaroiden siirtely");
        tyotehtava3.rekisteroi();
        Tyotehtava tyotehtava4 = new Tyotehtava("Paaluttaminen");
        tyotehtava4.rekisteroi();
        lisaa(tyotehtava1);
        lisaa(tyotehtava2);
        lisaa(tyotehtava3);
        lisaa(tyotehtava4);
        
    }
    
    
    /**
     * Lisää uuden tyotehtavan tietorakenteeseen
     * @param tyotehtava lisättävä tyotehtava
     */
    public void lisaa(Tyotehtava tyotehtava) {
        
        alkiot.add(tyotehtava);
        lkm++;
        muutettu = true;
    
    }
    

    /**
     * @return tietorakenteen koko
     */
    public int getTehtLkm() {
        return lkm;
    }

    
    /**
     * @param id , jolla etsitään tietorakenteesta
     * @return palauttaa Tyotehtavan nimen 
     */
    public String annaTyotehtava(int id) {
        for (Tyotehtava tyotehtava : alkiot) {
            if (tyotehtava.getTunnusNro() == id) return tyotehtava.getTyotehtava();
        }
        return "";
    }
    
    
    /**
     * @param s , jolla etsitään
     * @return tehtävän id:n tai -1, jos ei löydy
     */
    public List<Integer> annaTyotehtava(String s) {
        List<Integer> tehtavat = new ArrayList<Integer>();
        for (Tyotehtava teht : alkiot) {
            if (teht.getTyotehtava().contains(s)) tehtavat.add(teht.getTunnusNro());
        }
        return tehtavat;
    }
    
    
    /**
     * Tallentaa tyotehtavat tiedostoon.  
     * Tiedoston muoto: dat
     * <pre>
     * 1     |  maansiirto  
     * 2     |  maanmuokkaus
     * 3     |  tavaroiden siirtely
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
            for (Tyotehtava tyotehtava : alkiot) {
                fo.println(tyotehtava.toString());
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
     * Lukee työtehtävät tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import rekisteri.Tyotehtava;
     *
     * Tyotehtavat tyotehtavat = new Tyotehtavat();
     * Tyotehtava tyotehtava1 = new Tyotehtava("Maansiirto"), tyotehtava2 = new Tyotehtava("Maanmuokkaus");
     * String hakemisto = "testityokoneet";
     * String tiedNimi = hakemisto+"/tyokoneet";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * tyotehtavat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * tyotehtavat.lisaa(tyotehtava1);
     * tyotehtavat.lisaa(tyotehtava2);
     * tyotehtavat.tallenna();
     * Tyotehtavat tyotehtavat2 = new Tyotehtavat();  
     * tyotehtavat2.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     * tyotehtavat.annaTyotehtava(0).toString().equals(tyotehtavat2.annaTyotehtava(0).toString()) === true;
     * tyotehtavat.annaTyotehtava(1).toString().equals(tyotehtavat2.annaTyotehtava(1).toString()) === true;
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
                Tyotehtava tyotehtava = new Tyotehtava();
                tyotehtava.parse(rivi); // voisi olla virhekäsittely
                lisaa(tyotehtava);
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
     * Testi pääohjelma, jolla katsotaan Tyotehtavat luokan toimintaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Tyotehtavat tyotehtavat = new Tyotehtavat();        
        
        System.out.println("================ Työtehtävien testi ===================");
        for (Tyotehtava teh : tyotehtavat.alkiot) {
            teh.tulosta(System.out);
        }
          
    }
    
}
