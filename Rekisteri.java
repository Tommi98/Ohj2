package rekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * rekisteri-luokka, joka huolehtii konerekisterist‰.  P‰‰osin kaikki metodit
 * ovat vain "v‰litt‰j‰metodeja" muihin luokkiin.
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Rekisteri {
    
    private Koneet koneet = new Koneet();
    private Tyokoneet tyokoneet = new Tyokoneet();
    private Tilat tilat = new Tilat();
    private Tyotehtavat tyotehtavat = new Tyotehtavat();
    
    /**
    * Palautaa rekisterin koneiden m‰‰r‰n
    * @return koneiden m‰‰r‰
    */
   public int getKoneita() {
       
       return koneet.getLkm();
   
   }
   
   
   /**
    * @return tyˆkoneiden tietorakenteen pituus
    */
   public int getTyokoneetLkm() {
       return tyokoneet.tyokoneetGetLkm();
   }
   
   
   /**
    * @return tilojen tietorakenteen pituus
    */
   public int getTilatLkm() {
       return tilat.getTilatLkm();
   }
   
   
   /**
    * @return tyˆkoneiden tietorakenne
    */
   public int getTehtLkm() {
       return tyotehtavat.getTehtLkm();
   }
   
   
   /**
    * Palauttaa i:n koneen
    * @param i monesko kone palautetaan
    * @return viite i:teen koneeseen
    * @throws IndexOutOfBoundsException jos i v‰‰rin
    */
   public Kone annaKone(int i) throws IndexOutOfBoundsException {
       
       return koneet.anna(i);
   
   }
   
   
   /**
    * @param id ,jolla etsit‰‰n nime‰
    * @return tyokoneen
     */
   public String annaTyokone(int id) {
       
       return tyokoneet.annaTyokone(id);
   
   }
   
   
   /**
    * @param s ,jolla etsit‰‰n
    * @return tyokoneen id
     */
   public List<Integer> annaTyokone(String s) {
       
       return tyokoneet.annaTyokone(s);
   
   }
   
   
  /**
   * @param s ,jolla etsit‰‰n
   * @return tyˆteht‰v‰n id:t teitorakenteessa
   */
   public List<Integer> annaTyotehtava(String s) {
       return tyotehtavat.annaTyotehtava(s);
   }
   
   
   /**
    *@param s , jolla etsit‰‰n
    * @return tilan id:t
    */
   public List<Integer> annaTila(String s) {
       return tilat.annaTila(s);
   }
   
   
   /**
    * @param id , jolla etsit‰‰n nime‰
    * @return tilan
    */
   public String annaTila(int id) {
       
       return tilat.annaTila(id);
       
   }
   
   
   /**
    * @param id , jolla etsit‰‰n nime‰
    * @return tyoteht‰v‰n
    */
   public String annaTyotehtava(int id) {
       return tyotehtavat.annaTyotehtava(id);
   }
   
   
   /**
    * Lis‰‰ rekisteriin uuden koneen
    * @param kone lis‰tt‰v‰ kone
    * @throws SailoException jos lis‰yst‰ ei voida tehd‰
    * @example
    * <pre name="test">
    * #THROWS SailoException
    * Rekisteri rekisteri = new Rekisteri();
    * Kone kone = new Kone(), kone2 = new Kone();
    * kone.rekisteroi(); kone2.rekisteroi();
    * rekisteri.getKoneita() === 0;
    * rekisteri.lisaa(kone); rekisteri.getKoneita() === 1;
    * rekisteri.lisaa(kone2); rekisteri.getKoneita() === 2;
    * rekisteri.lisaa(kone); rekisteri.getKoneita() === 3;
    * rekisteri.getKoneita() === 3;
    * rekisteri.annaKone(0) === kone;
    * rekisteri.annaKone(1) === kone2;
    * rekisteri.annaKone(2) === kone;
    * rekisteri.annaKone(3) === kone; #THROWS IndexOutOfBoundsException
    * rekisteri.lisaa(kone); rekisteri.getKoneita() === 4;
    * rekisteri.lisaa(kone); rekisteri.getKoneita() === 5;
    * rekisteri.lisaa(kone);
    * </pre>
    */
   public void lisaa(Kone kone) throws SailoException {
       
       koneet.lisaa(kone);
   
   }
   
   
   /**
    * Lis‰‰ rekisteriin uuden tilan
    * @param tila lis‰tt‰v‰ tila
    * @throws SailoException jos lis‰yst‰ ei voida tehd‰
    */
   public void lisaa(Tila tila) throws SailoException {
       
       tilat.lisaa(tila);
   
   }
   
   
   /**
    * Lis‰‰ rekisteriin uuden tyokoneen
    * @param tyokone lis‰tt‰v‰ tyokone
    * @throws SailoException jos lis‰yst‰ ei voida tehd‰
    */
   public void lisaa(Tyokone tyokone) throws SailoException {
       
       tyokoneet.lisaa(tyokone);
   
   }
   
   
   /**
    * Lis‰‰ rekisteriin uuden tyotehtavan
    * @param tyotehtava lis‰tt‰v‰ tyotehtava
    * @throws SailoException jos lis‰yst‰ ei voida tehd‰
    */
   public void lisaa(Tyotehtava tyotehtava) throws SailoException {
       
       tyotehtavat.lisaa(tyotehtava);
   
   }
   
   
   /**
    * Korvaa koneen tietorakenteessa. Ottaa koneen omistukseensa.
    * Estsii samalla tunnusnrolla olevan koneen, jos ei lˆydy niin lis‰t‰‰n uutena j‰senen‰
    * @param kone lis‰tt‰v‰n koneen viite. Huom tietorakenne muuttuu omistajaksi
    * @throws SailoException jos tietorakenne t‰ynn‰
    */
   public void korvaaTaiLisaa(Kone kone) throws SailoException {
       
       koneet.korvaaTaiLisaa(kone);
       
   }
   
   
   /** 
    * Palauttaa "taulukossa" hakuehtoon vastaavien koneiden viitteet 
    * @param hakuehto hakuehto  
    * @param k etsitt‰v‰n kent‰n indeksi  
    * @return tietorakenteen lˆytyneist‰ koneista
    * @throws SailoException Jos jotakin menee v‰‰rin
    */ 
   public Collection<Kone> etsi(String hakuehto, int k) throws SailoException { 
       return koneet.etsi(hakuehto, k); 
   } 
   
   
   /**
    * Poistaa rekisterist‰ koneen tiedot
    * @param kone kone jokapoistetaan
    * @return montako konetta poistettiin
    */
   public int poista(Kone kone) {
       if ( kone == null ) return 0;
       int ret = koneet.poista(kone.getTunnusNro()); 
       return ret; 
   }
   
   
   /**
    * Tallenttaa rekisterin tiedot tiedostoon.  
    * Vaikka koneiden tallettamien ep‰onistuisi, niin yritet‰‰n silti tallettaa
    * kaikkia ennen poikkeuksen heitt‰mist‰.
    * @throws SailoException jos tallettamisessa ongelmia
    */
   public void tallenna() throws SailoException {
       String virhe = "";
       try {
           koneet.tallenna();
       } catch ( SailoException ex ) {
           virhe = ex.getMessage();
       }
   
       try {
           tyokoneet.tallenna();
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
       }
       
       try {
           tilat.tallenna();
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
       }
       
       try {
           tyotehtavat.tallenna();
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
       }
       if ( !"".equals(virhe) ) throw new SailoException(virhe);
       
   }
   
   
   /**
    * Lukee rekisterin tiedot tiedostosta
    * @param nimi jota k‰yte‰‰n lukemisessa
    * @throws SailoException jos lukeminen ep‰onnistuu
    * <pre name="test">
    * #THROWS SailoException 
    * #import java.io.*;
    * #import java.util.*;
    * 
    *  Rekisteri rekisteri = new Rekisteri();
    *  
    *  Kone kone1 = new Kone(); kone1.taytaKoneTiedoilla(); kone1.rekisteroi();
    *  Kone kone2 = new Kone(); kone2.taytaKoneTiedoilla(); kone2.rekisteroi();
    *     
    *  String hakemisto = "testityomaa";
    *  File dir = new File(hakemisto);
    *  File ftied  = new File(hakemisto+"/koneet.dat");
    *  File ftitied = new File(hakemisto+"/tila.dat");
    *  File ftktied = new File(hakemisto+"/tyokone.dat");
    *  File ftetied = new File(hakemisto+"/tyotehtava.dat");
    *  dir.mkdir();  
    *  ftied.delete();
    *  ftitied.delete();
    *  ftktied.delete();
    *  ftetied.delete();
    *  
    *  rekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
    *  rekisteri.lisaa(kone1);
    *  rekisteri.lisaa(kone2);
    *  rekisteri.tallenna();
    *  rekisteri = new Rekisteri();
    *  rekisteri.lueTiedostosta(hakemisto);
    *  
    *  rekisteri.annaKone(0).toString().equals(kone1.toString()) === true;
    *  rekisteri.annaKone(1).toString().equals(kone2.toString()) === true;
    *  
    *  rekisteri.lisaa(kone2);
    *  rekisteri.tallenna();
    *  ftied.delete() === true;
    *  ftitied.delete() === true;
    *  ftktied.delete() === true;
    *  ftetied.delete() === true;
    *  File fbak = new File(hakemisto+"/koneet.bak");
    *  File ftibak = new File(hakemisto+"/tila.bak");
    *  File ftkbak = new File(hakemisto+"/tyokone.bak");
    *  File ftebak = new File(hakemisto+"/tyotehtava.bak");
    *  fbak.delete() === true;
    *  ftibak.delete() === true;
    *  ftkbak.delete() === true;
    *  ftebak.delete() === true;  
    *  dir.delete() === true;
    * </pre>
    */
   public void lueTiedostosta(String nimi) throws SailoException {
       
       koneet = new Koneet(); // jos luetaan olemassa olevaan niin helpoin tyhjent‰‰ n‰in
       
       setTiedosto(nimi);
       koneet.lueTiedostosta();
       tilat.lueTiedostosta();
       tyokoneet.lueTiedostosta();
       tyotehtavat.lueTiedostosta();
       
   }
   
   
   /**
    * Asettaa tiedostojen perusnimet
    * @param nimi uusi nimi
    */
   public void setTiedosto(String nimi) {
       
       File dir = new File(nimi);
       dir.mkdirs();
       String hakemistonNimi = "";
       if ( !nimi.isEmpty() ) hakemistonNimi = nimi + "/";
       koneet.setTiedostonPerusNimi(hakemistonNimi + "koneet");
       tilat.setTiedostonPerusNimi(hakemistonNimi + "tila");
       tyokoneet.setTiedostonPerusNimi(hakemistonNimi + "tyokone");
       tyotehtavat.setTiedostonPerusNimi(hakemistonNimi + "tyotehtava");
       
   }
   

    /**
     * Testi ohjelma, jossa kokeillaan koneiden rekisterˆimist‰ rekisteriin
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
       
        Rekisteri rekisteri = new Rekisteri();
        
        Kone kone1 = new Kone(), kone2 = new Kone();
        kone1.rekisteroi();
        kone1.taytaKoneTiedoilla();
        kone2.rekisteroi();
        kone2.taytaKoneTiedoilla();
        
        try {
            rekisteri.lisaa(kone1);  
            rekisteri.lisaa(kone2); 

        } catch (SailoException ex) {
            System.err.println("Virhe: " + ex.getMessage());
        }
   
   
        System.out.println("============= Rekisterin testi =================");
        
        for (int i = 0; i < rekisteri.getKoneita(); i++) {
            
            Kone kone = rekisteri.annaKone(i);
            System.out.println("Kone paikassa: " + i);
            kone.tulosta(System.out);
        
        }
    
    }

}
