package kanta;

/**
 * Luokka Y-tunnuksen tarkistamiseksi
 * @author Tommi
 * @version 18.2.2019
 *
 */
public class YtunnusTarkistus {

    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvottavan luvun alaraja
     * @param yla arvottavan luvun ylaraja
     * @return luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    
    }

    
    /**
     * Arvotaan satunnainen  Y-tunnus
     * @return apuytunnus, joka annetaan koneelle
     */
    public static String arvoYtunnus() {
        
        String apuytunnus = "";
        int tarkistusmerkki = 0;
        
        int[] t = new int[7];
        int[] kertoimet = {2, 4, 8, 5, 10, 9, 7};
        int summa = 0;
        
        for (int i = 0; i < t.length; i++) {
            t[i] =  rand(1,9);
            apuytunnus = apuytunnus + Integer.toString(t[i]);
        }
        
        
        for (int i = t.length-1; i > 0; i--) {
            t[i] = t[i]*kertoimet[i];
        }
                
        for (int i = 0; i < t.length; i++) {
            summa += t[i];
        }
        
        if (summa % 11 == 1) return "Virheellinen Y-tunnus";
        if (summa % 11 == 0) tarkistusmerkki = 0;
        if (summa % 11 > 1) tarkistusmerkki = 11 - summa % 11;
        
        return apuytunnus + "-" + tarkistusmerkki;
        
    }
}
