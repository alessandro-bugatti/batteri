package batteri_figli;
import batteri.Food;
import java.awt.Color;
/**
 * @author michele
 * @version 0.6
 */
public class S6 extends batteri.Batterio {
    //direzione che percorre, usato nello switch per il movimento
    //0: nord, 1: sud, 2: est, 3: ovest
    private byte direzione;
    //inizia controllando in un raggio di {massimo} celle
    private static int massimo = 100;
    //dopo circa 25 secondi diminuisce le caselle da controllate a {continuous}
    private static final int CONTINUO = 100;
    //definisce per quante cell è necessario fare una ricerca completa
    //dopo {RICERCAVICINA} verrà controllavverrà una ata solo una cella ogni due
    private static final int RICERCAVICINA = 50;
    //se arrivano a questa distanza cambiano direzione, tornando indietro
    private static final int DISTANZADAIBORDI = 10;
    public S6(int x, int y, Color c) {
        //definire la posizione iniziale
        super(x, y, c);
        direzione = (byte)(Math.random() * 4); //direzione scelta casualmente
    }
    @Override
    public void move() {
        for (int j=1; j<massimo; j++) {
            if (j>RICERCAVICINA) j++;
            int mezzo = j/2, dueTerzi = j/3*2, unTerzo = j/3;
            if (Food.isFood(x+j, y)) { //verso est
                x+=j;
                return;
            } else if (Food.isFood(x-j, y)) { //verso ovest
                x-=j;
                return;
            } else if (Food.isFood(x, y+j)) { //verso sud
                y+=j;
                return;
            } else if (Food.isFood(x, y-j)) { //verso nord
                y-=j;
                return;
            } else if (Food.isFood(x+mezzo, y+mezzo)) { //verso sud-est
                x+=mezzo;
                y+=mezzo;
                return;
            } else if (Food.isFood(x-mezzo, y-mezzo)) { //nord-ovest
                x-=mezzo;
                y-=mezzo;
                return;
            } else if (Food.isFood(x-mezzo, y+mezzo)) { //sud-ovest
                x-=mezzo;
                y+=mezzo;
                return;
            } else if (Food.isFood(x+mezzo, y-mezzo)) { //nord-est
                x+=mezzo;
                y-=mezzo;
                return;
            } else if (Food.isFood(x-dueTerzi, y-unTerzo)) { //ovest-nord-ovest
                x-=dueTerzi;
                y-=unTerzo;
                return;
            } else if (Food.isFood(x-unTerzo, y-dueTerzi)) { //nord-nord-ovest
                x-=unTerzo;
                y-=dueTerzi;
                return;
            } else if (Food.isFood(x+unTerzo, y-dueTerzi)) { //nord-nord-est
                x+=unTerzo;
                y-=dueTerzi;
                return;
            } else if (Food.isFood(x+dueTerzi, y-unTerzo)) { //est-nord-est
                x+=dueTerzi;
                y-=unTerzo;
                return;
            } else if (Food.isFood(x+dueTerzi, y+unTerzo)) { //est-sud-est
                x+=dueTerzi;
                y+=unTerzo;
                return;
            } else if (Food.isFood(x+unTerzo, y+dueTerzi)) { //sud-sud-est
                x+=unTerzo;
                y+=dueTerzi;
                return;
            } else if (Food.isFood(x-unTerzo, y+dueTerzi)) { //sud-sud-ovest
                x-=unTerzo;
                y+=dueTerzi;
                return;
            } else if (Food.isFood(x-dueTerzi, y+unTerzo)) { //ovest-sud-ovest
                x-=dueTerzi;
                y+=unTerzo;
                return;
            }
        }
            /* movimento (eseguito solo se non trova il cibo)
        si muove di una casella verso la direzione che deve percorrere
        è solo un segnaposto, è DA RIFARE!!! 
        - potrebbe non arrivare fino al bordo ma girarsi prima ma potrebbe anche
        obbligare i batteri a stare più vicini e lontani dal cibo nato sui bordi */
        switch (direzione) {
            case 0: //nord
                if (y-1>DISTANZADAIBORDI) {
                    y--;
                } else {
                    direzione = 1;
                    y++;
                }
                break;
            case 1: //sud
                if (y+1<Food.getHeight()-DISTANZADAIBORDI) {
                    y++;
                } else {
                    direzione = 0;
                    y--;
                }
                break;
            case 2: //est
                if (x+1<Food.getWidth()-DISTANZADAIBORDI) {
                    x++;
                } else {
                    direzione = 3;
                    x--;
                }
                break;
            case 3: //ovest
                if (x-1>DISTANZADAIBORDI) {
                    x--;
                } else {
                    direzione = 2;
                    x++;
                }
                break;
        }
    }
    @Override
    public batteri.Batterio clone() throws CloneNotSupportedException {
        massimo = CONTINUO;
        //si potrebbe fare meglio di farla casuale?
        direzione = (byte)(Math.random()*4);
        return (S6)super.clone();
    }
}
