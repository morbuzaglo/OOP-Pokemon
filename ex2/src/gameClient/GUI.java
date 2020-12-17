package gameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;


public class GUI extends JFrame implements ActionListener{

    private JButton blogin;
    private JPanel loginpanel;
    private JTextField id_field;
    private JTextField level_field;
    private JLabel id;
    private  JLabel level;
    private boolean startGame = false;


    public GUI() {
        super("Load game");

        blogin = new JButton("Start game");
        blogin.addActionListener(this);
        loginpanel = new JPanel();
        ImageIcon ii = new ImageIcon("40711a6c71688f6d3f5061654caa501917a571cc_hq.gif");
        BufferedImage resizedImage = new BufferedImage(500, 281, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(ii.getImage(), 0, 0, 500, 281, null);
        graphics2D.dispose();
        // this.drawImage(resizedImage,0,0,ii.getImageObserver());
        this.update(resizedImage.getGraphics());
        repaint();
        id_field = new JTextField(15);
        level_field = new JTextField(15);
        //newUSer = new JButton("New User?");
        id = new JLabel("id - ");
        level = new JLabel("level - ");

        setSize(500, 281);
        setLocation(500, 280);
        loginpanel.setLayout(null);


        id_field.setBounds(200, 65, 150, 20);
        level_field.setBounds(200, 96, 150, 20);
        blogin.setBounds(110, 150, 100, 20);
        id.setBounds(120, 63, 80, 20);
        level.setBounds(120, 95, 80, 20);

        loginpanel.add(blogin);
        loginpanel.add(id_field);
        loginpanel.add(level_field);
        loginpanel.add(id);
        loginpanel.add(level);
        getContentPane().add(loginpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public int get_id()
    {
        return Integer.parseInt(id_field.getText());

    }
    public int get_level()
    {
        return Integer.parseInt(level_field.getText());

    }

    public boolean getStartGame()
    {
        return this.startGame;
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == blogin)
        {
            this.startGame = true;
            this.dispose();
        }
    }
}