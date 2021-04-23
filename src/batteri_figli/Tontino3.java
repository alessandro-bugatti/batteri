package batteri_figli;
import batteri.Batterio;
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
        int n[] = new int[3];
        for (int i=0; i<3; i++) {
            n[i]=i;
        }
    }
    @Override
    public batteri.Batterio clone() throws CloneNotSupportedException {
        return (Batterio) super.clone();
    }
}
