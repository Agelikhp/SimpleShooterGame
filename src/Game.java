import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game extends JPanel implements Runnable {
    // Declare the player variable
    private Player player;
    private List<Bullet> bullets;
    private List<Enemy> enemies;
    private Random random;
    // Movement flags
    private boolean up, down, left, right, shooting;
    private List<PowerUp> powerUps;
    private boolean powerUpCollected;
    private int score;



    public Game() {
        // Set up the game window
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        requestFocus();

        player = new Player(375, 275); // Center the player at (375, 275)
        bullets = new ArrayList<>();

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        random = new Random();
        powerUps = new ArrayList<>();
        powerUpCollected = false;
        score = 0;



        // Initialize the player instance
        player = new Player(375, 275); // Center the player at (375, 275)

        // Add key listener for movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) up = true;
                if (key == KeyEvent.VK_DOWN) down = true;
                if (key == KeyEvent.VK_LEFT) left = true;
                if (key == KeyEvent.VK_RIGHT) right = true;
                if (key == KeyEvent.VK_SPACE) {
                    shooting = true;
                    shoot();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) up = false;
                if (key == KeyEvent.VK_DOWN) down = false;
                if (key == KeyEvent.VK_LEFT) left = false;
                if (key == KeyEvent.VK_RIGHT) right = false;
            }
        });
    }

    private void shoot() {
        bullets.add(new Bullet(player.getX() + player.getWidth()/ 2 -2, player.getY()));
    }


    // Game loop
    @Override
    public void run() {
        while (true) {
            update();
            render();
            try {
                Thread.sleep(16); // approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Update the game
    private void update() {
        player.update(up, down, left, right);

        // Update bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.isOffScreen()) {
                bullets.remove(i);
                i--;
            }
        }

        // Check collision with enemies
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    System.out.println("Bullet hit enemy!"); // Debugging output
                    bullets.remove(i);
                    enemies.remove(j);
                    score += 10; // Increment the score by 10 when a bullet hits an enemy
                    System.out.println("Score: " + score); // Debugging output to confirm score increment
                    i--; // Decrement i to account for the removed bullet
                    break; // Break the inner loop to avoid IndexOutOfBoundsException
                }
            }
        }

        // Update enemies and spawn logic
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isOffScreen()) {
                enemies.remove(i);
                i--;
            }
        }

        // Spawn enemies
        if (random.nextInt(100) < 10) { // 10% chance to spawn an enemy each frame
            enemies.add(new Enemy(random.nextInt(750), 0));
        }

        if (random.nextInt(100) < 2) { // 2% chance to spawn a power-up
            powerUps.add(new PowerUp(random.nextInt(750), 0));
        }


        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update();

            // Check collision with player
            if (powerUp.getBounds().intersects(player.getBounds())) {
                powerUpCollected = true; // Mark power-up as collected
                powerUps.remove(i);
                i--;
                System.out.println("Power-up collected!"); // Debugging output
            }

            if (powerUp.isOffScreen()) {
                powerUps.remove(i);
                i--;
            }
        }

        player.update(up, down, left, right);

        //Update bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.isOffScreen()) {
                bullets.remove(i);
                i--;
            }
        }


    }

    // Render game objects
    private void render() {
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);

        //Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g);


        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        }

        // Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        //Display the score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        // Draw power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
    }

    // Start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Shooter Game");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(game).start();
    }

}



