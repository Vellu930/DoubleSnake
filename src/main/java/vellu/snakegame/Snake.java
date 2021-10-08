
package vellu.snakegame;

public class Snake {
    
    private int length = 5;     
    private int speed = 200;    //miliseconds
    public boolean collision = false;
    
    public Snake() {
    }
    
    public Snake(int[] x, int[] y, int unit, int pos) {
        for (int z = 0; z < length; z++) {
            x[z] = pos - z * unit;
            y[z] = pos;
        }
    }
    public void move(int[] x, int[] y) {
        // move the snake's body
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public int getLength(){
        return length;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}
