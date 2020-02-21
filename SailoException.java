package rekisteri;


/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Tommi Prusti
 * @version 24.2.2019
 *
 */
public class SailoException extends Exception {
    
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
     * @param message Poikkeuksen antama viesti käyttäjälle
     */
    public SailoException(String message) {
        
        super(message);
    
    }
}