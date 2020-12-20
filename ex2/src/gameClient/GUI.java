package gameClient;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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


