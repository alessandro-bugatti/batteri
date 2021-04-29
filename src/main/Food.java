package main;
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
     * Tipo di distribuzione da effettuare
     */
    private static Distribution distributionType;
    /**
     * Quantità di cibo prodotta, nei modi definiti in {distributionType}
     */
    private static int foodQuantity;
    /**
     * Grandezza in pixel dei quadrati di cibo
     */
    private static final int foodDimension = 50;
    /**
     * Contiene i vari tipi di distribuzione del cibo
     */
    public static enum Distribution {
        square, corner, random
    }
    /**
     * @param w Larghezza della matrice
     * @param h Altezza della matrice
     */
    private Food(int w, int h, Distribution dt, int foodQuantity) {
        width = w;
        height = h;
        food = new boolean[w][h];
        random = new Random();
        Food.distributionType = dt;
        Food.foodQuantity = foodQuantity;
    }
    /**
     * Distribuisce il cibo secondo una distribuzione quadrata
     * @param l lato del quadrato della distribuzione   
     * @param q quantità di cibo da distribuire
     */
    private void squareDistribution(int l, int q) {
        int randx = random.nextInt(width - l);
        int randy = random.nextInt(height - l);
        for (int i = 0; i < q; i++)
            food[random.nextInt(l) + randx][random.nextInt(l) + randy] = true;
    }
    /**
     * Distribuisce il cibo secondo una distribuzione casuale
     * @param q quantità di cibo da distribuire
     */
    private void randomDistribution(int q) {
        for (int i = 0; i < q; i++)
            food[random.nextInt(width-1)][random.nextInt(height-1)] = true;
    }
    /**
     * Distribuisce il cibo secondo una distribuzione che distribuisce solo negli angoli
     * @param radius raggio del cerchio dove verrà distribuito il cibo
     * @param q quantità di cibo da distribuire
     */
    private void cornerDistribution(int radius, int q) {
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
     * 
     */
    public void toggle() {
        switch (Food.distributionType) {
            case square:
                this.squareDistribution(foodDimension, foodQuantity);
                break;
            case corner:
                this.cornerDistribution(foodDimension, foodQuantity);
                break;
            default:
                this.randomDistribution(foodQuantity);
                break;
        }
    }
    /**
     * 
     * @return 
     */
    public static Distribution getDistribution() {
        return Food.distributionType;
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
    /**
     * Classe utilizzata per creare oggetti di tipo Food in modo tale che
     * si possa creare solo un oggetto di questo tipo e si possa richiedere il riferimento
     * a quell'oggetto una volta soltanto
     */
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
         * Tipo di distribuzione da effettuare
         */
        private static Distribution distributionType;
        /**
         * Quantità di cibo prodotta, nei modi definiti in {distributionType}
         */
        private static int foodQuantity;
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
        public Builder (int w, int h, Distribution dt, int fq) {
            width = w;
            height = h;
            Builder.distributionType = dt;
            Builder.foodQuantity = fq;
        }
        /**
         * crea il riferimento al cibo e lo ritorna, può essere chiamato una sola volta
         * @return riferimento al cibo
         * @throws NullPointerException 
         */
        synchronized public Food build() throws NullPointerException {
            if (!istanziato) {
                istanziato = true;
                riferimento = new Food(width, height, distributionType, foodQuantity);
                return riferimento;
            }
            return null;
        }
        /**
         * ritorna il riferimento al cibo ma può essere chiamato una sola volta
         * @return riferimento al cibo
         * @throws NullPointerException 
         */
        synchronized static public Food getFood() throws NullPointerException {
            if (riferibile) {
                riferibile = false;
                return riferimento;
            }
            return null;
        }
    }
}
