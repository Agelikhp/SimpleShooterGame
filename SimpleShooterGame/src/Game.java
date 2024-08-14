import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    private boolean running;
    private Thread gameThread;

    public Game() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();
            try {
                Thread.sleep(16); // approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // Update game state
    }

    private void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw game objects here
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Shooter Game");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}

