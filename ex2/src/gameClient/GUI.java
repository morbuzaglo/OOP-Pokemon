package gameClient;
import javax.swing.*;
<<<<<<< HEAD
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
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == blogin) {

            Ex2_Client.startgame();
            this.dispose();
        }

    }
}

=======
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 *
 */
public class GUI extends JFrame  implements ActionListener{
     JFrame frame;
    TextField id_field = new TextField();
    TextField level_field = new TextField();
    JButton button = new JButton("Start Game");
    private boolean startGame = false;
    /**
     *
     *defult constructor of the main login gui
     */
    public GUI()// used form to create these comands it was much eaiser than manully writing them
    {
        frame = new JFrame();
        frame.setBounds(500, 300, 500, 281);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        JLabel uu = new JLabel("");
        uu.setBackground(Color.WHITE);
        uu.setBounds(0, 0, 500, 281);
        panel.setLayout(null);
        uu.setIcon(new ImageIcon("data\\login_gif.gif"));
        id_field.setBounds(220, 100, 85, 21);
        panel.add(id_field);
        level_field.setBounds(220, 150, 85, 21);
        panel.add(level_field);
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(150, 100, 30, 20);
        panel.add(panel_2);
        panel_2.setLayout(null);
        JLabel id = new JLabel("ID :");
        id.setBounds(6, 0, 26, 16);
        panel_2.add(id);
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(150, 150, 60, 20);
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        JLabel level = new JLabel("LEVEL :");
        panel_1.add(level);
        button.addActionListener(this);
        button.setBounds(215, 200, 100, 21);
        panel.add(button);
        panel.add(uu);
        frame.setVisible(true);
    }
    /**
     *return inserted id
     * @returns client id
     */
    public int get_id()
    {
        return Integer.parseInt(id_field.getText());
    }
    /**
     *returns requested level
     * @returns level
     */
    public int get_level()
    {
        return Integer.parseInt(level_field.getText());
    }
    /**
     *returns status of inserted level
     * @returns boolean
     */
    public boolean valid_level(TextField level_field)
    {
        if (level_field.getText() == null)
        {
            return false;
        }
        try
        {
            int level = get_level();
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    /**
     *returns if id is valid
     * @returns if id is valid
     */
    public boolean valid_id(TextField id_field)
    {
        if (id_field.getText() == null)
        {
            return false;
        }
        try
        {
            int id = get_id();
                return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
    }
    /**
     * returns if game has started or not
     * @returns status
     */
    public boolean getStartGame()
    {
        return this.startGame;
    }
    /**
     *sets start game status
     *
     */
    public void setStartGame(boolean b)
    {
        this.startGame=b;
    }
    /**
     *creates warning gui for wrong inserts of data or level
     * @retuns warning gui
     */
    public GUI warning(String s)
    {
        GUI warning =new GUI();
        warning.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        warning.frame.getContentPane().removeAll();
        warning.frame.repaint();
        warning.frame.setSize(300,100);
        warning.frame.setBounds(630,400,300,100);
        JPanel panel=new JPanel();
        warning.frame.getContentPane().add(panel, BorderLayout.CENTER);
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(150, 150, 60, 20);
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        JLabel warning_label = new JLabel(s);
        warning_label.setBounds(6, 0, 26, 16);
        panel_1.add(warning_label);
        return warning;
    }
    /**
     *action performed function to start game when presse start game button
     *
     */
    @Override
    public void actionPerformed (ActionEvent A)
    {
        if (A.getSource() == button)
        {
            if(valid_id(id_field)&&valid_level(level_field))
            {
                    setStartGame(true);
                    frame.dispose();
            }
            else
            {
               warning("wrong id or level, try again");
            }

        }

    }

}
>>>>>>> main


