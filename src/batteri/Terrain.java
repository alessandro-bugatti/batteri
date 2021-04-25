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
    public static Food food;
    private final Color sfondo;
    private final LinkedList<Batterio> batteri;
    private final HashMap<String,Integer> numeroBatteri;
    public Terrain(Food f, LinkedList<Batterio> l, Color s,HashMap<String,Integer> numeroBatteri) {
        batteri = l;
        sfondo = s;
        this.numeroBatteri = numeroBatteri; 
        food = f;
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
            String tipo_batterio = batterio.getClass().getName().replace("batteri_figli.", "");
            batterio.run();
            if (batterio.isDead()) {
                numeroBatteri.put(tipo_batterio, numeroBatteri.get(tipo_batterio)-1);
                i.remove();
            }
            else if (batterio.isReadyForCloning()) {
                try {
                    Batterio b = (Batterio) batterio.clone();
                    babies.add(b);
                    numeroBatteri.put(tipo_batterio, numeroBatteri.get(tipo_batterio)+1);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else {
                g.setColor(batterio.getColor());
                g.fillRect(batterio.getX(), batterio.getY(), 3, 3);
            }
        }
        batteri.addAll(babies);
        //Ridisegna il cibo a ogni ciclo
        g.setColor(Color.GREEN);
        for (int i = 0; i < Food.getWidth(); i++)
            for (int j = 0; j < Food.getHeight(); j++)
                if (Food.isFood(i, j))
                    g.fillRect(i, j, 2, 2);
    }
    public void toggleFood() {
        food.squareDistribution(50, 500);
    }
}
