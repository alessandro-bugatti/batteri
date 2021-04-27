package batteri_figli;
import batteri.Food;
import java.awt.Color;
/**
 * @author Gregorelli
 * @author Fusari
 * @version 1.1
 */
public class S12 extends batteri.Batterio {
    //direzione che percorre, usato nello switch per il movimento
    private byte direzione;
    //0: nord, 1: sud, 2: est, 3: ovest
    private static final byte DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;
    //true se ha mangiato nel ciclo precedente, false altrimenti
    private boolean ciboVicino;
    //definisce quanto è grando il raggio della ricerca vicina
    private static final int TERMINERICERCAVICINA = 7;
    //definisce quanto è grando il raggio della ricerca distante (nei primi 25 sec)
    private static final int TERMINERICERCADISTANTE = 130 + TERMINERICERCAVICINA;
    //nelle rette viene controllato un pixel su {INCREMENTO}
    private static final int INCREMENTO = 7;
    //se arrivano a questa distanza tornano indietro
    private static final int DISTANZADAIBORDI = 15;
    //varaibili usate dal costruttore per il posizionamento iniziale dei batteri
    private static final int XSS = 1024/10/2, YSS = 700/10/2;
    private static int xS=XSS, yS=YSS;
    private static int contatore = 0;
    public S12(int x, int y, Color c) {
        super(xS, yS, c); //definisce la posizione iniziale
        if (Food.getDistribution()==Food.Distribution.corner) {
            if (contatore<25) {
                xS = contatore;
                yS = contatore;
            } else if (contatore<50) {
                xS = Food.getWidth()-contatore+25;
                yS = contatore-25;
            } else if (contatore<75) {
                xS = contatore-50;
                yS = Food.getHeight()-contatore+50;
            } else {
                xS = Food.getWidth()-contatore+75;
                yS = Food.getHeight()-contatore+75;
            }
            contatore++;
        }
        else {
            xS+=XSS*2;
            if (xS+XSS>Food.getWidth()) {xS=XSS; yS+=YSS*2;}
            direzione = (byte)(Math.random() * 4); //direzione scelta casualmente
            ciboVicino = false;
        }
    }
    /**
     * Compie una ricerca a simil-spirale, controllando inizialmente le celle con un costo minore 
     * e le controlla tutte fino a quelle che costano {distanzaDiRicerca} incluse
     * @param distanzaDiRicerca
     * @return 
     */
    private boolean ricercaVicina(int distanzaDiRicerca) {
        for (int i=1; i<distanzaDiRicerca; i++) {
            int tx = x, ty = y-i;
            //inizia dalla casella più a nord
            for (int j=1; j<=i; j++) //scende {+y} a destra {+x}
                if (Food.isFood(++tx, ++ty)) {x = tx; y = ty; return true;}
            for (int j=1; j<=i; j++) //scende {+y} a sinistra {-x}
                if (Food.isFood(--tx, ++ty)) {x = tx; y = ty; return true;}
            for (int j=1; j<=i; j++) //sale {-y} a sinistra {-x}
                if (Food.isFood(--tx, --ty)) {x = tx; y = ty; return true;}
            for (int j=1; j<=i; j++)  //sale {-y} a destra {+x}
                if (Food.isFood(++tx, --ty)) {x = tx; y = ty; return true;}
        }
        return false;
    }
    /**
     * Parte dalle caselle vicine ed arriva fino a {distanzaDiRicerca} 
     * controlla rette orizzontali, verticali ed a vari livelli in obliquo
     * Non controlla tutte le celle delle rete ma solo una ogni {incremento}
     * @param distanzaDiRicerca
     * @param incremento
     * @return 
     */
    private boolean ricercaDistante(int distanzaDiRicerca, int incremento) {
        for (int j=TERMINERICERCAVICINA+2; j<distanzaDiRicerca; j+=incremento) {
            int mezzo = j/2, unTerzo = j/3, dueTerzi = unTerzo*2;
            if (Food.isFood(x+j, y)) { //verso est
                x+=j; return true;
            } else if (Food.isFood(x-j, y)) { //verso ovest
                x-=j; return true;
            } else if (Food.isFood(x, y+j)) { //verso sud
                y+=j; return true;
            } else if (Food.isFood(x, y-j)) { //verso nord
                y-=j; return true;
            } else if (Food.isFood(x+mezzo, y+mezzo)) { //verso sud-est
                x+=mezzo; y+=mezzo; return true;
            } else if (Food.isFood(x-mezzo, y-mezzo)) { //nord-ovest
                x-=mezzo; y-=mezzo; return true;
            } else if (Food.isFood(x-mezzo, y+mezzo)) { //sud-ovest
                x-=mezzo; y+=mezzo; return true;
            } else if (Food.isFood(x+mezzo, y-mezzo)) { //nord-est
                x+=mezzo; y-=mezzo; return true;
            }  else if (Food.isFood(x-dueTerzi, y-unTerzo)) { //ovest-nord-ovest
                x-=dueTerzi; y-=unTerzo; return true;
            } else if (Food.isFood(x-unTerzo, y-dueTerzi)) { //nord-nord-ovest
                x-=unTerzo; y-=dueTerzi; return true;
            } else if (Food.isFood(x+unTerzo, y-dueTerzi)) { //nord-nord-est
                x+=unTerzo; y-=dueTerzi; return true;
            } else if (Food.isFood(x+dueTerzi, y-unTerzo)) { //est-nord-est
                x+=dueTerzi; y-=unTerzo; return true;
            } else if (Food.isFood(x+dueTerzi, y+unTerzo)) { //est-sud-est
                x+=dueTerzi; y+=unTerzo; return true;
            } else if (Food.isFood(x+unTerzo, y+dueTerzi)) { //sud-sud-est
                x+=unTerzo; y+=dueTerzi; return true;
            } else if (Food.isFood(x-unTerzo, y+dueTerzi)) { //sud-sud-ovest
                x-=unTerzo; y+=dueTerzi; return true;
            } else if (Food.isFood(x-dueTerzi, y+unTerzo)) { //ovest-sud-ovest
                x-=dueTerzi; y+=unTerzo; return true;
            }
        }
        return false;
    }
    /**
     * Si muove di una casella verso {direzione} 
     * torna indietro quando arriva a {DISTANZADAIBORDI} dai bordi
     * @return 
     */
    private boolean movimento() {
        switch (this.direzione) {
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
        return true;
    }
    /**
     * 
     */
    private void m() {
        int dx = (int)(Math.random()*3) - 1;
        int dy = (int)(Math.random()*3) - 1;
        if (x+dx >= 0 && x+dx<Food.getWidth())
            x += dx; 
        if (y+dy >= 0 && y+dy<Food.getHeight())
            y += dy; 
    }
    /**
     * Metodo chiamato dal terreno per far muovere {this}
     */
    @Override
    public void move() {
        switch (Food.getDistribution()) {
            case corner:
                if (ricercaVicina(30))
                    return;
                m();
                return;
            case random:
                if (ricercaVicina(30))
                    return;
                movimento();
                return;
            default:
                if (ciboVicino) {
                    if (ricercaVicina(TERMINERICERCAVICINA))
                        return;
                }
                if (!ciboVicino) {
                    ciboVicino = ricercaDistante(TERMINERICERCADISTANTE+20, INCREMENTO);
                } else {
                    ciboVicino = ricercaDistante(TERMINERICERCADISTANTE-20, INCREMENTO);
                }
                if (ciboVicino)
                    return;
                movimento();
        }
    }
    /**
     * Creazione di un nuovo batterio di tipo {TheStrategist} castato a {Batterio}
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public batteri.Batterio clone() throws CloneNotSupportedException {
        S12 copia = (S12) super.clone();
        copia.ciboVicino = false;
        //Il clone andrà a sinistra od a destra rispetto la direzione del padre
        if (direzione == DOWN || direzione == UP)
            copia.direzione = (byte)(Math.random()*2+2);
        else
            copia.direzione = (byte)(Math.random()*2);
        /*int h[] = new int[2];
        for (int i=0; i<3; i++)
            h[i] = i;*/
        return copia;
    }
}
