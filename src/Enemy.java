import java.awt.*;

public class Enemy {
    private int x, y, speed;
    private int width, height;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 2;
        this.width = 50;
        this.height = 50;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return y > 600;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
