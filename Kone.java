package rekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.YtunnusTarkistus.*;

/**
 * Koneen ylläpito
 * @author Tommi Prusti
 * @version 19.4.2019
 *
 */
public class Kone implements Cloneable {

    private int tunnusNro;
    private int tyokone;
    private String rekisterinumero = "";
    private String ytunnus = "";
    private String puhelinnro = "";
    private int tyotehtava;
    private int tila;
    private String tunnit = "";
    private String lisatietoja = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Vertailija osaa verrata tietyn kentän mukaan
     * @author Tommi Prusti
     * @version 20.4.2019
     *
     */
    public static class Vertailija implements Comparator<Kone> {

        
        private int k;
        
        
        /**
         * @param k ,kenttä minkä mukaan vertaillaan
         */
        public Vertailija(int k) {
            this.k = k;
        }
        

        /**
         * Vertaa kahta konetta toisiinsa
         */
        @Override
        public int compare(Kone kone1, Kone kone2) {
            return kone1.getAvain(k).compareTo(kone2.getAvain(k));
        }
        
    }
    
    
    /**
     * Palauttaa tietyn kentän lajitteluavaimen
     * @param k ,minkä kentän
     * @return lajitteluavain
     */
    public String getAvain(int k) {
        
        switch ( k ) {
        case 0: return "" + tunnusNro;       
        case 1: return "" + String.format("%05d", tyokone);         
        case 2: return "" + rekisterinumero; 
        case 3: return "" + ytunnus;         
        case 4: return "" + puhelinnro;      
        case 5: return "" + String.format("%04d", tyotehtava);      
        case 6: return "" + String.format("%04d", tila);            
        case 7: return "" + String.format("%5d", Integer.parseInt(tunnit));          
        case 8: return "" + lisatietoja;     
        default: return "";
        }
        
    }
    
    
    /**
     * Antaa koneelle seuraavan tunnusnumeron
     * @return Koneen uusi tunnusnumero
     *  @example
     * <pre name="test">
     *   Kone kone1 = new Kone();
     *   kone1.getTunnusNro() === 0;
     *   kone1.rekisteroi();
     *   Kone kone2 = new Kone();
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
     * Palauttaa koneen tunnusnumeron
     * @return koneen tunnusnumeron
     */
    public int getTunnusNro() {
        
        return tunnusNro;
    
    }
    
    
    /** 
     * palauttaa tiedon mikä työkone on
     * @return tiedon mikä työkone on kyseessä
     */
    public int getTyokone() {
        return this.tyokone;
    
    }
    
    
    /**
     * @param i id, joka vastaa jotai tyokonetta
     * @return Virheilmoitus, null jos ok
     */
    public String setTyokone(int i) {
        this.tyokone = i;
        return null;
    }
    
    
    /**
     * palauttaa tiedon mikä on koneen rekisterinumero
     * @return koneen rekisterinumeron
     */
    public String getReknro() {
        return this.rekisterinumero;
    }
    
    
    /**
     * @param s koneelle laitettava rekisterinumero
     * @return virheilmoitus, null jos ok
     */
    public String setReknro(String s) {
        if (!s.contains("-")) return "Rekisterinumeron täytyy sisältää '-' erotin merkki";
        this.rekisterinumero = s;
        return null;
    }
    
    
    /**
     * Palauttaa puhelinnumeron
     * @return koneen puhelunnumero
     */
    public String getPuhnro() {
        return this.puhelinnro;
    }
    
    
    /**
     * @param s koneelle laitettava puhelinnro
     * @return Virheilmoitus, null jos ok
     */
    public String setPuhnro(String s) {
        if ( !s.matches("[0-9]*") ) return "Puhelinnumeron on oltava numeerinen";
        if (s.length() < 10 || s.length() > 10) return "Puhelinnumero on liian pitkä tai lyhyt";
        this.puhelinnro = s;
        return null;
    }
    
    
    /**
     * Palauttaa koneen y tunnuksen
     * @return y tunnus
     */
    public String getYtunnus() {
        return this.ytunnus;
    }
    
    
    /**
     * @param s koneelle laitettava ytunnus
     * @return virheilmoitus, null jos ok
     */
    public String setYtunnus(String s) {
        if (s.length() < 9 || s.length() > 9) return "Y-tunnuksen tulee olla muotoa #######-#";
        if (!s.contains("-")) return "Y-tunnuksen täytyy sisältää '-' erotin merkki";
        String[] t = s.split("-",2);
        if (t[0].length() != 7 || t[1].length() != 1) return "Y-tunnuksen tulee olla muotoa #######-#";
        if (!t[0].matches("[0-9]*") || !t[1].matches("[0-9]*")) return "Y-tunnuksen tulee olla numeerinen";
        this.ytunnus = s;
        return null;
    }
    
    
    /**
     * Palauttaa tiedon koneen tilasta
     * @return tilan id
     */
    public int getTila() {
        return this.tila;
    }
    
    
    /**
     * @param i id, joka vastaa jotain tilaa
     * @return virheilmoitus, null jos ok
     */
    public String setTila(int i) {
        this.tila = i;
        return null;
    }
    
    
    /**
     * Palauttaa koneen lisätiedot
     * @return koneen lisätiedot merkkijonona
     */
    public String getLisatieto() {
        return this.lisatietoja;
    }
    
    
    /**
     * @param s koneelle lisättävä lisätietoja
     * @return Virheilmoitus, null jos ok
     */
    public String setLisatieto(String s) {
        this.lisatietoja = s;
        return null;
    }
    
    
    /**
     * Palauttaa tiedon koneen tyotehtavasta
     * @return tyotehtavan id:n
     */
    public int getTyotehtava() {
        return this.tyotehtava;
    }
    
    
    /**
     * @param i id, joka vastaa jotain tyotehtavaa
     * @return Virheilmoitus, null jos ok
     */
    public String setTyotehtava(int i) {
        this.tyotehtava = i;
        return null;
    }
    
    
    /**
     * Palauttaa koneen työtunnit
     * @return koneen työtunnit
     */
    public String getTunnit() {
        return this.tunnit;
        
    }
    
    
    /**
     * @param s koneelle laitettavat tunnit
     * @return Virheilmoitus, null jos ok
     */
    public String setTunnit(String s) {
        if ( !s.matches("[0-9]*") ) return "Tuntien tulee olla numeerinen";
        this.tunnit = s;
        return null;
    }
 
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot koneelle.
     * @param apuytunnus yritystunnus joka annetaan koneelle
     */
    public void taytaKoneTiedoilla(String apuytunnus) {
        
        tyokone = rand(1, 4);
        rekisterinumero = "ei ole";
        ytunnus = apuytunnus;
        puhelinnro ="0400568654";
        tyotehtava = rand(1, 4);
        tila = rand(1, 3);
        tunnit = "14";
        lisatietoja = "Lähtee huoltoon ensi viikoksi";
        
    
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
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String annaSisalto(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + tyokone;
        case 2: return "" + rekisterinumero;
        case 3: return "" + ytunnus;     
        case 4: return "" + puhelinnro;  
        case 5: return "" + tyotehtava;          
        case 6: return "" + tila;                
        case 7: return "" + tunnit;  
        case 8: return "" + lisatietoja; 
        default: return "";
        }
    }
      
    
    /**
     * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "tunnusNro";       
        case 1: return "tyokone";         
        case 2: return "rekisterinumero"; 
        case 3: return "ytunnus";         
        case 4: return "puhelinnro";      
        case 5: return "tyotehtava";      
        case 6: return "tila";            
        case 7: return "tunnit";          
        case 8: return "lisatietoja";     
        default: return "";
        }
    }
    
    
    /**
    * Palauttaa koneen kenttien lukumäärän
    * @return kenttien lukumäärä
    */
    public int getKenttia() {
       return 9;
    }
    
    
    /**
    * Selvitää koneen tiedot | erotellusta merkkijonosta
    * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
    * @param rivi josta koneen tiedot otetaan
    * @example
    * <pre name="test">
    * Kone kone = new Kone();
    * kone.parse("   2  |  2   | ei ole");
    * kone.getTunnusNro() === 2;
    * kone.toString().startsWith("2|2|ei ole|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
    *
    * kone.rekisteroi();
    * int n = kone.getTunnusNro();
    * kone.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
    * kone.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    * kone.getTunnusNro() === n+20; 
    * </pre>
    */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        tyokone = Mjonot.erota(sb, '|', tyokone);
        rekisterinumero = Mjonot.erota(sb, '|', rekisterinumero);
        ytunnus = Mjonot.erota(sb, '|', ytunnus);
        puhelinnro = Mjonot.erota(sb, '|', puhelinnro);
        tyotehtava = Mjonot.erota(sb, '|', tyotehtava);
        tila = Mjonot.erota(sb, '|', tila);
        tunnit = Mjonot.erota(sb, '|', tunnit);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }
    
    
    /**
     * Palauttaa koneen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kone tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kone kone = new Kone();
     *   kone.parse("   1   |  2  |   ei ole  | 1424546-2 | 0403213243 | 2 | 1 | ");
     *   kone.toString()    === "1|2|ei ole|1424546-2|0403213243|2|1|";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + tyokone + "|" + rekisterinumero + "|" + ytunnus + "|" + puhelinnro + "|" + tyotehtava + "|" + tila + "|" + tunnit + "|" + lisatietoja;
    }
    
    
    /**
     * Apumetodi, jolla saadaan luotoua koneelle satunnainen Y-tunnus, jotta nähdään ettei kahdella koneella olisi
     * samoja tietoja.
     */
    public void taytaKoneTiedoilla() {
        
        String apuytunnus = arvoYtunnus();
        taytaKoneTiedoilla(apuytunnus);
    
    }
    
    
    /**
     * Tehdään identtinen klooni koneesta
     * @return Object kloonattu kone 
     */
    @Override
    public Kone clone() throws CloneNotSupportedException {
        Kone uusi = (Kone) super.clone();
        return uusi;
    }
    
    
    /**
     * Tulostetaan koneen tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println(String.format("%03d", tunnusNro) + "  " + this.getTyokone() + "  " + rekisterinumero);
        out.println("Yrityksen tiedot:  " + ytunnus + "  " + puhelinnro);
        out.println(tyotehtava + "  " + tila);
        out.println("Lisätietoja: " + lisatietoja);
    
    }
    
    
    /**
     * Tulostaa koneen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
    
    }
    
       
    /**
     * Testi pääohjelma, jolla kokeillaan uusien kone olioiden luomista
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kone kaivinkone = new Kone();
        Kone kaivinkone2 = new Kone();
        kaivinkone.rekisteroi();
        kaivinkone2.rekisteroi();
        kaivinkone.taytaKoneTiedoilla();
        kaivinkone2.taytaKoneTiedoilla();
        kaivinkone.tulosta(System.out);
        kaivinkone2.tulosta(System.out);
                     
    }

}
