package batteri_figli;
import batteri.Batterio;
import batteri.Food;
import java.awt.Color;
public class Tontino extends batteri.Batterio{
    private boolean scendere;
    private batteri.Food f;
    public Tontino(int x, int y, Color c, Food f) {
        super(x, y, c, f);
        this.f = f;
        scendere = true;
    }
    @Override
    protected void sposta() {
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
    public batteri.Batterio clone() throws CloneNotSupportedException {
        return (Batterio) super.clone();
    }
}
