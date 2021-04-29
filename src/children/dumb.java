package children;
import main.Food;
public class dumb extends main.Batterio{
    private boolean scendere;
    private enum e {g,t}
    public dumb() {
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
    public main.Batterio clone() throws CloneNotSupportedException {
        dumb clone = (dumb)super.clone();
        clone.scendere = !scendere;
        return clone;
    }
}
