/**
 * 
 */
package rekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tyotehtävän ylläpito
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Tyotehtava {
    
    private int tunnusNro;
    private String tyotehtava = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan tyotehtava, mutta vielä ei tehdä mitään
     */
    public Tyotehtava() {
        // Ei tarvita vielä
    }
    
    
    /**
     * Tyotehtavan muodostaja
     * @param tyotehtava uusi tyotehtava
     */
    public Tyotehtava(String tyotehtava) {
        
        this.tyotehtava = tyotehtava;
    
    }
    
        
    /**
     * Tulostetaan tyotehtava
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println("id: " + this.tunnusNro + " " + this.tyotehtava);
    
    }
    
    
    /**
     * Tulostaa tyotehtavan
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
    
    }
    
    
    /**
     * @return palauttaa tyotyotehtavaen oman id:n
     */
    public int getTunnusNro() {
        
        return this.tunnusNro;
        
    }
    
    
    /**
     * @return palautaa tyotehtavan nimen
     */
    public String getTyotehtava() {
        
        return this.tyotehtava;
    
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
   * Selvitää tyotehtavan tiedot | erotellusta merkkijonosta.
   * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
   * @param rivi josta tyotehtavan tiedot otetaan
   * @example
   * <pre name="test">
   * Tyotehtava tyotehtava = new Tyotehtava();
   * tyotehtava.parse("   1 |  Maansiirto");
   * tyotehtava.getTunnusNro() === 1;
   * tyotehtava.toString().startsWith("1|Maansiirto") === true;
   *
   * tyotehtava.rekisteroi();
   * int n = tyotehtava.getTunnusNro();
   * tyotehtava.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
   * tyotehtava.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
   * tyotehtava.getTunnusNro() === n+20+1; 
   * </pre>
   */
    public void parse(String rivi) {
        
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        tyotehtava = Mjonot.erota(sb, '|', tyotehtava);
        
    }
    
    
    /**
     * Palauttaa tyotehtavan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tyotehtava tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tyotehtava tyotehtava = new Tyotehtava();
     *   tyotehtava.parse("   1   |  Maansiirto");
     *   tyotehtava.toString()    === "1|Maansiirto";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + tyotehtava;
    }
    
    
    /**
     * Antaa Tyotehtavalle seuraavan tunnusnumeron
     * @return Työtehtävän uusi tunnusnumero
     *  @example
     * <pre name="test">
     *   Tyotehtava tyotehtava1 = new Tyotehtava();
     *   tyotehtava1.getTunnusNro() === 0;
     *   tyotehtava1.rekisteroi();
     *   Tyotehtava tyotehtava2 = new Tyotehtava();
     *   tyotehtava2.rekisteroi();
     *   int n1 = tyotehtava1.getTunnusNro();
     *   int n2 = tyotehtava2.getTunnusNro();
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
        
        Tyotehtava tyotehtava = new Tyotehtava("Maansiirto");
        Tyotehtava tyotehtava2 = new Tyotehtava("Maanmuokkaus");
        tyotehtava.rekisteroi();
        tyotehtava2.rekisteroi();
        tyotehtava.tulosta(System.out);
        tyotehtava2.tulosta(System.out);
    
    }
    
}
