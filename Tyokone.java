/**
 * 
 */
package rekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tyokoneen ylläpito
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Tyokone {
    
    private int tunnusNro;
    private String tyokone = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan tyokone, mutta vielä ei tehdä mitään
     */
    public Tyokone() {
        // Ei tarvita vielä
    }
    
    
    /**
     * Tyokoneen muodostaja
     * @param tyokone uusi tyokone
     */
    public Tyokone(String tyokone) {
        
        this.tyokone = tyokone;
    
    }
    
    
    /**
     * Tulostetaan tila
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println("id: " + this.tunnusNro + " " + this.tyokone);
    
    }
    
    
    /**
     * Tulostaa tyokoneen
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
    
    }
    
    
    /**
     * @return palauttaa tyokoneen oman id:n
     */
    public int getTunnusNro() {
        
        return this.tunnusNro;
    
    }
    
    
    /**
     * @return palautaa tyokoneen nimen
     */
    public String getTyokone() {
        
        return this.tyokone;
    
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nro asetettava tunnusnumero
     */
    private void setTunnusNro(int nro) {
        
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
        
    }
    
    
  /**
   * Selvitää tyokoneen tiedot | erotellusta merkkijonosta.
   * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
   * @param rivi josta tyokoneen tiedot otetaan
   * @example
   * <pre name="test">
   * Tyokone tyokone = new Tyokone();
   * tyokone.parse("   1  |  Kuorma-auto");
   * tyokone.getTunnusNro() === 1;
   * tyokone.toString().startsWith("1|Kuorma-auto") === true;
   *
   * tyokone.rekisteroi();
   * int n = tyokone.getTunnusNro();
   * tyokone.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
   * tyokone.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
   * tyokone.getTunnusNro() === n+20+1; 
   * </pre>
   */
    public void parse(String rivi) {
        
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        tyokone = Mjonot.erota(sb, '|', tyokone);
        
    }
    
    
    /**
     * Palauttaa tyokoneen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tyokone tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tyokone tyokone = new Tyokone();
     *   tyokone.parse("   3   |  Kaivinkone");
     *   tyokone.toString()    === "3|Kaivinkone";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + tyokone;
    }
    

    /**
     * Antaa Työkoneelle seuraavan tunnusnumeron
     * @return Työkoneen uusi tunnusnumero
     *  @example
     * <pre name="test">
     *   Tyokone kone1 = new Tyokone();
     *   kone1.getTunnusNro() === 0;
     *   kone1.rekisteroi();
     *   Tyokone kone2 = new Tyokone();
     *   kone2.rekisteroi();
     *   int n1 = kone1.getTunnusNro();
     *   int n2 = kone2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    
    }

    
    /**
     * Testi pääohjelma, jossa muododstetaan tila olio
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Tyokone kone = new Tyokone("Kaivinkone");
        Tyokone kone2 = new Tyokone("Kuorma-auto");
        kone.rekisteroi();
        kone2.rekisteroi();
        kone.tulosta(System.out);
        kone2.tulosta(System.out);
    
    }

}
