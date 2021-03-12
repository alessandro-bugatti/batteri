/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package batteri;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author Alessandro Bugatti 2015
 */
public class mainForm extends javax.swing.JFrame {

    /**
     * Creates new form mainForm
     * 
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.io.IOException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.net.URISyntaxException
     */
    public mainForm() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, URISyntaxException {
        initComponents();
        running = false;
        food = new Food(1024,700);
        inizializzaBatteri();
        terrain = new Terrain(food, batteri, jPanelTerrain.getBackground(),numeroBatteri);
        this.jPanelTerrain.add(terrain);
        values = new javax.swing.JLabel[10];
        for (int i = 0; i < nomiBatteri.size(); i++)
            {
                values[i] = new javax.swing.JLabel(nomiBatteri.get(i)+
                        " " + numeroBatteri.get(nomiBatteri.get(i)));
                values[i].setForeground(coloreBatteri.get(nomiBatteri.get(i)));
                this.jPanelResult.add(values[i]);
            } 
        javax.swing.JButton btnStart = new javax.swing.JButton("Start");
        btnStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                   timerUpdateSimulation.start();
                   timerUpdateResult.start();
                   timerUpdateFood.start();
                }
            }
        );
        this.jPanelResult.add(btnStart);
        javax.swing.JButton btnStop = new javax.swing.JButton("Stop");
        btnStop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                   timerUpdateSimulation.stop();
                   timerUpdateResult.stop();
                   timerUpdateFood.stop();
                }
            }
        );
        this.jPanelResult.add(btnStop);
        pack();
        //Timer per l'aggiornamento della simulazione
        ActionListener taskUpdateSimulation;
        taskUpdateSimulation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Lo scopo di running è evitare che riparta un ciclo
                // di ridisegno del campo gara mentre ne è già in corso uno
                if (running) return;
                running = true;
                terrain.repaint();
                running = false;
            }

        };
        timerUpdateSimulation = new Timer(50, taskUpdateSimulation); 
        //timer.setInitialDelay(2000);        
        //timerUpdateSimulation.setRepeats(true);
        
        
        //Timer per l'aggiunta di cibo
        ActionListener taskUpdateFood;
        taskUpdateFood = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terrain.toggleFood();
            }

        };
        timerUpdateFood = new Timer(1000, taskUpdateFood); 
        //timer.setInitialDelay(2000);        
        timerUpdateFood.setRepeats(true);
        //timerUpdateFood.start();
        
        //Timer per l'aggiornamento del pannello dei dati
        ActionListener taskUpdateResult;
        taskUpdateResult = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < nomiBatteri.size(); i++)
                {
                    values[i].setText(nomiBatteri.get(i)+
                            " " + numeroBatteri.get(nomiBatteri.get(i)));
                } 
            }

        };
        timerUpdateResult = new Timer(1000, taskUpdateResult); 
        //timer.setInitialDelay(2000);        
        //timerUpdateResult.setRepeats(true);
        //timerUpdateResult.start();
     }
    
    /**
     * Funzione che recupera il nome di tutti i batteri ereditati che si trovano
     * nel package batteri_figli
     */
    private List<String> recuperaNomi() throws IOException, URISyntaxException
    {
        List<String> nomi= new ArrayList<String>(), files;
        Path path = new File(
        this.getClass().getResource("../batteri_figli/Tontino.class").toURI()
).getParentFile().toPath();
        files = Files.walk(path)
                                 .map(Path::getFileName)
                                 .map(Path::toString)
                                 .filter(n -> n.endsWith(".class"))
                                 .collect(Collectors.toList());
        for(String nome:files)
            nomi.add(nome.replace(".class", ""));
        return nomi;
    }        
            
    
    /**
     * Inizializza la lista dei batteri
     */
    private void inizializzaBatteri() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, URISyntaxException{
        batteri = new LinkedList<>();
        Random r = new Random();
        numeroBatteri = new HashMap<>();
        coloreBatteri = new HashMap<>();
        nomiBatteri = new ArrayList<>();
        ArrayList<Color> colori = new ArrayList<>();
        colori.add(Color.RED);
        colori.add(Color.BLUE);
        colori.add(Color.GREEN);
        colori.add(Color.YELLOW);
        colori.add(Color.MAGENTA);
        colori.add(Color.ORANGE);
        colori.add(Color.PINK);
        colori.add(Color.CYAN);
        colori.add(Color.DARK_GRAY);
        colori.add(Color.GRAY);
        colori.add(Color.BLACK);
        colori.add(Color.LIGHT_GRAY);
        nomiBatteri = (ArrayList<String>)recuperaNomi();
        System.out.println(nomiBatteri);
        int j = 0;
        for (String Batterio: nomiBatteri)
        {
            Color c = colori.get(j++);
            for (int i = 0; i < 100; i++)
                batteri.add((Batterio)Class.forName("batteri_figli." + Batterio).
                                    getConstructor(Integer.TYPE,Integer.TYPE,Color.class,Food.class).
                                    newInstance(r.nextInt(food.getWidth()),
                                r.nextInt(food.getHeight()),
                                c,food));
            coloreBatteri.put(Batterio, c);
            numeroBatteri.put(Batterio, 100);
        }
            
       
        
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.1111111
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelResult = new javax.swing.JPanel();
        jPanelTerrain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bacteria");
        setResizable(false);

        jPanelResult.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelResult.setMinimumSize(new java.awt.Dimension(150, 100));
        jPanelResult.setLayout(new javax.swing.BoxLayout(jPanelResult, javax.swing.BoxLayout.Y_AXIS));
        getContentPane().add(jPanelResult, java.awt.BorderLayout.LINE_END);

        jPanelTerrain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTerrain.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelTerrain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new mainForm().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelResult;
    private javax.swing.JPanel jPanelTerrain;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JLabel values[];
    private Terrain terrain;
    private javax.swing.Timer timerUpdateSimulation;
    private javax.swing.Timer timerUpdateFood;
    private javax.swing.Timer timerUpdateResult;
    private LinkedList<Batterio> batteri;
    private Food food;
    private boolean running;
    private HashMap<String,Integer> numeroBatteri;
    private HashMap<String,Color> coloreBatteri;
    private ArrayList<String> nomiBatteri;
    
}
