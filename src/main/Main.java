package main;

import javax.swing.JFrame;
import sf2test.View;

/**
 *
 * @author leonardo
 */
public class Main {

    public static void main(String[] args) {
        View view = new View();
        JFrame frame = new JFrame();
        frame.setTitle("Test");
        frame.getContentPane().add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        view.start();
        view.requestFocus();
    }
    
}
