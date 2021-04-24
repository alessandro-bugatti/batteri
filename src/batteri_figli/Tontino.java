package batteri_figli;
import java.awt.Color;
public class Tontino extends batteri.Batterio{
    private boolean scendere;
    public Tontino(int x, int y, Color c) {
        super(x, y, c);
        scendere = true;
    }
    @Override
    protected void sposta() {
        if (scendere)
            if (y>getFoodHeight())
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
