package vellu.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int HEIGHT = 600;
    private final int WIDTH = 600;
    private final int UNIT = 20;
    private final int GAMEUNITS = (HEIGHT * WIDTH) / (UNIT * UNIT); // 30x30 grid playground
    private int[] x = new int[GAMEUNITS];
    private int[] y = new int[GAMEUNITS];
    private int[] q = new int[GAMEUNITS];
    private int[] w = new int[GAMEUNITS];
    private Snake snake = new Snake(x, y, UNIT, 100);
    private Snake snake2 = new Snake(q, w, UNIT, 200);
    private int x_random;
    private int y_random;
    private int snakeLength = snake.getLength();
    private int snakeSpeed = snake.getSpeed();
    boolean right = true;
    boolean left, up, down = false;
    boolean right2 = true;
    boolean left2, up2, down2 = false;
    Timer timer;
    Random random;
    Graphics2D g2d;
    private int score = 0;
    private int score2 = 0;

    public GamePanel() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(HEIGHT, WIDTH)); // setSize() doesn't work
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(snakeSpeed, this);
        random = new Random();
        foodLocation();
    }

    // paint() doesn't clear the previous graphics
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(Color.red);

        // snake 1
        for (int z = snake.getLength() - 1; z >= 0; z--) {
            Rectangle rectangle = new Rectangle(x[z], y[z], UNIT, UNIT);
            g2d.fill(rectangle);
        }

        // snake 2
        g2d.setColor(Color.green);
        for (int z = snake2.getLength() - 1; z >= 0; z--) {
            Rectangle rectangle = new Rectangle(q[z], w[z], UNIT, UNIT);
            g2d.fill(rectangle);
        }

        createFood(g2d);
        if (collision()) {
            gameOver(g2d);
        }
        Toolkit.getDefaultToolkit().sync();

    }

    public void foodLocation() {
        x_random = random.nextInt(WIDTH / UNIT) * 20;
        y_random = random.nextInt(HEIGHT / UNIT) * 20;
        System.out.println(x_random + ", " + y_random);
    }

    public void createFood(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        Rectangle food = new Rectangle(x_random, y_random, UNIT, UNIT);
        g2d.fill(food);
    }

    public void checkFoodEaten() {
        if (x_random == x[0] && y_random == y[0]) {
            snake.setLength(snake.getLength() + 1);
            foodLocation();
            score++;
            System.out.println("Score: " + score);
        }
        if (x_random == q[0] && y_random == w[0]) {
            snake.setLength(snake2.getLength() + 1);
            foodLocation();
            score2++;
            System.out.println("Score 2: " + score2);
        }
    }

    boolean collision() {
        
        // check if the snake collide with its own body
        for (int i = 2; i < snake.getLength(); i++) {
            if (x[0] == x[i - 1] && y[0] == y[i - 1]) {
                return true;
            }
        }
        for (int i = 2; i < snake2.getLength(); i++) {
            if (q[0] == q[i - 1] && w[0] == w[i - 1]) {
                return true;
            }
        }
        
        // check if the snake collide with other snake 
        for (int i = 0; i < snake2.getLength(); i++) {
            if (x[0] == q[i] && y[0] == w[i]) {
                return true;
            }
        }
        for (int i = 0; i < snake.getLength(); i++) {
            if (q[0] == x[i] && w[0] == y[i]) {
                return true;
            }
        }
        return false;
    }

    int checkScore() {
        if (score < score2) {
            return score2;
        }
        if (score > score2) {
            return score;
        }
        if(score == score2) {
            return score;
        }
        return 0;
    }
    
    String checkWinner() {
        if (score < score2) {
            return "Green snake!";
        }
        if (score > score2) {
            return "Red snake!";
        }
        if(score == score2) {
            return "It's a tie!";
        }
        return null;
    }

    void gameOver(Graphics2D g2d) {
        timer.stop();
        g2d.setColor(Color.cyan);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Sans Serif", Font.BOLD, 40));
        g2d.drawString("GAME OVER!", 120, 150);
        g2d.drawString("Score: " + checkScore(), 120, 210);
        g2d.setFont(new Font("Sans Serif", Font.BOLD, 20));
        g2d.drawString("The winner is: " +checkWinner(), 100, 300);
        score = 0;
        score2 = 0;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        checkFoodEaten();
        snake.move(x, y);

        // snake 1
        if (right) {
            if (x[0] < WIDTH - UNIT) {
                x[0] = x[0] + UNIT;
            } else {
                x[0] = 0;
            }
        }
        if (left) {
            if (x[0] > 0) {
                x[0] = x[0] - UNIT;
            } else {
                x[0] = WIDTH - UNIT;
            }
        }

        if (up) {
            if (y[0] > 0) {
                y[0] = y[0] - UNIT;
            } else {
                y[0] = HEIGHT - UNIT;
            }
        }
        if (down) {
            if (y[0] < HEIGHT - UNIT) {
                y[0] = y[0] + UNIT;
            } else {
                y[0] = 0;
            }
        }

        // snake 2
        snake.move(q, w);

        if (right2) {
            if (q[0] < WIDTH - UNIT) {
                q[0] = q[0] + UNIT;
            } else {
                q[0] = 0;
            }
        }
        if (left2) {
            if (q[0] > 0) {
                q[0] = q[0] - UNIT;
            } else {
                q[0] = WIDTH - UNIT;
            }
        }

        if (up2) {
            if (w[0] > 0) {
                w[0] = w[0] - UNIT;
            } else {
                w[0] = HEIGHT - UNIT;
            }
        }
        if (down2) {
            if (w[0] < HEIGHT - UNIT) {
                w[0] = w[0] + UNIT;
            } else {
                w[0] = 0;
            }
        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // snake 1
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!left) {
                right = true;
                up = false;
                down = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (!right) {
                left = true;
                up = false;
                down = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!up) {
                right = false;
                left = false;
                down = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!down) {
                right = false;
                left = false;
                up = true;
            }
        }
        // ****************************************************************
        // snake 2
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (!left2) {
                right2 = true;
                up2 = false;
                down2 = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (!right2) {
                left2 = true;
                up2 = false;
                down2 = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (!up2) {
                right2 = false;
                left2 = false;
                down2 = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (!down2) {
                right2 = false;
                left2 = false;
                up2 = true;
            }
        }
        // ****************************************************************8

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            foodLocation();
        }

        // start and pause the game
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (collision()) {
                snake = new Snake(x, y, UNIT, 100);
                snake2 = new Snake(q, w, UNIT, 400);
                foodLocation();
                timer.start(); 
            } else {
                timer.start();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            timer.stop();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
