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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * Classe che rappresenta il terreno di gara
 * @author Alessandro Bugatti
 * @version 0.1
 */

public class Terrain extends JPanel {
    private final Food food;
    private final Color sfondo;
    private final LinkedList<Batterio> batteri;
    private final HashMap<String,Integer> numeroBatteri;
    public Terrain(Food f, LinkedList<Batterio> l, Color s,HashMap<String,Integer> numeroBatteri) {
        food = f;
        batteri = l;
        sfondo = s;
        this.numeroBatteri = numeroBatteri; 
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1024,700);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(sfondo);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        LinkedList<Batterio> babies = new LinkedList<>();
        for(Iterator<Batterio> i = batteri.iterator(); i.hasNext();) {
            Batterio batterio = i.next();
            g.setColor(sfondo);
            g.fillRect(batterio.getX(), batterio.getY(), 2, 2);
            try {
                batterio.Run();
            } catch(Exception e) {
                System.out.println("Eccezione: " + e + " -> " + batterio.getClass().getName());
            }
            String tipo_batterio = batterio.getClass().getName().replace("batteri_figli.", "");
            if (batterio.Morto()) {
                numeroBatteri.put(tipo_batterio, numeroBatteri.get(tipo_batterio)-1);
                i.remove();
            }
            else if (batterio.Fecondo()) {
                try {
                    Batterio b = (Batterio) batterio.clone();
                    babies.add(b);
                    numeroBatteri.put(tipo_batterio, numeroBatteri.get(tipo_batterio)+1);
                } catch (CloneNotSupportedException e) {
                    System.out.println(e);
                }
            }
            else {
                g.setColor(batterio.getColore());
                g.fillRect(batterio.getX(), batterio.getY(), 3, 3);
            }
        }
        batteri.addAll(babies);
        //Ridisegna il cibo a ogni ciclo
        g.setColor(Color.GREEN);
        for (int i = 0; i < food.getWidth(); i++)
            for (int j = 0; j < food.getHeight(); j++)
                if (food.isFood(i, j))
                    g.fillRect(i, j, 2, 2);
    } 
    public void toggleFood() {
        food.squareDistribution(50, 500);
    }
}
