package batteri_figli;
import batteri.Food;
import java.awt.Color;
public class Tontino3 extends batteri.Batterio{
    private boolean scendere;
    private batteri.Food f;
    public Tontino3(int x, int y, Color c, Food f) {
        super(x, y, c, f);
        this.f = f;
        scendere = true;
    }
    @Override
    protected void Sposta() {
        if (scendere) {
            if (y>getFoodHeight()) {
                scendere = false;
            } else {
                y++;
            }
        } else {
            if (y<0) {
                scendere = true;
            } else {
                y--;
            }
        }
    }
    @Override
    protected batteri.Batterio clone() {
        Tontino3 a = new Tontino3(x,y,getColore(),f);
        return a;
    }
}
