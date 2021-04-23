package batteri_figli;
import batteri.Batterio;
import java.awt.Color;
/**
 * @version 1.0
 */
public class TheStrategist extends batteri.Batterio {
    //direzione che percorre, usato nello switch per il movimento
    private byte direzione;
    //0: nord, 1: sud, 2: est, 3: ovest
    private static final byte DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;
    //definisce quanto è grando il raggio della ricerca vicina
    private static final int TERMINERICERCAVICINA = 4;
    //definisce quanto è grando il raggio della ricerca distante (nei primi 25 sec)
    private static int termineRicercaDistante = 300;
    //definisce quanto è grando il raggio della ricerca distante
    private static final int CONTINUO = 130 + TERMINERICERCAVICINA;
    //nelle rette viene controllato un pixel su {INCREMENTO}
    private static final int INCREMENTO = 7;
    //se arrivano a questa distanza tornano indietro
    private static final int DISTANZADAIBORDI = 15;
    //varaibili usate dal costruttore per il posizionamento iniziale dei batteri
    private static final int XSS = 1024/10/2, YSS = 700/10/2;
    private static int xS=XSS, yS=YSS;
    public TheStrategist(int x, int y, Color c, batteri.Food f) {
        super(xS, yS, c, f); //definisce la posizione iniziale
        xS+=XSS*2;
        if (xS+XSS>super.getFoodWidth()) {xS=XSS; yS+=YSS*2;}
        direzione = (byte)(Math.random() * 4); //direzione scelta casualmente
    }
    @Override
    public void Sposta() {
        /*ricerca vicina (40 controlli)
        Compie una ricerca a simil-spirale, controllando inizialmente le celle con un costo minore
        e le controlla tutte fino a quelle che costano {TERMINERICERCAVICINA} incluse */
        for (int i=1; i<TERMINERICERCAVICINA; i++) {
            int tx = x, ty = y-i;
            //inizia dalla casella più a nord
            for (int j=1; j<=i; j++) //scende {+y} a destra {+x}
                if (ControllaCibo(++tx, ++ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++) //scende {+y} a sinistra {-x}
                if (ControllaCibo(--tx, ++ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++) //sale {-y} a sinistra {-x}
                if (ControllaCibo(--tx, --ty)) {x = tx; y = ty; return;}
            for (int j=1; j<=i; j++)  //sale {-y} a destra {+x}
                if (ControllaCibo(++tx, --ty)) {x = tx; y = ty; return;}
        }
        /* ricerca distante (300 controlli)
        parte dalle caselle vicine ed arriva fino a {termineRicercaDistante}
        controlla in orizzontale, verticale e vari livelli di obliquo */
        for (int j=TERMINERICERCAVICINA+1; j<termineRicercaDistante; j+=INCREMENTO) {
            int mezzo = j/2, unTerzo = j/3, dueTerzi = unTerzo*2;
            if (ControllaCibo(x+j, y)) { //verso est
                x+=j; return;
            } else if (ControllaCibo(x-j, y)) { //verso ovest
                x-=j; return;
            } else if (ControllaCibo(x, y+j)) { //verso sud
                y+=j; return;
            } else if (ControllaCibo(x, y-j)) { //verso nord
                y-=j; return;
            } else if (ControllaCibo(x+mezzo, y+mezzo)) { //verso sud-est
                x+=mezzo; y+=mezzo; return;
            } else if (ControllaCibo(x-mezzo, y-mezzo)) { //nord-ovest
                x-=mezzo; y-=mezzo; return;
            } else if (ControllaCibo(x-mezzo, y+mezzo)) { //sud-ovest
                x-=mezzo; y+=mezzo; return;
            } else if (ControllaCibo(x+mezzo, y-mezzo)) { //nord-est
                x+=mezzo; y-=mezzo; return;
            }  else if (ControllaCibo(x-dueTerzi, y-unTerzo)) { //ovest-nord-ovest
                x-=dueTerzi; y-=unTerzo; return;
            } else if (ControllaCibo(x-unTerzo, y-dueTerzi)) { //nord-nord-ovest
                x-=unTerzo; y-=dueTerzi; return;
            } else if (ControllaCibo(x+unTerzo, y-dueTerzi)) { //nord-nord-est
                x+=unTerzo; y-=dueTerzi; return;
            } else if (ControllaCibo(x+dueTerzi, y-unTerzo)) { //est-nord-est
                x+=dueTerzi; y-=unTerzo; return;
            } else if (ControllaCibo(x+dueTerzi, y+unTerzo)) { //est-sud-est
                x+=dueTerzi; y+=unTerzo; return;
            } else if (ControllaCibo(x+unTerzo, y+dueTerzi)) { //sud-sud-est
                x+=unTerzo; y+=dueTerzi; return;
            } else if (ControllaCibo(x-unTerzo, y+dueTerzi)) { //sud-sud-ovest
                x-=unTerzo; y+=dueTerzi; return;
            } else if (ControllaCibo(x-dueTerzi, y+unTerzo)) { //ovest-sud-ovest
                x-=dueTerzi; y+=unTerzo; return;
            }
        }
        /* movimento (eseguito solo se non trova il cibo)
        si muove di una casella verso {direzione}
        torna indietro quando arriva a {DISTANZADAIBORDI} dai bordi*/
        switch (this.direzione) {
            case UP: //nord
                if (y-1>DISTANZADAIBORDI) y--;
                else {direzione = DOWN; y++;}
                break;
            case DOWN: //sud
                if (y+1<super.getFoodHeight()-DISTANZADAIBORDI) y++;
                else {direzione = UP; y--;}
                break;
            case RIGHT: //est
                if (x+1<super.getFoodWidth()-DISTANZADAIBORDI) x++;
                else {direzione = LEFT; x--;}
                break;
            default: //ovest
                if (x-1>DISTANZADAIBORDI) x--;
                else {direzione = RIGHT; x++;}
                break;
        }
    }
    @Override
    public Batterio clone() throws CloneNotSupportedException {
        //modifica il termine della ricerca distante (eseguito dopo circa 25 secondi dallo start)
        termineRicercaDistante = CONTINUO;
        TheStrategist a = (TheStrategist)super.clone();
        //il clone andrà a sinistra od a destra rispetto la direzione di {this}
        if (direzione == DOWN || direzione == UP)
            a.direzione = (byte)(Math.random()*2+2);
         else
            a.direzione = (byte)(Math.random()*2);
        return a;
    }
}
