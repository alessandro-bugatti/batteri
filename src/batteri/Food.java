package batteri;
import java.util.Random;

/**
 * Classe che rappresenta la distribuzione di cibo
 * @author Alessandro Bugatti
 */

public class Food {
    /**
     * Matrice del cibo
     */
    private static boolean food[][];
    /**
     * Larghezza della matrice
     */
    private static int width;
    /**
     * Altezza della matrice
     */
    private static int height;
    /**
     * is used to generate a stream of pseudorandom numbers
     */
    private static Random random;
    /**
     * @param w Larghezza della matrice
     * @param h Altezza della matrice
     */
    private Food(int w, int h) {
        width = w;
        height = h;
        food = new boolean[w][h];
        random = new Random();
    }
    /**
     * Distribuisce il cibo secondo una distribuzione
     * quadrata
     * @param l lato del quadrato della distribuzione   
     * @param q quantità di cibo da distribuire
     */
    public void squareDistribution(int l, int q) {
        int randx = random.nextInt(width - l);
        int randy = random.nextInt(height - l);
        for (int i = 0; i < q; i++)
            food[random.nextInt(l) + randx][random.nextInt(l) + randy] = true;
    }
    /**
     * Distribuisce il cibo secondo una distribuzione
     * casuale
     * @param q quantità di cibo da distribuire
     */
    public void randomDistribution(int q) {
        for (int i = 0; i < q; i++)
            food[random.nextInt(width-1)][random.nextInt(height-1)] = true;
    }
    /**
     * Distribuisce il cibo secondo una distribuzione
     * che distribuisce solo negli angoli
     * @param radius raggio del cerchio dove verrà distribuito il cibo
     * @param q quantità di cibo da distribuire
     */
    public void cornerDistribution(int radius, int q) {
        int x=0,y=0,dx=1,dy=1;
        int corner = random.nextInt(4);
        switch(corner){
            case 0: 
                x=0;
                y=0;
                dx = 1;
                dy = 1;
                break;
            case 1: 
                x=width-1;
                y=0;
                dx = -1;
                dy = 1;
                break;
            case 2: 
                x=0;
                y=height-1;
                dx = 1;
                dy = -1;
                break;
            case 3: 
                x=width-1;
                y=height-1;
                dx = -1;
                dy = -1;
                break;
        }
        for (int i = 0; i < q; i++)
            food[x + dx*random.nextInt(radius)][y + dy*random.nextInt(radius)] = true;
    }
    /**
     * Controlla se c'è cibo in posizione x,y
     * @param x Coordinata x da controllare
     * @param y Coordinata y da controllare
     * @return True se c'è cibo in x,y, false altrimenti
     * <strong> Se x e y non sono valori validi per la matrice
     * ritorna false, evitando di sollevare un'eccezione</strong>
     */
    public static boolean isFood(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;
        return food[x][y];
    }
    /**
     * Mangia il cibo in posizione x,y
     * @param x Coordinata x 
     * @param y Coordinata y 
     */
    public void eatFood(int x, int y) {
        if (isFood(x,y))
            food[x][y] = false;
    }
    /**
     * @return Larghezza
     */
    public static int getWidth() {
        return width;
    }
    /**
     * @return Altezza
     */
    public static int getHeight() {
        return height;
    }
    public static final class Builder {
        /**
         * Larghezza della matrice
         */
        private static int width;
        /**
         * Altezza della matrice
         */
        private static int height;
        /**
         * flag, true se l'oggetto è già stato istanziato
         */
        private static boolean istanziato = false;
        /**
         * True se è possibile richiedere il riferimento al cibo
         */
        private static boolean riferibile = true;
        /**
         * Riferimento al cibo
         */
        private static Food riferimento = null;
        public Builder (int w, int h) {
            width = w;
            height = h;
        }
        synchronized public Food build() throws NullPointerException {
            if (!istanziato) {
                istanziato = true;
                riferimento = new Food(width, height);
                return riferimento;
            }
            return null;
        }
        synchronized static public Food getFood() throws NullPointerException {
            if (istanziato && riferibile) {
                riferibile = false;
                return riferimento;
            }
            return null;
        }
    }
}
