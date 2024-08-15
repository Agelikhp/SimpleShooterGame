import java.awt.*;

public class PowerUp {
    private int x, y;
    private int width =  20, height = 20;
    private Color color;

    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.GREEN; //Color for the power-up
    }

    public void update() {
        y += 2; // Move down the screen
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOffScreen() {
        return y > 600; // 600 is the height of the window
    }
}
