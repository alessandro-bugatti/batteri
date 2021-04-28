package batteri_figli;
import batteri.Food;
import java.awt.Color;
/**
 * @author Gregorelli
 * @author Fusari
 * @version 0.16
 */
public class S16 extends batteri.Batterio {
    //direzione che percorre, usato nello switch per il movimento
    //0: nord, 1: sud, 2: est, 3: ovest
    private byte direzione;
    private static final byte DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;
    //definisce quanto è grando il raggio della ricerca vicina
    private static final int TERMINERICERCAVICINA = 5;
    //definisce quanto è grando il raggio della ricerca distante (nei primi 25 sec)
    private static int termineRicercaDistante = 200;
    //definisce quanto è grando il raggio della ricerca distante
    private static final int CONTINUO = 110 + TERMINERICERCAVICINA;
    //se arrivano a questa distanza tornando indietro
    private static final int DISTANZADAIBORDI = 15;
    //usate dal costruttore per il posizionamento iniziale
    private static final int XSS = 1024/10/2, YSS = 700/10/2;
    private static int xS=XSS, yS=YSS;
    public S16() {
        xS+=XSS*2;
        if (xS+XSS>Food.getWidth()) {xS=XSS; yS+=YSS*2;}
        direzione = (byte)(Math.random() * 4); //direzione scelta casualmente
    }
    @Override
    public void move() {
        /*ricerca vicina (60 controlli)
        Compie una ricerca a spirale, controllando inizilmente le celle con un costo minore
        e le controlla tutte fino quando non arriva a quelle che costano {TERMINERICERCAVICINA}*/
        for (int i=1; i<TERMINERICERCAVICINA; i++) { //numero di quadrati
            int tx = x, ty = y-i;
            for (int j=1; j<=i; j++) //scendere {+y} a destra {+x}
                if (Food.isFood(++tx, ++ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++) //scendere {+y} a sinistra {-x}
                if (Food.isFood(--tx, ++ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++) //alzarsi {-y} a sinistra {-x}
                if (Food.isFood(--tx, --ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++)  //alzarsi {-y} a destra {+x}
                if (Food.isFood(++tx, --ty)) {x = tx; y = ty; return;}
        }
        /* ricerca distante (260 controlli)
        parte dalle caselle vicine ed arriva fino a {termineRicercaDistante}
        controlla in orizzontale, verticale e vari livelli di obliquo */
        for (int j=TERMINERICERCAVICINA+1; j<termineRicercaDistante; j+=7) {
            int mezzo = j/2, unTerzo = j/3, dueTerzi = unTerzo*2;
            if (Food.isFood(x+j, y)) { //verso est
                x+=j; return;
            } else if (Food.isFood(x-j, y)) { //verso ovest
                x-=j; return;
            } else if (Food.isFood(x, y+j)) { //verso sud
                y+=j; return;
            } else if (Food.isFood(x, y-j)) { //verso nord
                y-=j; return;
            } else if (Food.isFood(x+mezzo, y+mezzo)) { //verso sud-est
                x+=mezzo; y+=mezzo; return;
            } else if (Food.isFood(x-mezzo, y-mezzo)) { //nord-ovest
                x-=mezzo; y-=mezzo; return;
            } else if (Food.isFood(x-mezzo, y+mezzo)) { //sud-ovest
                x-=mezzo; y+=mezzo; return;
            } else if (Food.isFood(x+mezzo, y-mezzo)) { //nord-est
                x+=mezzo; y-=mezzo; return;
            }  else if (Food.isFood(x-dueTerzi, y-unTerzo)) { //ovest-nord-ovest
                x-=dueTerzi; y-=unTerzo; return;
            } else if (Food.isFood(x-unTerzo, y-dueTerzi)) { //nord-nord-ovest
                x-=unTerzo; y-=dueTerzi; return;
            } else if (Food.isFood(x+unTerzo, y-dueTerzi)) { //nord-nord-est
                x+=unTerzo; y-=dueTerzi; return;
            } else if (Food.isFood(x+dueTerzi, y-unTerzo)) { //est-nord-est
                x+=dueTerzi; y-=unTerzo; return;
            } else if (Food.isFood(x+dueTerzi, y+unTerzo)) { //est-sud-est
                x+=dueTerzi; y+=unTerzo; return;
            } else if (Food.isFood(x+unTerzo, y+dueTerzi)) { //sud-sud-est
                x+=unTerzo; y+=dueTerzi; return;
            } else if (Food.isFood(x-unTerzo, y+dueTerzi)) { //sud-sud-ovest
                x-=unTerzo; y+=dueTerzi; return;
            } else if (Food.isFood(x-dueTerzi, y+unTerzo)) { //ovest-sud-ovest
                x-=dueTerzi; y+=unTerzo; return;
            }
        }
        /* movimento (eseguito solo se non trova il cibo)
        si muove di una casella verso la direzione che deve percorrere
        torna indietro quando arriva a {DISTANZADAIBORDI} */
        switch (direzione) {
            case UP: //nord
                if (y-1>DISTANZADAIBORDI) y--;
                else {direzione = DOWN; y++;}
                break;
            case DOWN: //sud
                if (y+1<Food.getHeight()-DISTANZADAIBORDI) y++;
                else {direzione = UP; y--;}
                break;
            case RIGHT: //est
                if (x+1<Food.getWidth()-DISTANZADAIBORDI) x++;
                else {direzione = LEFT; x--;}
                break;
            default: //ovest
                if (x-1>DISTANZADAIBORDI) x--;
                else {direzione = RIGHT; x++;}
                break;
        }
    }
    @Override
    public batteri.Batterio clone() throws CloneNotSupportedException {
        termineRicercaDistante = CONTINUO;
        
            S16 a = (S16)super.clone();
            if (direzione == DOWN || direzione == UP)
                a.direzione = (byte)(Math.random()*2+2);
            else
                a.direzione = (byte)(Math.random()*2);
            return a;
    }
}
