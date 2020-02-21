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
 * Tyokone luokka, joka osaa mm. lisätä uuden koneen
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Tyokoneet {

    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    
    private final Collection<Tyokone> alkiot = new ArrayList<Tyokone>();  
    
    
    /**
     *  Tyokoneiden alustaminen, tässä vaiheessa luodaan esimerkeiksi muutama Tyokone
     */
    public Tyokoneet() {
        
        Tyokone tyokone1 = new Tyokone("Kuorma-auto");
        tyokone1.rekisteroi();
        Tyokone tyokone2 = new Tyokone("Pyöräkuormaaja");
        tyokone2.rekisteroi();
        Tyokone tyokone3 = new Tyokone("Kaivinkone");
        tyokone3.rekisteroi();
        Tyokone tyokone4 = new Tyokone("Paalutuskone");
        tyokone4.rekisteroi();
        lisaa(tyokone1);
        lisaa(tyokone2);
        lisaa(tyokone3);
        lisaa(tyokone4);

        
    }
    
    
    /**
     * Lisää uuden koneen tietorakenteeseen
     * @param tyokone lisättävä kone
     */
    public void lisaa(Tyokone tyokone) {
        
        alkiot.add(tyokone);
        muutettu = true;
        
    }
    
    
    /**
     * @param id , jolla etsitään tietorakenteesta
     * @return palauttaa Tyokonen nimen 
     */
    public String annaTyokone(int id) {
        
        for (Tyokone tyokone : alkiot) {
            if (tyokone.getTunnusNro() == id) return tyokone.getTyokone();
        }
        
        return "";
        
    }
    
    
    /**
     * @param s , jolla etsitään tietorakenteesta
     * @return palauttaa tyokoneiden id:t listassa, jotka sisältävät parametrin s
     */
    public List<Integer> annaTyokone(String s) {
        
        
        List<Integer> koneet = new ArrayList<Integer>();
        
        for (Tyokone tyokone : alkiot) {
            if (tyokone.getTyokone().contains(s)) koneet.add(tyokone.getTunnusNro());
        }
        
        return koneet;
        
    }
    
    
    
    /**
     * @return tietorakenteen koon
     */
    public int tyokoneetGetLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Tallentaa tyokoneet tiedostoon.  
     * Tiedoston muoto: dat
     * <pre>
     * 1     |  kuorma-auto  
     * 2     |  pyöräkuormaaja
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
            for (Tyokone tyokone : alkiot) {
                fo.println(tyokone.toString());
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
     * Lukee työkoneet tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import rekisteri.Tyokone;
     *
     * Tyokoneet tyokoneet = new Tyokoneet();
     * Tyokone tyokone1 = new Tyokone("Kuorma-auto"), tyokone2 = new Tyokone("Kaivinkone");
     * String hakemisto = "testityokoneet";
     * String tiedNimi = hakemisto+"/tyokoneet";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * tyokoneet.lueTiedostosta(tiedNimi); #THROWS SailoException
     * tyokoneet.lisaa(tyokone1);
     * tyokoneet.lisaa(tyokone2);
     * tyokoneet.tallenna();
     * Tyokoneet tyokoneet2 = new Tyokoneet();  
     * tyokoneet2.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     * tyokoneet.annaTyokone(0).toString().equals(tyokoneet2.annaTyokone(0).toString()) === true;
     * tyokoneet.annaTyokone(1).toString().equals(tyokoneet2.annaTyokone(1).toString()) === true;
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
                Tyokone tyokone = new Tyokone();
                tyokone.parse(rivi); // voisi olla virhekäsittely
                lisaa(tyokone);
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
     * Testi pääohjelma, jolla katsotaan tyokoneet luokan toimintaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Tyokoneet tyokoneet = new Tyokoneet();
        
        System.out.println("================ Työkoneiden testi ===================");
        
        for (Tyokone k : tyokoneet.alkiot) {
            k.tulosta(System.out);
        }
          
    }

}
