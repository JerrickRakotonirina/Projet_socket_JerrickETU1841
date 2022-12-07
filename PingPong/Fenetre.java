package aff;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame
{
    public Fenetre(JPanel pan)
    {
        this.setContentPane(pan);
        this.proprieteFenetre();
    }

    private void proprieteFenetre()
    {
        this.setTitle("Ping Pong");
        this.setSize(920,840);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}