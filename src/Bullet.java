import java.awt.*;

public class Bullet {
    private int x, y;
    private int width = 5, height = 10; // Define the size of the bullet
    private Color color;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.RED; // Color for the bullet
    }

    public void update() {
        y -= 5; // Move the bullet upwards
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Return the bounds for collision detection
    }

    public boolean isOffScreen() {
        return y < 0; // Bullet is offscreen if it moves above the window
    }
}
