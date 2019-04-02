package batteri_figli;

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



import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe d'esempio per la gara
 * @author Alessandro Bugatti &lt; alessandro.bugatti@gmail.com &gt;
 */

public class Tontino2 extends batteri.Batterio implements Cloneable{
    public Tontino2(int x, int y, Color c, batteri.Food f){
        super(x,y,c,f);
    }
    @Override
    protected void Sposta(){
        int dx = (int)(Math.random()*3) - 1;
        int dy = (int)(Math.random()*3) - 1;
        //if (x+dx >= 0 && x+dx<food.getWidth())
            x += dx; 
        //if (y+dy >= 0 && y+dy<food.getHeight())
            y += dy; 
    }
    
    @Override
    public batteri.Batterio Clona(){
       try { 
            return (Tontino2)this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Tontino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
}
