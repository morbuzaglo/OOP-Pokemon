package gameClient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GUITest
{
    @Test
    void get_id()
    {
        GUI gui=new GUI();
        gui.frame.setVisible(false);
        assertEquals(gui.id_field.getText(),"");
    }
    @Test
    void get_level()
    {
        GUI gui=new GUI();
        gui.frame.setVisible(false);
        assertEquals(gui.level_field.getText(),"");
    }
    @Test
    void valid_level()
    {
        GUI gui=new GUI();
        gui.frame.setVisible(false);
        boolean answer= gui.valid_level(gui.level_field);
        assertFalse(answer);

    }
    @Test
    void valid_id()
    {
        GUI gui=new GUI();
        gui.frame.setVisible(false);
        boolean answer= gui.valid_id(gui.id_field);
        assertFalse(answer);

    }
    @Test
    void getStartGame()
    {
        GUI gui=new GUI();
        gui.frame.setVisible(false);
        assertFalse(gui.getStartGame());

    }

}