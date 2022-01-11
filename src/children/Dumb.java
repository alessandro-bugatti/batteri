package children;
import main.Food;
public class Dumb extends main.Batterio{
    private boolean goDown;
    public Dumb() {
        goDown = true;
    }
    @Override
    protected void move() {
        if (goDown) {
            y++;
            if (y>Food.getHeight())
                goDown = false;
        } else {
            y--;
            if (y<0)
                goDown = true;
        }
        
    }
    @Override
    public main.Batterio clone() throws CloneNotSupportedException {
        Dumb clone = (Dumb)super.clone();
        clone.goDown = !goDown;
        return clone;
    }
}