/**
 * 
 */
package rekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tilan ylläpito 
 * @author Tommi
 * @version 11.3.2019
 *
 */
public class Tila {
    
    private int tunnusNro;
    private String tila = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan tila, mutta vielä ei tehdä mitään
     */
    public Tila() {
        // Ei tarvita vielä
    }
    
    
    /**
     * Tilan muodostaja
     * @param tila uusi tila
     */
    public Tila(String tila) {
        this.tila = tila;
    }
    
    
    /**
     * Tulostetaan tila
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println("id: " + this.tunnusNro + " " + this.tila);
    
    }
    
    
    /**
     * Tulostaa tilan
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
    
    }
    
    
    /**
     * @return palauttaa tilan oman id:n
     */
    public int getTunnusNro() {
        
        return this.tunnusNro;
        
    }
    
    
    /**
     * @return palautaa tilan nimen
     */
    public String getTila() {
        
        return this.tila;
        
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
   * Selvitää tilan tiedot | erotellusta merkkijonosta.
   * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
   * @param rivi josta tilan tiedot otetaan
   * @example
   * <pre name="test">
   * Tila tila = new Tila();
   * tila.parse("   2  |  Poissa");
   * tila.getTunnusNro() === 2;
   * tila.toString().startsWith("2|Poissa") === true;
   *
   * tila.rekisteroi();
   * int n = tila.getTunnusNro();
   * tila.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
   * tila.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
   * tila.getTunnusNro() === n+20+1; 
   * </pre>
   */
    public void parse(String rivi) {
        
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        tila = Mjonot.erota(sb, '|', tila);
        
    }
    
    
    /**
     * Palauttaa tilan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tila tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tila tila = new Tila();
     *   tila.parse("   1   |  Töissä");
     *   tila.toString()    === "1|Töissä";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + tila;
    }
    
    
    /**
     * Antaa Tilalle seuraavan tunnusnumeron
     * @return Tilan uusi tunnusnumero
     *  @example
     * <pre name="test">
     *   Tila tila1 = new Tila();
     *   tila1.getTunnusNro() === 0;
     *   tila1.rekisteroi();
     *   Tila tila2 = new Tila();
     *   tila2.rekisteroi();
     *   int n1 = tila1.getTunnusNro();
     *   int n2 = tila2.getTunnusNro();
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
        
        Tila tila = new Tila("Töissä");
        Tila tila2 = new Tila("Poissa");
        tila.rekisteroi();
        tila2.rekisteroi();
        tila.tulosta(System.out);
        tila2.tulosta(System.out);
    
    }

}
