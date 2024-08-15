import java.awt.*;

public class Bullet {
    private int x, y;
    private int speed;
    private int width, height;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 10; // Speed at which the bullet moves
        this.width = 5;
        this.height = 10;
    }

    public void update() {
        y -= speed; // Move the bullet upwards
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return y < 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
