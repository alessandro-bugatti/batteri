package batteri_figli;
import batteri.Food;
import java.awt.Color;
public class Tontino extends batteri.Batterio{
    private boolean scendere;
    private enum h{
        s,f,g
    }
    public Tontino(int x, int y, Color c) {
        super(x, y, c);
        /*int a[] = new int[2];
        for (int i=0; i<3; i++) {
            a[i] = i;
        }*/
        scendere = true;
    }
    @Override
    protected void move() {
        if (scendere)
            if (y>Food.getHeight())
                scendere = false;
            else
                y++;
        else
            if (y<0)
                scendere = true;
            else
                y--;
        
    }
    @Override
    public batteri.Batterio clone() throws CloneNotSupportedException {
        Tontino t = (Tontino) super.clone();
        t.scendere = true;
        return t;
    }
}
