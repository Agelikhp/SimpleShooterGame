import java.awt.*;

public class Player {
    private int x, y;
    private int width, height;
    private int speed;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50; // Set player width
        this.height = 50; // Set player height
        this.speed = 5;
    }


    // Update the player's position

    public void update(boolean up, boolean down, boolean left, boolean right) {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
    }

    // Draw the player on the screen
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;

    }

    public int getHeight() {
        return height;

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
