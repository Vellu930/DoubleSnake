
package vellu.snakegame;

import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        add(new GamePanel());
        setTitle("Play Snake with me!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    
    
}
