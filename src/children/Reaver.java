package children;

import main.Food;
import java.awt.Color;

/**
 *
 * @author Maffi, Mariottini
 */

/** TO DO:
 * IMPROVE EATING METHOD
 */

public class Reaver extends main.Batterio {
    private String dir;
    
    public Reaver(){
        int random = (int)(Math.random()*4);
        if(random == 0)
            dir = "UP";
        if(random == 1)
            dir = "DOWN";
        if(random == 2)
            dir = "LEFT";
        if(random == 3)
            dir = "RIGHT";
    }
    
    @Override
    protected void move(){
        int dx = 0;
        int dy = 0;
        
        //HANDLE BACTERIA DIRECTIONS (+ rebounds)
        if (dir.equals("UP")) {
            dx = 0;
            dy = -1;
            
            if (y <= 1)
                dir = "DOWN";
        }
        else if (dir.equals("DOWN")) {
            dx = 0;
            dy = 1;
            
            if (y >= Food.getHeight() - 1)
                dir = "UP";
        }
        else if (dir.equals("LEFT")) {
            dx = -1;
            dy = 0;
            
            if (x <= 1)
                dir = "RIGHT";
        }
        else if (dir.equals("RIGHT")) {
            dx = 1;
            dy = 0;
            
            if (x >= Food.getWidth() - 1)
                dir = "LEFT";
        }
        
        //CHECK IF THERE IS FOOD AROUND THE BACTERIUM AND THEN EAT
        for(int i = 0; i < 120; i += 4) {
            if(Food.isFood(x, y - i)){               //N
                dy = -i;
                dx = 0;
                break;
            }
            else if(Food.isFood(x + (i/2), y - i)){  //NNE
                dx = i/2;
                dy = -i;
                break;
            }
            else if(Food.isFood(x + i, y - i)){      //NE
                dx = i;
                dy = -i;
                break;
            }
            else if(Food.isFood(x + i, y - (i/2))){  //ENE
                dx = i;
                dy = -i/2;
                break;
            }
            else if(Food.isFood(x + i, y)){          //E
                dx = i;
                dy = 0;
                break;
            }
            else if(Food.isFood(x + i, y + (i/2))){  //ESE
                dx = i;
                dy = i/2;
                break;
            }
            else if(Food.isFood(x + i, y + i)){      //SE
                dx = i;
                dy = i;
                break;
            }
            else if(Food.isFood(x + (i/2), y + i)){  //SSE
                dx = i/2;
                dy = i;
                break;
            }
            else if(Food.isFood(x, y + i)){          //S
                dy = i;
                dx = 0;
                break;
            }
            else if(Food.isFood(x - (i/2), y + i)){  //SSW
                dx = -i/2;
                dy = i;
                break;
            }
            else if(Food.isFood(x - i, y + i)){      //SW
                dx = -i;
                dy = i;
                break;
            }
            else if(Food.isFood(x - i, y + (i/2))){  //WSW
                dx = -i;
                dy = i/2;
                break;
            }
            else if(Food.isFood(x - i, y)){          //W
                dx = -i;
                dy = 0;
                break;
            }
            else if(Food.isFood(x - i, y - (i/2))){      //WNW
                dx = -i;
                dy = -i/2;
                break;
            }
            else if(Food.isFood(x - i, y - i)){      //NW
                dx = -i;
                dy = -i;
                break;
            }
            else if(Food.isFood(x - (i/2), y - i)){      //NNW
                dx = -i/2;
                dy = -i;
                break;
            }    
        }    
        
        //MOVE BACTERIA
        if (x+dx >= 0 && x+dx<Food.getWidth())
            x += dx; 
        if (y+dy >= 0 && y+dy<Food.getHeight())
            y += dy; 
        
    }
    
    @Override
    public main.Batterio clone() throws CloneNotSupportedException {
        return super.clone();
    }
}