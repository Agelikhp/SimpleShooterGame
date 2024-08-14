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


    // Update game state
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
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            // Check collision with enemies
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    System.out.println("Collision detected"); //Debugging output
                    bullets.remove(i);
                    enemies.remove(j);
                    score++; //Increment the score when a bullet hits enemy
                    System.out.println("Score: " + score); // Debugging output to confirm score increment
                    i--;
                    break; //Break the loop to avoid IndexOutOfBounceException
                }

                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bullets.remove(i);
                    enemies.remove(j);
                    score++; // Increment the score when a bullet hits an enemy
                    i--;
                    break; // Break the loop to avoid IndexOutOfBoundsException
                }
            }

            // Remove bullets that are off-screen
            if (bullet.isOffScreen()) {
                bullets.remove(i);
                i--;
            }

        }


        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isOffScreen()) {
                enemies.remove(i);
                i--;
            }
        }

            if (random.nextInt(100) < 2) { // 2% chance to spawn an enemy each frame
             enemies.add(new Enemy(random.nextInt(750), 0));

        }


    }

    // Render game objects
    private void render() {
        repaint();
    }

    private int score;

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



