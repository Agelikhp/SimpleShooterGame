import java.awt.*;

public class Enemy {
    private int x, y;
    private int width = 40, height = 40; // Define the size of the enemy
    private Color color;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.RED; // Color for the enemy
    }

    public void update() {
        y += 2; // Move down the screen
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Return the bounds for collision detection
    }

    public boolean isOffScreen() {
        return y > 600; // Assuming 600 is the height of the game window
    }
}
