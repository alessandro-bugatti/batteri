/*
  Copyright (C) 2013 Alessandro Bugatti (alessandro.bugatti@istruzione.it)

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation; either version 2
  of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package batteri;

import java.awt.Color;

/**
 * Classe astratta genitore della gerarchia dei batteri.
 * Ogni tipo diverso di batterio eredita da questa classe
 * @author Alessandro Bugatti
 */
abstract public class Batterio implements Cloneable {
    /**
     * Incremento della salute ogni qualvolta mangia il cibo
     */
    private final static int DELTA = 100;
    private final static int MAX_LIFE = 1500;
    private final static int MAX_HEALTH = 600;
    private final static int CICLO_RIPRODUTTIVO = 500;
    /**
     * Salute minima necessaria per la riproduzione
     */
    private final static int BUONA_SALUTE = 200;
    /**
     * Contiene la durata massima del batterio
     */
    private int eta;
    /**
     * Contiene la "salute" del batterio, arrivata a zero il batterio muore
     */
    private int salute;
    /**
     * Flag che indica se il batterio è maturo per la duplicazione o no
     * quando arriva a zero indica che può riprodursi
     */
    private int duplica;
    /**
    * Posizione x del batterio nello schermo
    */
    protected int x;
    /**
    * Posizione y del batterio nello schermo
    */
    protected int y;
    /**
     * Riferimento al cibo
     */
    private final Food food;
    /**
     * Colore tipo del batterio
     */
    private final Color colore;
    
    public Batterio(int x, int y, Color c, Food f) {
        this.x = x;
        this.y = y;
        this.colore=c;
        this.eta=(int)(Math.random()*MAX_LIFE)+500;
        this.salute=(int)(Math.random()*MAX_HEALTH)+200;
        this.duplica=CICLO_RIPRODUTTIVO+(int)(Math.random()*100);
        this.food = f;
    }
    /**
     * Sposta il batterio nel terreno. Deve essere ridefinita nelle classi
     * ereditate per dar loro un comportamento diverso
     */
    protected abstract void Sposta();
    /**
     * Controlla se c'è del cibo nella posizione occupata dal batterio
     * @return True se c'è del cibo, false altrimenti
     */
    protected final boolean ControllaCibo() {
        return food.isFood(getX(), getY());
    }
    /**
     * brief Controlla se c'è del cibo nella posizione x,y
     * @param X Posizione x dove cercare il cibo
     * @param Y Posizione y dove cercare il cibo
     * @return True se c'è del cibo, false altrimenti
     */
    protected final boolean ControllaCibo(int X, int Y) {
        return food.isFood(X, Y);
    }
    /**
     * Se nella posizione occupata dal batterio c'è del cibo lo mangia e incrementa la sua salute di DELTA
     */
    private final void Mangia() {
        if (ControllaCibo()) {
            food.eatFood(x, y);
            salute+=DELTA;
        }
    }
    /**
     * Controlla se un batterio è fecondo
     * @return True se è fecondo, false altrimenti
     */
    public final boolean Fecondo() {
        if (duplica == 0 && salute > BUONA_SALUTE) {
			duplica = BUONA_SALUTE;
			return true;
		}
        return false;
    }
    /**
     * Controlla se un batterio è morto o perchè troppo vecchio o perchè non ha abbastanza salute
     * @return True se è morto, false altrimenti
     */
    public final boolean Morto() {
        if (salute<1 || eta < 1)
            return true;
        else
            return false;
    }
    /**
     * Esegue le mosse del batterio
     */
    public final void Run() {
        if (Morto()) return ;
        int xprec = getX();
        int yprec = getY();
        Sposta(); /*Calcolo le nuove coord
     * inate del batterio*/
        Mangia(); /*Mangia l'eventuale cibo*/
        eta--; /*Faccio invecchiare il batterio*/
        /*Diminuisce la sua salute
        in funzione dello spostamento effettuato secondo una metrica Manhattan*/
        int sforzo = Math.abs(getX()-xprec) + Math.abs(getY()-yprec);
        salute-=sforzo;
        /*Diminuisce il tempo per la riproduzione, solo se si è mosso, altrimenti no*/
        if (duplica>0 && sforzo!=0)
            duplica--;
    }
    /**
     * @return x
     */
    public final int getX() {
        return x;
    }
    /**
     * @return y
     */
    public final int getY() {
        return y;
    }
    /**
     * @return il colore
     */
    public final Color getColore() {
        return colore;
    }
    /**
     * @return la larghezza del terreno
     */
    protected final int getFoodWidth() {
        return food.getWidth();
    }
    /**
     * @return l'altezza del terreno
     */
    protected final int getFoodHeight() {
        return food.getHeight();
    }
    /**
     * @return età
     */
    public final int getAge() {
        return this.eta;
    }
    /**
     * @return salute
     */
    public final int getHealth() {
        return this.salute;
    }
    /**
     * @return quanti cicli mancano alla duplicazione
     */
    public final int getDuplica() {
        return this.duplica;
    }
    /**
     * Clona il batterio in senso biologico
     * @return Un nuovo batterio creato con la stessa posizione di quello originale
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Batterio b = (Batterio)super.clone();
        b.eta=(int)(Math.random()*MAX_LIFE)+500;
        b.salute=(int)(Math.random()*MAX_HEALTH)+200;
        b.duplica=CICLO_RIPRODUTTIVO+(int)(Math.random()*100);
        return b;
    }
}
