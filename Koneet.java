package rekisteri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Koneet luokka, joka osaa mm. lis‰t‰ uuden koneen
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Koneet {
    
   private static final int MAX_KONEITA = 5;
   private int lkm = 0;
   private String tiedostonPerusNimi = "";
   private boolean muutettu = false;
   private Kone[] alkiot = new Kone[MAX_KONEITA];
   
   
   /**
   *  Parametritˆn muododostaja
   */
   public Koneet() {
       // Attribuuttien oma alustus riitt‰‰
   }
   
   
  /**
  * Lis‰‰ uuden koneen tietorakenteeseen. Ottaa koneen omistukseensa.
  * @param kone lis‰t‰‰v‰n koneen viite.  Huom tietorakenne muuttuu omistajaksi
  * @throws SailoException mik‰li virhe lis‰‰misess‰
  * @example
  * <pre name="test">
  * #THROWS SailoException
  * Koneet koneet = new Koneet();
  * Kone kaivinkone1 = new Kone(), kaivinkone2 = new Kone();
  * koneet.getLkm() === 0;
  * koneet.lisaa(kaivinkone1); koneet.getLkm() === 1;
  * koneet.lisaa(kaivinkone2); koneet.getLkm() === 2;
  * koneet.lisaa(kaivinkone1); koneet.getLkm() === 3;
  * koneet.anna(0) === kaivinkone1;
  * koneet.anna(1) === kaivinkone2;
  * koneet.anna(2) === kaivinkone1;
  * koneet.anna(1) == kaivinkone1 === false;
  * koneet.anna(1) == kaivinkone2 === true;
  * koneet.lisaa(kaivinkone1); koneet.getLkm() === 4;
  * koneet.lisaa(kaivinkone1); koneet.getLkm() === 5;
  * </pre>
  */
  public void lisaa(Kone kone) throws SailoException {
     
      if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+15); 
      alkiot[lkm] = kone;
      lkm++;
      muutettu = true;
  
  }
  
  

  /** Korvaa koneen tietorakenteessa. Ottaa koneen omistukseensa.
   * Estsii samalla tunnusnrolla olevan koneen, jos ei lˆydy niin lis‰t‰‰n uutena j‰senen‰
   * @param kone lis‰tt‰v‰n koneen viite. Huom tietorakenne muuttuu omistajaksi
   * @throws SailoException jos tietorakenne jo t‰ynn‰
   * <pre name="test">
   * #THROWS SailoException,CloneNotSupportedException
   * #PACKAGEIMPORT
   * Koneet koneet = new Koneet();
   * Kone kone1 = new Kone(), kone2 = new Kone();
   * kone1.rekisteroi(); kone2.rekisteroi();
   * koneet.getLkm() === 0;
   * koneet.korvaaTaiLisaa(kone1); koneet.getLkm() === 1;
   * koneet.korvaaTaiLisaa(kone2); koneet.getLkm() === 2;
   * Kone kone3 = kone1.clone();
   * kone3.setPuhnro("0402223331");
   * koneet.korvaaTaiLisaa(kone3); koneet.getLkm() === 2;
   * Kone k0 = koneet.anna(0);
   * k0 === kone3;
   * k0 == kone3 === true;
   * k0 == kone1 === false;
   * </pre>
   */
  public void korvaaTaiLisaa(Kone kone) throws SailoException {
      
      int id = kone.getTunnusNro();
      for (int i = 0; i < lkm; i++) {
          if (alkiot[i].getTunnusNro() == id) {
              alkiot[i] = kone;
              muutettu = true;
              return;
          }
      }
      lisaa(kone);
      
  }

  
  /** 
   * Palauttaa "taulukossa" hakuehtoon vastaavien j‰senten viitteet 
   * @param hakuehto hakuehto 
   * @param k etsitt‰v‰n kent‰n indeksi  
   * @return tietorakenteen lˆytyneist‰ j‰senist‰ 
   * @example 
   * <pre name="test"> 
   * #THROWS SailoException 
   * #import java.util.*; 
   *   Koneet koneet = new Koneet(); 
   *   Kone kone1 = new Kone(); kone1.parse("1|3|ei ole|"); 
   *   Kone kone2 = new Kone(); kone2.parse("2|1|NFK-356|"); 
   *   Kone kone3 = new Kone(); kone3.parse("3|3|ei ole|"); 
   *   Kone kone4 = new Kone(); kone4.parse("4|4|ei ole|"); 
   *   Kone kone5 = new Kone(); kone5.parse("5|1|NGK-352|"); 
   *   koneet.lisaa(kone1); koneet.lisaa(kone2); koneet.lisaa(kone3); koneet.lisaa(kone4); koneet.lisaa(kone5);
   *   List<Kone> loytyneet;  
   *   loytyneet = (List<Kone>)koneet.etsi("*3*",1);  
   *   loytyneet.size() === 2;  
   *   loytyneet.get(0) == kone1 === true;  
   *   loytyneet.get(1) == kone3 === true;  
   *     
   *   loytyneet = (List<Kone>)koneet.etsi("*-3*",2);  
   *   loytyneet.size() === 2;  
   *   loytyneet.get(0) == kone2 === true;  
   *   loytyneet.get(1) == kone5 === true; 
   *     
   *   loytyneet = (List<Kone>)koneet.etsi(null,-1);  
   *   loytyneet.size() === 5;  
   * </pre> 
   */ 
  public Collection<Kone> etsi(String hakuehto, int k) { 
      String ehto = "*"; 
      if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
      int hk = k; 
      if ( hk < 0 ) hk = 1;
      List<Kone> loytyneet = new ArrayList<Kone>(); 
      for (int i = 0; i < lkm; i++) { 
          if (WildChars.onkoSamat(alkiot[i].annaSisalto(hk), ehto)) loytyneet.add(alkiot[i]);   
      } 
      
      Kone.Vertailija vertailija = new Kone.Vertailija(hk);
      Collections.sort(loytyneet, vertailija);
          
      return loytyneet; 
 
  }
  
  
  /**
   * Lukee koneet tiedostosta.
   * @param tied tiedoston perusnimi
   * @throws SailoException jos lukeminen ep‰onnistuu
   * @example
   * <pre name="test">
   * #THROWS SailoException
   * #import java.io.File;
   * #import rekisteri.Kone;
   *
   * Koneet koneet = new Koneet();
   * Kone kone1 = new Kone(), kone2 = new Kone();
   * kone1.taytaKoneTiedoilla();
   * kone2.taytaKoneTiedoilla();
   * String hakemisto = "testikoneet";
   * String tiedNimi = hakemisto+"/koneet";
   * File ftied = new File(tiedNimi+".dat");
   * File dir = new File(hakemisto);
   * dir.mkdir();
   * ftied.delete();
   * koneet.lueTiedostosta(tiedNimi); #THROWS SailoException
   * koneet.lisaa(kone1);
   * koneet.lisaa(kone2);
   * koneet.tallenna();
   * Koneet koneet2 = new Koneet();  
   * koneet2.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
   * koneet.anna(0).toString().equals(koneet2.anna(0).toString()) === true;
   * koneet.anna(1).toString().equals(koneet2.anna(1).toString()) === true;
   * ftied.delete() === true;
   * dir.delete() === true;
   * </pre>
   */
  public void lueTiedostosta(String tied) throws SailoException {
      setTiedostonPerusNimi(tied);

      try (Scanner fi = new Scanner(new File(getTiedostonNimi()))) { 
          String rivi;
          while ( fi.hasNext()) {
              rivi = fi.nextLine().trim();
              if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
              Kone kone = new Kone();
              kone.parse(rivi); // voisi olla virhek‰sittely
              lisaa(kone);
          }
      }
      catch (FileNotFoundException ex) {
          throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
      }
      
  }
  
  
  /**
   * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
   * @throws SailoException jos tulee poikkeus
   */
  public void lueTiedostosta() throws SailoException {
      lueTiedostosta(getTiedostonPerusNimi());
  }
  
  
  
  /**
  * Palauttaa rekisterin koneiden lukum‰‰r‰n
  * @return koneiden lukum‰‰r‰
  */
  public int getLkm() {
      
      return this.lkm;
  
  }
  
  
  /**
   * Tallentaa koneet tiedostoon.  
   * Tiedoston muoto: dat
   * <pre>
   * 1  |1      |YZF-579        |1234567-8   |0405606062   |1         |1   |Ajaa vain maata pois                           
   * 2  |3      |ei ole         |3412534-6   |0400506732   |2         |1   |                                               
   * 3  |1      |KRN-604        |1353674-5   |0405606062   |1         |3   |                       
   * </pre>
   * @throws SailoException jos talletus ep‰onnistuu
   */
  public void tallenna() throws SailoException {
      if ( !muutettu ) return;
      
      File fbak = new File(getBakNimi());
      File ftied = new File(getTiedostonNimi());
      fbak.delete(); // if .. System.err.println("Ei voi tuhota");
      ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimet‰");
       
      try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
          for (int i = 0; i < lkm; i++) {
              fo.println(alkiot[i].toString());
          }
          //} catch ( IOException e ) { // ei heit‰ poikkeusta
          //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
          muutettu = false;
      } catch ( FileNotFoundException ex ) {
          throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
      }
      
  }
  
  
  /** 
   * Poistaa koneen jolla on valittu tunnusnumero  
   * @param id poistettavan koneen tunnusnumero 
   * @return 1 jos poistettiin, 0 jos ei lˆydy 
   * @example 
   * <pre name="test"> 
   * #THROWS SailoException  
   * Koneet koneet = new Koneet(); 
   * Kone kone1 = new Kone(), kone2 = new Kone(), kone3 = new Kone(); 
   * kone1.rekisteroi(); kone2.rekisteroi(); kone3.rekisteroi(); 
   * int id = kone1.getTunnusNro(); 
   * koneet.lisaa(kone1);
   * koneet.lisaa(kone2); 
   * koneet.lisaa(kone3); 
   * koneet.poista(id+1) === 1; 
   * koneet.annaId(id+1) === null; koneet.getLkm() === 2; 
   * koneet.poista(id) === 1; koneet.getLkm() === 1; 
   * koneet.poista(id+3) === 0; koneet.getLkm() === 1; 
   * </pre> 
   *  
   */ 
  public int poista(int id) { 
      int ind = etsiId(id); 
      if (ind < 0) return 0; 
      lkm--; 
      for (int i = ind; i < lkm; i++) 
          alkiot[i] = alkiot[i + 1]; 
      alkiot[lkm] = null; 
      muutettu = true; 
      return 1; 
  } 
  
  
  /** 
   * Etsii koneen id:n perusteella 
   * @param id tunnusnumero, jonka mukaan etsit‰‰n 
   * @return lˆytyneen koneen indeksi tai -1 jos ei lˆydy 
   * <pre name="test"> 
   * #THROWS SailoException  
   * Koneet koneet = new Koneet(); 
   * Kone kone1 = new Kone(), kone2 = new Kone(), kone3 = new Kone(); 
   * kone1.rekisteroi(); kone2.rekisteroi(); kone3.rekisteroi(); 
   * int id = kone1.getTunnusNro(); 
   * koneet.lisaa(kone1); koneet.lisaa(kone2); koneet.lisaa(kone3); 
   * koneet.etsiId(id+1) === 1; 
   * koneet.etsiId(id+2) === 2; 
   * </pre> 
   */ 
  public int etsiId(int id) { 
      for (int i = 0; i < lkm; i++) 
          if (id == alkiot[i].getTunnusNro()) return i; 
      return -1; 
  } 
  
  
  /** 
   * Etsii koneen id:n perusteella 
   * @param id tunnusnumero, jonka mukaan etsit‰‰n 
   * @return kone jolla etsitt‰v‰ id tai null 
   * <pre name="test"> 
   * #THROWS SailoException  
   * Koneet koneet = new Koneet(); 
   * Kone kone1 = new Kone(), kone2 = new Kone(), kone3 = new Kone(); 
   * kone1.rekisteroi(); kone2.rekisteroi(); kone3.rekisteroi(); 
   * int id = kone1.getTunnusNro(); 
   * koneet.lisaa(kone1); koneet.lisaa(kone2); koneet.lisaa(kone3); 
   * koneet.annaId(id) == kone1 === true; 
   * koneet.annaId(id+1) == kone2 === true; 
   * koneet.annaId(id+2) == kone3 === true; 
   * </pre> 
   */ 
  public Kone annaId(int id) { 
      for (Kone kone : alkiot) { 
          if (kone == null) return null;
          if (id == kone.getTunnusNro()) return kone; 
      } 
      return null; 
  } 
  
  
  /**                                                          
   * Asettaa tiedoston perusnimen                              
   * @param tie tallennustiedoston perusnimi                   
   */                                                          
  public void setTiedostonPerusNimi(String tie) {              
      tiedostonPerusNimi = tie;                                
  }                                                            
                                                               
                                                               
  /**                                                          
   * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen   
   * @return tallennustiedoston nimi                           
   */                                                          
  public String getTiedostonPerusNimi() {                      
      return tiedostonPerusNimi;                               
  }  
  
  
  /**
   * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
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
  * Palauttaa viitteen i:teen koneeseen.
  * @param i monennenko koneen viite halutaan
  * @return viite koneeseen, jonka indeksi on i
  * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella 
  */
  public Kone anna(int i) throws IndexOutOfBoundsException {
    
      if (i < 0 || lkm <= i)
         throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
     return alkiot[i];
  
  }
  
  
  /**
   * Testi ohjelma, jolla testataan koneiden lisˆˆmistˆ rekisteriin<
   * @param args ei k‰ytˆss‰
   */
  public static void main(String[] args) {
  
      Koneet koneet = new Koneet();
      Kone kaivinkone = new Kone();
      Kone kaivinkone2 = new Kone();
      kaivinkone.rekisteroi();
      kaivinkone.taytaKoneTiedoilla();
      kaivinkone2.rekisteroi();
      kaivinkone2.taytaKoneTiedoilla();
      
      try {
        koneet.lisaa(kaivinkone);
        koneet.lisaa(kaivinkone2); 
    } catch (SailoException ex) {
        System.out.println(ex.getMessage());
    }  

 
 

      
      System.out.println("============= Koneet testi =================");
      for (int i = 0; i < koneet.getLkm(); i++) {
          
          Kone kone = koneet.anna(i);
          System.out.println("Kone paikassa: " + i);
          kone.tulosta(System.out);
      
      }
  
  }

  
}
